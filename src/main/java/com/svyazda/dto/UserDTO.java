package com.svyazda.dto;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDTO {

    @Nonnull
    private Integer userId;

    @Nonnull
    private String email;

    @Nonnull
    private String username;

    @Nonnull
    private String password;
}
