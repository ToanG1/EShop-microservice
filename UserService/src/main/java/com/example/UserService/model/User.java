package com.example.UserService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer phoneNumber;
    private String password;
    private String uid;
    private Integer role; //0 for customer, 1 for vendor, 2 for admin
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Address> addressList = new java.util.ArrayList<>();
    private String avatar;
    private Integer point;
    private Date createAt;
    private Date updateAt;
}
