package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.CartItem.Request.CreateCartItemRequest;
import com.sheppo.OrderService.User.dto.CartItem.Request.UpdateCartItemRequest;
import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import com.sheppo.OrderService.common.service.ProductService;
import com.sheppo.OrderService.common.service.StoreService;
import com.sheppo.OrderService.common.service.UserService;
import com.sheppo.OrderService.model.Cart;
import com.sheppo.OrderService.model.CartBox;
import com.sheppo.OrderService.model.CartItem;
import com.sheppo.OrderService.repository.CartBoxRepository;
import com.sheppo.OrderService.repository.CartItemRepository;
import com.sheppo.OrderService.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartItemService {

    private final UserService userService;
    private final ProductService productService;
    private final StoreService storeService;
    private final CartRepository cartRepository;
    private final CartBoxRepository cartBoxRepository;

    private final CartItemRepository cartItemRepository;


    private void createCart(String uid) {
        if (userService.isUserValid(uid)) {
            cartRepository.findByUid(uid).ifPresentOrElse(
                    cart -> {
                        log.info("User {} have cart {} already", uid, cart.getId());
                    },
                    () -> {
                        Cart cart = Cart.builder()
                                .uid(uid)
                                .createAt(new Date())
                                .build();
                        cartRepository.save(cart);
                        log.info("User {} create cart {} successfully", uid, cart.getId());
                    }
            );
        } else log.info("User {} is not available", uid);
    }

    public void createCartBox(Long storeId, Cart cart) {
        if (storeService.isStoreValid(storeId)) {
            CartBox cartBox = CartBox.builder()
                    .storeId(storeId)
                    .cart(cart)
                    .createAt(new Date())
                    .updateAt(new Date())
                    .build();
            cartBoxRepository.save(cartBox);
            log.info("CartBox {} is saved success fully", cartBox.getId());
        } else log.info("Store {} is not available", storeId);
    }

    public void createCartItem(CreateCartItemRequest createCartItemRequest) {
        if (userService.isUserValid(createCartItemRequest.getUid())) {
            //Check is cart available
            if (!cartRepository.existsByUid(createCartItemRequest.getUid()))
                createCart(createCartItemRequest.getUid());
            Cart cart = cartRepository.findByUid(createCartItemRequest.getUid()).get();

            //Check is product valid from product service
            ProductDto product = productService.findProduct(createCartItemRequest.getProductId());
            if (product != null) {

                //Check do cart have cart box for store of product
                if (!cartBoxRepository.existsByCartIdAndStoreId(cart.getId(), product.getStoreId()))
                    createCartBox(product.getStoreId(), cart);
                CartBox cartBox = cartBoxRepository.findByCartIdAndStoreId(cart.getId(), product.getStoreId());

                //Check is CartItem existed
                if (cartItemRepository.existsByCartBoxIdAndProductId(cartBox.getId(), product.getId())) {
                    //Update quantity
                    CartItem cartItem = cartItemRepository.findByCartBoxIdAndProductId(cartBox.getId(), product.getId());
                    cartItem.setQuantity(cartItem.getQuantity() + createCartItemRequest.getQuantity());
                    CartBox cartBox1 = cartItem.getCartBox();
                    cartBox1.setUpdateAt(new Date());
                    cartBoxRepository.save(cartBox1);
                    cartItemRepository.save(cartItem);
                    log.info("Cart Item {} updated quantity successfully", cartItem.getId());
                } else {
                    //Add cartItem
                    CartItem cartItem = CartItem.builder()
                            .productId(createCartItemRequest.getProductId())
                            .quantity(createCartItemRequest.getQuantity())
                            .cartBox(cartBox)
                            .createAt(new Date())
                            .updateAt(new Date())
                            .build();
                    cartBox.setUpdateAt(new Date());
                    cartBoxRepository.save(cartBox);
                    cartItemRepository.save(cartItem);
                    log.info("Add Cart Item {} successfully", cartItem.getId());
                }
            } else log.info("Product {} is not available", createCartItemRequest.getProductId());
        } else log.info("User {} is not available", createCartItemRequest.getUid());
    }

    public void updateCartItem(UpdateCartItemRequest updateCartItemRequest) {
        cartItemRepository.findById(updateCartItemRequest.getCartItemId()).ifPresentOrElse(
                cartItem -> {
                    cartItem.setQuantity(updateCartItemRequest.getQuantity() > 0 ? updateCartItemRequest.getQuantity() : 1);
                    CartBox cartBox = cartItem.getCartBox();
                    cartBox.setUpdateAt(new Date());
                    cartBoxRepository.save(cartBox);
                    cartItemRepository.save(cartItem);
                    log.info("CartItem {} is updated quantity successfully", updateCartItemRequest.getCartItemId());
                },
                () -> log.info("Cartitem {} is not available", updateCartItemRequest.getCartItemId())
        );
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.findById(id).ifPresentOrElse(
                cartItem -> {
                    cartItemRepository.delete(cartItem);
                    if (cartItem.getCartBox().getCartItemList().size() == 1) {
                        cartBoxRepository.deleteById(cartItem.getCartBox().getId());
                    }
                    log.info("CartItem {} is removed successfully", id);
                },
                () -> log.info("Cartitem {} is not available", id)
        );
    }

    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
        if (cartItem.getCartBox().getCartItemList().size() == 1) {
            cartBoxRepository.deleteById(cartItem.getCartBox().getId());
            log.info("CartBox {} isremoved successfully", cartItem.getCartBox().getId());
        }
        log.info("CartItem {} is removed successfully", cartItem.getId());
    }
}
