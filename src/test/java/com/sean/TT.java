package com.sean;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.base.DateUtil;
import com.sean.esdemo.BookVO;
import com.sean.form.DatatableSearch;
import org.elasticsearch.action.index.IndexResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TT {

    public static void main(String args[]) throws Exception {
        BookVO book = new BookVO();
        book.setType("Y");
        book.setAuthor("aa");
        book.setWord_count(123);
        book.setTitle("ccc");
        book.setPublish_date("2019-09-09");
        System.out.println((new ObjectMapper()).writeValueAsBytes(book).toString());
    }
}
