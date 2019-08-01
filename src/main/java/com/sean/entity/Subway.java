package com.sean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subway {

    private Long id;

    private String name;

    private String cityEnName;
}
