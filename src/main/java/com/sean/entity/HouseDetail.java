package com.sean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("house_detail")
public class HouseDetail {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String description;

    private String layoutDesc;

    private String traffic;

    private String roundService;

    private int rentWay;

    private String address;

    private Long subwayLineId;

    private String subwayLineName;

    private Long subwayStationId;

    private String subwayStationName;

    private Long houseId;
}
