package com.jamkrindo.generate.generatesertfkatspr.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthDto {


    private String username;
    private String password;
}
