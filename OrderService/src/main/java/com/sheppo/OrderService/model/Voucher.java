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
@Table(name = "t_voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    private Boolean isActive;
    private String categoryId;     // allCategory for all
    private Long storeId;  // 0 for all
    private Integer role;   // 0 is shipping voucher, 1 is product voucher
    private Float voucherValue;
    private Integer count;
    private Date createAt;
    private Date updateAt;
}
