package com.sean.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 租房请求参数结构体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RentSearch {
    private String cityEnName;
    private String regionEnName;
    private String priceBlock;
    private String areaBlock;
    private int room;
    private int direction;
    private String keywords;
    private int rentWay = -1;
    private String orderBy = "last_update_time";
    private String orderDirection = "desc";
    private int start = 0;
    private int size = 5;


    public int getStart() {
        return start > 0 ? start : 0;
    }

    public int getSize() {
        if (this.size < 1) {
            return 5;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }


    public int getRentWay() {
        if (rentWay > -2 && rentWay < 2) {
            return rentWay;
        } else {
            return -1;
        }
    }

    public String getOrderBy() {
        if ("distanceToSubway".equals(orderBy)) {
            return "distance_to_subway";
        }
        return orderBy;
    }

}
