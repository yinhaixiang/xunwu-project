package com.sean.service;

import com.sean.esdemo.BookService;
import com.sean.esdemo.BookVO;
import com.sean.esdemo.BoolQueryVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {


    @Autowired
    private BookService bookService;


    @Test
    public void addBook() {
        BookVO book = new BookVO();
        book.setType("Y");
        book.setAuthor("aa");
        book.setWordCount(123);
        book.setTitle("bbb");
        book.setPublishDate("2019-09-09");
        String result = bookService.addBook(book);
        System.out.println(result);
    }

    @Test
    public void get() {
        String result = bookService.findBookById("1");
        System.out.println(result);
    }


    @Test
    public void update() {
        BookVO book = new BookVO();
        book.setId("1");
        book.setType("Y");
        book.setAuthor("aa");
        book.setWordCount(123);
        book.setTitle("bbb");
        book.setPublishDate("2019-09-09");
        String result = bookService.update(book);
        System.out.println(result);
    }


    @Test
    public void delete() {
        String result = bookService.delete("4");
        System.out.println(result);
    }




    @Test
    public void boolQuery() {
        BoolQueryVO boolQueryVO = new BoolQueryVO();
        boolQueryVO.setAuthor("孙悟空");
        boolQueryVO.setTitle("大圣");
        String result = bookService.boolQuery(boolQueryVO);
        System.out.println(result);
    }









}