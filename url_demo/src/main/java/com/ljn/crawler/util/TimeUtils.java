package com.ljn.crawler.util;

/**
 * @Author李胖胖
 * @Date 2019/1/12 1:03
 * @Description:
 **/
public class TimeUtils {
    private long millis;
    private String msg;
    public void start(String event){
        this.msg = event;
        System.out.println("开始 "+msg);
        this.millis = System.currentTimeMillis();
    }
    public void end(){
        System.out.println("已完成 "+msg);
        showTime();
    }
    public void showTime(){
        long secend = (System.currentTimeMillis() - millis) / 1000;
        System.out.println("共计耗时 "+secend +"秒");
    }
}
