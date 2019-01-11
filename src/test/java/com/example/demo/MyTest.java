package com.example.demo;

import com.example.demo.lgh.annotation.FiledAnnotation;
import com.example.demo.lgh.annotation.MyAnnotation;


public class MyTest {
    @FiledAnnotation(selector = "body > div.container.cfix > div.detail-main > div.detail-cont > div.job-title > div.j-des > em")
    private String price;
    @FiledAnnotation(selector = "body > div.container.cfix > div.detail-main > div.detail-cont > div.entrust-btn-warp > h2.postion-title > span.title.fl")
    private String jopName;

    @MyAnnotation(hello = "hehe")
    public void output() {
        System.out.println("sasa");
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "hehe[{" +
                "price:" + price
                + ",jopName:" + jopName + "}]";
    }
}