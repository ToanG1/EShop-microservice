package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.Order.Request.DeleteOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.FindOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.PlaceOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.UpdateOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Response.*;
import com.sheppo.OrderService.common.dto.Address.Response.AddressDto;
import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import com.sheppo.OrderService.common.service.ProductService;
import com.sheppo.OrderService.common.service.UserService;
import com.sheppo.OrderService.common.service.ZaloPayService;
import com.sheppo.OrderService.model.*;
import com.sheppo.OrderService.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("UserOrderService")
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

    public SuccessOrderResponse placeOrder(PlaceOrderRequest request) {
        SuccessOrderResponse resp = new SuccessOrderResponse();
        //Check is user valid
        AddressDto addressDto = userService.isUserAndAddressValid(request.getUid(), request.getAddressId());
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
                resp.setOrders( storeIdList.stream().map(store -> {

                    //Find products follow storeid
                    List<ProductDto> products = productDtoList.stream()
                            .filter(productDto -> productDto.getStoreId().equals(store)).toList();
                    //Map cartItem to Products
                    List<ProductDto> mapedProducts = maptoOrderProduct(products, cartItems);

                    Optional<Shipping> shipping = shippingRepository.findById(request.getShippingId());
                    if (shipping.isPresent()) {
                        //Create order and save order
                        Order order = Order.builder()
                                .uid(request.getUid())
                                .addressId(request.getAddressId())
                                .storeId(store)
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
                                productService.ProductAfterOrder(productDto.getId(), productDto.getCartItemQuantity());
                                //Delete cartItem after ordered successfully
                                cartItemService.deleteCartItem(productDto.getCartItemId(), request.getUid());
                            }
                        });

                        //call api for payment and update order
                        if (request.getPayment().equals("zaloPay")) {
                            String url = zaloPayService.createOrder(order.getId());
                            order.setPaymentUrl(url);
                            orderRepository.save(order);
                            return CreateOrderResponse.builder()
                                    .url(url)
                                    .id(order.getId())
                                    .build();
                        }else return CreateOrderResponse.builder().url(null).id(order.getId()).build();
                    } else {
                        log.info("Shipping {} is not available", request.getShippingId());
                        return CreateOrderResponse.builder().res("Shipping option is not available").build();
                    }
                }).toList());
            } else {
                log.info("There are no product available to order");
                resp.setRes("Products is not available to order");
            }
        } else {
            log.info("User {} with address {} is not available", request.getUid(), request.getAddressId());
            resp.setRes("User with address is not available");
        }
        return resp;
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

    private Integer calculateShippingCost(Shipping shipping, String shippingVoucherCode) {
        Optional<Voucher> voucher = voucherRepository.findByCode(shippingVoucherCode);
        return (int) (shipping.getShippingCost() * (1 - (voucher.isPresent() ? voucher.get().getVoucherValue() : 0)));
    }

    public String updateOrder(UpdateOrderRequest updateOrderRequest) {
        Optional<Order> order = orderRepository.findByUidAndId(updateOrderRequest.getUid(), updateOrderRequest.getId());
        if (order.isPresent() && order.get().getOrderStatus() == 0){
            if (updateOrderRequest.getAddressId() != null) {
                AddressDto addressDto = userService.isUserAndAddressValid(updateOrderRequest.getUid(),
                        updateOrderRequest.getAddressId());
                order.get().setAddressId(addressDto.getId());
                order.get().setReceiverName(addressDto.getReceiverName());
                order.get().setReceiverAddress(addressDto.getReceiverAddress());
                order.get().setPhoneNumber(addressDto.getPhoneNumber());
            }
            String url = null;
            if (updateOrderRequest.getPayment() != null && order.get().getPayment().equals("COD")) {
                url = zaloPayService.createOrder(order.get().getId());
            }
            order.get().setNote(updateOrderRequest.getNote() != null ? updateOrderRequest.getNote() : order.get().getNote());
            order.get().setUpdateAt(new Date());
            orderRepository.save(order.get());
            return url;
        } else{
            log.warn("Order {} is not available", updateOrderRequest.getId());
            return null;
        }
    }

    public OrderResponse findOrder(FindOrderRequest findOrderRequest) {
        if (userService.isUserValid(findOrderRequest.getUid())){
            List<Order> orderList = orderRepository.findAllByUidAndOrderStatus(findOrderRequest.getUid(), findOrderRequest.getOrderStatus(),
                    PageRequest.of(findOrderRequest.getCurrentPage(), findOrderRequest.getSize())).getContent();
            List<OrderDto> orderDtos = orderList.stream().map(this::mapToOrderDto).toList();
            return OrderResponse.builder()
                    .orderStatus(findOrderRequest.getOrderStatus())
                    .orderDtoList(orderDtos)
                    .currentPage(findOrderRequest.getCurrentPage())
                    .totalPage((int) Math.ceil((float) orderRepository.countAllByUidAndOrderStatus(findOrderRequest.getUid(), findOrderRequest.getOrderStatus()) /
                            findOrderRequest.getSize()))
                    .size(findOrderRequest.getSize())
                    .build();
        } else {
            log.warn("User {} is not available", findOrderRequest.getUid());
            return null;
        }
    }

    private OrderDto  mapToOrderDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItemList().stream().map(this::mapToOrderItemDto).toList();
        return OrderDto.builder()
                .id(order.getId())
                .uid(order.getUid())
                .receiverName(order.getReceiverName())
                .receiverAddress(order.getReceiverAddress())
                .phoneNumber(order.getPhoneNumber())
                .note(order.getNote())
                .orderStatus(order.getOrderStatus())
                .orderValue(order.getOrderValue())
                .shippingCost(order.getShippingCost())
                .payment(order.getPayment())
                .isPaid(order.getIsPaid())
                .paymentUrl(order.getPaymentUrl())
                .createAt(order.getCreateAt())
                .updateAt(order.getUpdateAt())
                .orderItemDtoList(orderItemDtos)
                .build();
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .productAvatar(orderItem.getProductAvatar())
                .productValue(orderItem.getProductValue())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public void cancelOrder(DeleteOrderRequest deleteOrderRequest) {
        if (userService.isUserValid(deleteOrderRequest.getUid())){
            orderRepository.findByUidAndId(deleteOrderRequest.getUid(), deleteOrderRequest.getId()).ifPresentOrElse(
                    order -> {
                        if (order.getOrderStatus() == 0) {
                            order.setOrderStatus(3);
                            order.setUpdateAt(new Date());
                            orderRepository.save(order);
                            log.info("Order {} is canceled successfully", deleteOrderRequest.getId());
                        } else {
                            log.warn("Order {} can not be canceled", deleteOrderRequest.getId());
                        }
                    },
                    () -> log.warn("Order {} is not available", deleteOrderRequest.getId())
            );
        }
    }
}
