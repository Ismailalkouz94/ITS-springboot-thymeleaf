package com.example.its.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ChangePasswordDTO {

    private String userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
