package com.sean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("house_tag")
public class HouseTag {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long houseId;

    private String name;

    public HouseTag(Long houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }

}
