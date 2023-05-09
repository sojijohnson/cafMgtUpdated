package com.inn.cafe.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private  String email;
    private String name;
    private String password;
    private  String contactNumber;
    private  String role;
    private  String status;
}
