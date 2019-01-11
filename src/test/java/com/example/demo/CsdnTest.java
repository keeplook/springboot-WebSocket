package com.example.demo;

import com.example.demo.lgh.annotation.FiledAnnotation;

public class CsdnTest {
    @FiledAnnotation(selector = "body > div.container.clearfix > main > div.blog-content-box > div.article-header-box > div.article-header > div.article-info-box > div.article-bar-top > span.read-count")
    private String readCount;

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    @Override
    public String toString() {
        return readCount;
    }
}
