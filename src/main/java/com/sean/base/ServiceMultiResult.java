package com.sean.base;

import lombok.*;

import java.util.List;

/**
 * 通用多结果Service返回结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class ServiceMultiResult<T> {
    private long total;
    private List<T> result;


    public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}
