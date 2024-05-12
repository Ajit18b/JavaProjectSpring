package com.crud.CRUD.models;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class UserDetails {
    private int id;
    @NotEmpty(message = "Name is required")
    private String name;
    private String email;
    private String phone;
}
