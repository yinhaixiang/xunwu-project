package com.sean.service;

import com.sean.esdemo.BookService;
import com.sean.esdemo.BookVO;
import com.sean.esdemo.BoolQueryVO;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
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
        book.setWord_count(123);
        book.setTitle("eee");
        book.setPublish_date("2019-09-09");
        IndexResponse result = bookService.addBook(book);
        System.out.println(result);
    }

    @Test
    public void get() {
        GetResponse result = bookService.findBookById("1");
        System.out.println(result);
    }


    @Test
    public void update() {
        BookVO book = new BookVO();
        book.setId("1");
        book.setType("Y");
        book.setAuthor("aa");
        book.setWord_count(123);
        book.setTitle("abc");
        book.setPublish_date("2019-09-09");
        UpdateResponse result = bookService.update(book);
        System.out.println(result);
    }


    @Test
    public void delete() {
        DeleteResponse result = bookService.delete("6");
        System.out.println(result);
    }




    @Test
    public void boolQuery() {
        BoolQueryVO boolQueryVO = new BoolQueryVO();
        boolQueryVO.setAuthor("孙悟空");
        boolQueryVO.setTitle("大圣");
        boolQueryVO.setGtWordCount(20);
        boolQueryVO.setLtWordCount(10000);
        SearchResponse result = bookService.boolQuery(boolQueryVO);
        System.out.println(result);
    }









}