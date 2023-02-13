package com.example.UserService.model;

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
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String bio;
    private Boolean isActive;
    private Boolean isOpen;
    private String avatar;
    private Integer rating;
    private Date createAt;
    private Date updateAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany
    @JoinColumn(name = "staff_id")
    private List<User> staffs;
}
