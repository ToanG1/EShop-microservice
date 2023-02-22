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
                                .orderValue(calculateOrderValue(products, cartItems, request.getProductVoucher()))
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

                        //Save oderItem and delete cartItem
                        products.forEach(productDto -> {
                            //map cartitem quantity to product dto to save orderItem
                            CartItem cartItem1 = cartItems.stream().filter(cartItem ->
                                            cartItem.getProductId().equals(productDto.getId()) &&
                                                    productDto.getQuantity() >= cartItem.getQuantity()).findFirst().get();
                            orderItemRepository.save(mapToOrderItem(productDto, cartItem1.getQuantity(), order));
                            //Minus quantity of product
                            productService.minusQuantityAfterOrder(cartItem1.getProductId(), cartItem1.getQuantity());
                            //Delete cartItem after ordered successfully
                            cartItemService.deleteCartItem(cartItem1);
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

    private boolean checkPaymentValid(String payment) {
        return payment.equals("zaloPay") || payment.equals("banking") || payment.equals("visa");
    }

    private Integer calculateShippingCost(Shipping shipping, String shippingVoucherCode) {
        Optional<Voucher> voucher = voucherRepository.findByCode(shippingVoucherCode);
        return (int) (shipping.getShippingCost() * (1 - (voucher.isPresent() ? voucher.get().getVoucherValue() : 0)));
    }

    private OrderItem mapToOrderItem(ProductDto productDto, int quantity, Order order) {
        return OrderItem.builder()
                .productId(productDto.getId())
                .productName(productDto.getName())
                .productValue(productDto.getPrice())
                .productAvatar(productDto.getListImages().get(0))
                .quantity(quantity)
                .order(order)
                .createAt(new Date())
                .build();
    }

    private int calculateOrderValue(List<ProductDto> products,List<CartItem> cartItems, String voucherCode) {
        int orderValue = products.stream().mapToInt(product ->{
            CartItem cartItem1 = cartItems.stream().filter(cartItem ->
                    cartItem.getProductId().equals(product.getId())).findFirst().get();
            return product.getPrice() * cartItem1.getQuantity();
        }).sum();
        Optional<Voucher> voucher = voucherRepository.findByCode(voucherCode);
        return (int) (orderValue * (1 - (voucher.isPresent() ? voucher.get().getVoucherValue() : 0)));
    }
}
