package com.enigmacamp.enigshop_challenge.model.entity;

import com.enigmacamp.enigshop_challenge.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.CUSTOMER)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "isActive", columnDefinition = "BOOLEAN DEFAULT TRUE")
    Boolean isActive;
}
