package com.sheppo.OrderService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private Long storeId;
    private Long addressId;
    private String receiverName;
    private String receiverAddress;
    private Integer phoneNumber;
    private String note;
    private Integer orderStatus; // 0 for pending, 1 for delivering, 2 for delivered, 3 for canceled, 4 for denied
    private Integer orderValue;
    private Integer shippingCost;
    private String payment;
    private Boolean isPaid;
    private String paymentUrl;
    private Date createAt;
    private Date updateAt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderItem> orderItemList;
    @OneToOne
    @JoinColumn(name = "shippingId")
    private Shipping shipping;
}
