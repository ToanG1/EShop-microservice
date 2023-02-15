package com.sheppo.OrderService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_OrderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private String productName;
    private String productAvatar;
    private Integer productValue;
    private Integer quantity;
    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
