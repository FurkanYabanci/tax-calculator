package com.yabanci.ayrotek.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
}