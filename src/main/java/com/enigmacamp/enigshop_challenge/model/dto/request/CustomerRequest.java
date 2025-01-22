package com.enigmacamp.enigshop_challenge.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRequest {
    private String id;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters and spaces")
    private String fullName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d+$", message = "Phone number must contain only digits")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;
    private Boolean isActive;
}
