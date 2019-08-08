package com.sean.esdemo;

import lombok.Data;

/**
 * @author Zereao
 * @version 2019/05/15 19:03
 */
@Data
public class BookVO {
    private String id;
    private String type;
    private Integer wordCount;
    private String author;
    private String title;
    private String publishDate;
}
