package com.sean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@TableName("house_subscribe")
public class HouseSubscribe {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long houseId;

    private Long userId;

    private Long adminId;

    private int status;

    private Date createTime;

    private Date lastUpdateTime;

    private Date orderTime;

    private String telephone;

    private String description;

}
