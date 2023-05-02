package com.nandoamori.springboot2essencials.requests;

import lombok.Data;

@Data
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
