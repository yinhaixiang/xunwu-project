package com.sean;

import com.sean.base.DateUtil;
import com.sean.form.DatatableSearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TT {

    public static void main(String args[]) throws ParseException {

//        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//
//        Date result = ft.parse("2019-08-06 00:00:00");
//        System.out.println(result);


        DatatableSearch searchBody = new DatatableSearch();
        searchBody.setStart(0);
        searchBody.setLength(10);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createTimeMax = ft.parse("2019-08-06 00:00:00");

        searchBody.setCreateTimeMax(createTimeMax);


        String stringYmdByDate = DateUtil.getStringYmdByDate(searchBody.getCreateTimeMax());
        System.out.println(stringYmdByDate);
    }
}
