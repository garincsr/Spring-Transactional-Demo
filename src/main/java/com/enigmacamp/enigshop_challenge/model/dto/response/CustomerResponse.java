package com.enigmacamp.enigshop_challenge.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerResponse {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isActive;
}
