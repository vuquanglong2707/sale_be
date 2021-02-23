package com.saleor.saleor_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoUser {
    private Long id;
    private String username;
    private String password;
    private String email;
    private DtoShop dtoShops = new DtoShop();
}
