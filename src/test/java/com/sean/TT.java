package com.sean;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sean.base.DateUtil;
import com.sean.form.DatatableSearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TT {

    public static void main(String args[]) throws ParseException {
        System.out.println(DateUtil.getDateYmdDate("2099-08-11"));
    }
}
