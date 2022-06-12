package com.ironhack.WTWAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewListDTO {
    private String name;
    private String description;
    private Long ownerId;
}
