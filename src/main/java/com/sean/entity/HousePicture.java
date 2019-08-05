package com.sean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("house_picture")
public class HousePicture {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long houseId;

    private String path;

    private String cdnPrefix;

    private int width;

    private int height;

    private String location;

}
