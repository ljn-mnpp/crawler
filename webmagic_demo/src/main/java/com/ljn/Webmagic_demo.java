package com.ljn;

import us.codecraft.webmagic.Spider;

/**
 * @Author李胖胖
 * @Date 2019/1/27 8:49
 * @Description:
 **/
public class Webmagic_demo {
    public static void main(String[] args) {
        Spider.create(new MyProcessor()).addUrl("http://www.itcast.cn")
                //.run(); //当前线程
                .start(); //开启新线程
    }
}
