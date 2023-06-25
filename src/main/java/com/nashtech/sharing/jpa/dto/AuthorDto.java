package com.nashtech.sharing.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthorDto {

    Long authorId;
    String name;
    String email;
    String address;
}
