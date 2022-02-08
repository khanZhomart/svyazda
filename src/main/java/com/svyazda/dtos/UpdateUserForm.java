package com.svyazda.dtos;

import com.svyazda.enums.Visibility;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateUserForm {
    public String username;
    public String password;
    public Visibility profilePageVisibility;
}