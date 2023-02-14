package com.sheppo.OrderService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_CartBox")
public class CartBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cartBox")
    private List<CartItem> cartItemList;
}
