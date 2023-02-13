package com.example.UserService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiverName;
    private String receiverAddress;
    private Integer phoneNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}