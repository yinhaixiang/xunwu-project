package com.sean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@TableName("support_address")
public class SupportAddress {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String belongTo;

    private String enName;

    private String cnName;

    private String level;

    @TableField("baidu_map_lng")
    private double baiduMapLongitude;

    @TableField("baidu_map_lat")
    private double baiduMapLatitude;

}
