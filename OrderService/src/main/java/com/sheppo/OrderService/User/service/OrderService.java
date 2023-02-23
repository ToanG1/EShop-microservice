package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.Order.Request.PlaceOrderRequest;
import com.sheppo.OrderService.common.dto.Address.Response.AddressDto;
import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import com.sheppo.OrderService.common.service.ProductService;
import com.sheppo.OrderService.common.service.UserService;
import com.sheppo.OrderService.common.service.ZaloPayService;
import com.sheppo.OrderService.model.*;
import com.sheppo.OrderService.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ZaloPayService zaloPayService;

    private final UserService userService;

    private final ProductService productService;

    private final CartItemService cartItemService;

    private final CartItemRepository cartItemRepository;

    private final ShippingRepository shippingRepository;

    private final VoucherRepository voucherRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public List<String> placeOrder(PlaceOrderRequest request) {
        List<String> orderUrl = new ArrayList<>();

        //Check is user valid
        AddressDto addressDto = userService.isUseAndAddressValid(request.getUid(), request.getAddressId());
        if (addressDto != null) {

            //Check is Product list valid
            List<CartItem> cartItems = cartItemRepository.findAllById(request.getCartItemIdList());
            List<ProductDto> productDtoList = productService.findProductList(
                    cartItems.stream().map(CartItem::getProductId).toList());
            if (!productDtoList.isEmpty()) {

                //Seperate product list into multiple order by store id or cart box
                List<Long> storeIdList = new ArrayList<>();
                productDtoList.forEach(productDto -> {
                    if (!storeIdList.contains(productDto.getStoreId())) storeIdList.add(productDto.getStoreId());
                });
                //place orders
                orderUrl = storeIdList.stream().map(store -> {

                    //Find products follow storeid
                    List<ProductDto> products = productDtoList.stream()
                            .filter(productDto -> productDto.getStoreId().equals(store)).toList();
                    //Map cartItem to Products
                    List<ProductDto> mapedProducts = maptoOrderProduct(products, cartItems);

                    Optional<Shipping> shipping = shippingRepository.findById(request.getShippingId());
                    if (shipping.isPresent()) {
                        //save order
                        Order order = Order.builder()
                                .uid(request.getUid())
                                .addressId(request.getAddressId())
                                .receiverAddress(addressDto.getReceiverAddress())
                                .receiverName(addressDto.getReceiverName())
                                .phoneNumber(addressDto.getPhoneNumber())
                                .note(request.getNote() != null ? request.getNote() : "")
                                .orderStatus(0)
                                .orderValue(calculateOrderValue(mapedProducts, request.getProductVoucher()))
                                .shippingCost(calculateShippingCost(shipping.get(), request.getShippingVoucher()))
                                .payment(request.getPayment() != null ? checkPaymentValid(request.getPayment()) ?
                                        request.getPayment() : "COD" : "COD")
                                .isPaid(false)
                                .shipping(shipping.get())
                                .createAt(new Date())
                                .updateAt(new Date())
                                .build();
                        orderRepository.save(order);
                        log.info("Order {} is saved successfully", order.getId());

                        mapedProducts.forEach(productDto -> {
                            if (productDto != null) {
                                //Save oderItem
                                orderItemRepository.save(mapToOrderItem(productDto, order));
                                //Minus quantity of product
                                productService.minusQuantityAfterOrder(productDto.getId(), productDto.getCartItemQuantity());
                                //Delete cartItem after ordered successfully
                                cartItemService.deleteCartItem(productDto.getCartItemId());
                            }
                        });

                        //call api for payment to update isPaid
                        if (request.getPayment().equals("zaloPay")) return zaloPayService.createOrder(order.getId());
                        else return null;
                    } else log.info("Shipping {} is not available", request.getShippingId());
                    return null;
                }).toList();
            } else log.info("There are no product available to order");
        } else log.info("User {} with address {} is not available", request.getUid(), request.getAddressId());
        return orderUrl;
    }

    private List<ProductDto> maptoOrderProduct(List<ProductDto> products, List<CartItem> cartItems) {
        List<ProductDto> productDtoList = products.stream().map(productDto -> {
            Optional<CartItem> cartItem = cartItems.stream().filter(cartItem1 -> cartItem1.getProductId().equals(productDto.getId()) &&
                    productDto.getQuantity() >= cartItem1.getQuantity()).findFirst();
            if (cartItem.isPresent()) {
                productDto.setCartItemQuantity(cartItem.get().getQuantity());
                productDto.setCartItemId(cartItem.get().getId());
                return productDto;
            }else {
                log.info("Product {} is not available", productDto.getId());
                return null;
            }
        }).toList();
        return productDtoList;

    }

    private boolean checkPaymentValid(String payment) {
        return payment.equals("zaloPay") || payment.equals("banking") || payment.equals("visa");
    }

    private Integer calculateShippingCost(Shipping shipping, String shippingVoucherCode) {
        Optional<Voucher> voucher = voucherRepository.findByCode(shippingVoucherCode);
        return (int) (shipping.getShippingCost() * (1 - (voucher.isPresent() ? voucher.get().getVoucherValue() : 0)));
    }

    private OrderItem mapToOrderItem(ProductDto productDto, Order order) {
        return OrderItem.builder()
                .productId(productDto.getId())
                .productName(productDto.getName())
                .productValue(productDto.getPrice())
                .productAvatar(productDto.getListImages().get(0))
                .quantity(productDto.getCartItemQuantity())
                .order(order)
                .createAt(new Date())
                .build();
    }

    private int calculateOrderValue(List<ProductDto> products, String voucherCode) {
        int orderValue = products.stream().mapToInt(product -> product.getPrice() * product.getCartItemQuantity()).sum();
        Optional<Voucher> voucher = voucherRepository.findByCode(voucherCode);
        return (int) (orderValue * (1 - (voucher.isPresent() ? voucher.get().getVoucherValue() : 0)));
    }
}
