package com.sean.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseSuggest {
    private String input;
    private int weight = 10; // 默认权重

}
