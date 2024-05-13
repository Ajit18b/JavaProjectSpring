package com.crud.CRUD.models;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDetails {
    private int id;

    @NotEmpty(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a 10-digit number")
    @Size(max = 10, message = "Phone number must not exceed 10 characters")
    private String phone;
}