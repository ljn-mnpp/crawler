package com.ljn.crawler.meinv;

import com.ljn.crawler.util.FileUtil;
import com.ljn.crawler.util.NetIOUtil;
import com.ljn.crawler.util.SimpleURLCrawlerUtil;
import com.ljn.crawler.util.TimeUtils;

import java.io.File;
import java.util.*;

/**
 * @Author李胖胖
 * @Date 2019/1/11 21:21
 * @Description: 爬取并自动下载 http://www.netbian.com 下的美女图片
 *
 *               必须设置的参数: 分页 startPage  endPage  下载目录(本地目录)
 *               可选参数:  如果想爬取 http://www.netbian.com 站点下其他类型图片[本人只爱美女~] 只需改动匹配模式正则表达式
 *               说明:  每个分页 最终会下载18个图片,每个图片大约 约为  0.5M - 1.5M[1920*1080]
 *               运行:  直接运行main方法即可.  请注意查看参数设置
 *               控制台output示例:
                        生成分页链接 开始...
                        生成分页链接 结束...共计耗时 0秒
                        记录已下载页 开始...
                        记录已下载页 结束...共计耗时 0秒
                        爬取图片页面链接 开始...
                        爬取图片页面链接 结束...共计耗时 0秒
                        爬取1920*1080图片页面链接 开始...
                        爬取1920*1080图片页面链接 结束...共计耗时 2秒
                        爬取1920*1080图片资源链接 开始...
                        爬取1920*1080图片资源链接 结束...共计耗时 2秒
                        ==============================================================
                        开始爬取...最大线程数:5
                        目标资源数量: 18
                        警告:线程池未设置自动结束参数...如果所有线程均已爬取结束,停止程序即可.
                        ===============================================================
                        Thread[pool-1-thread-2,5,main]爬取链接... 开始...
                        Thread[pool-1-thread-1,5,main]爬取链接... 开始...
                        Thread[pool-1-thread-4,5,main]爬取链接... 开始...
                        Thread[pool-1-thread-5,5,main]爬取链接... 开始...
                        Thread[pool-1-thread-3,5,main]爬取链接... 开始...
                        Thread[pool-1-thread-5,5,main]爬取链接... 结束...共计耗时 5秒
                        Thread[pool-1-thread-2,5,main]爬取链接... 结束...共计耗时 5秒
                        Thread[pool-1-thread-4,5,main]爬取链接... 结束...共计耗时 5秒
                        Thread[pool-1-thread-1,5,main]爬取链接... 结束...共计耗时 7秒
                        Thread[pool-1-thread-3,5,main]爬取链接... 结束...共计耗时 10秒
 *
 *
 *
 * 分析:
 * 此网站模式很简单  分页链接 :      http://www.netbian.com/meinv/index_[?].htm  ?号为 2-200内整数,连续增长
 *                 可循环生成   每页18个目标链接
 *
 *                 图片页面链接:    desk/[ID]-[尺寸:1920*1080].htm
 *                 模式            http://www\\.netbian\\.com  /desk/\\d*\\.htm  卧槽他,这个模式可一顿试...
 *                 每页含 1个大图片页面目标链接
 *
 *                 大图片页面链接:  http://www.netbian.com/desk/21359-1920x1080.htm
 *                 模式:           http://www.netbian.com /desk/\\d*-1920x1080.htm
 *                 每页含一个 目标资源链接  有个蛋疼的小广告就是过滤不掉,他喵的资源地址与目标格式完全一致 除非按资源大小过滤
 *
 *                 img图片下载链接: http://img.netbian.com/file/2018/1210/07afcefd1752d952142990c4ea0e2392.jpg
 *                 模式:           http://img\\.netbian\\.com/file/.*\\jpg
 *
 *
 *
 **/
public class Display {

    //图片页面链接模式
    private static String targitHtmPattern ="/desk/\\d*\\.htm";
    //链接前缀
    private static String desk_prefix = "http://www.netbian.com";
    //大图片页面模式
    private static String Htm_1920_1080_Pattern = "/desk/\\d*-1920x1080.htm";
    //图片资源模式
    private static String imgSourcePattern = "http://img\\.netbian\\.com/file/.+?\\.jpg";

    //以下为示例参数 请自行配置
    private static int startPage = 1;
    //分页参数(endPage - endPage)20以内安全，超过20有可能被封IP 超过40一定封！ 建议: 设置为10
    private static int endPage = 1;

    //下载目录
    private static String dir_path = "E:\\www_netbian_com_meinv";

    public static void main(String[] args) {

        //获取图片资源链接
        List<String> imgSources = getImgSources(startPage, endPage);

        //多线程下载图片
        NetIOUtil.multiThreadDownloadJpg(imgSources,new File(dir_path),5);

        //下载
       /* for (String imgSource : imgSources) {
            NetIOUtil.downloadJpg(imgSource,new File(dir_path));
        }*/

    }

    //获取图片资源链接
    private static List<String> getImgSources(int startPage,int endPage){

        //生成分页链接
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start("生成分页链接");
        LinkedList<String> pageLinks =
                getPageLink("http://www.netbian.com/meinv/index_", ".htm", startPage, endPage);
        timeUtils.end();


        //记录已下载页
        timeUtils.start("记录已下载页");
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            list.add(String.valueOf(i));
        }
        FileUtil.saveList(
                new File("D:\\project\\crawler\\url_demo\\src\\main\\java\\com\\ljn\\crawler\\meinv\\已下载页数"),
                list,true);
        timeUtils.end();
        //已保存
        //FileUtil.saveList(new File("D:\\project\\crawler\\url_demo\\file\\netbianPageLinks"),pageLink);

        //爬取图片页面链接
        timeUtils.start("爬取图片页面链接");
        LinkedList<String> deskHtms = new LinkedList<>();
        for (String page : pageLinks) {
            LinkedList<String> Htms = SimpleURLCrawlerUtil.getTargetURLS(page, targitHtmPattern);
            deskHtms.addAll(Htms);
        }
        timeUtils.end();

        //拼接链接  别用增强....  String 拿出来的不是引用...
        for (int i = 0; i < deskHtms.size();i++) {
            deskHtms.set(i,desk_prefix +deskHtms.get(i));
        }

        //爬取1920*1080图片页面链接
        timeUtils.start("爬取1920*1080图片页面链接");
        Set<String> htm_1920_1080_links = new HashSet();
        for (String deskHtm : deskHtms) {
            LinkedList<String> targetURLS = SimpleURLCrawlerUtil.getTargetURLS(deskHtm, Htm_1920_1080_Pattern);
            for (String desk : targetURLS){
                htm_1920_1080_links.add(desk_prefix + desk);
            }
        }
        timeUtils.end();

        //爬取1920*1080图片资源链接
        timeUtils.start("爬取1920*1080图片资源链接");
        Set<String> imgSources = new HashSet<>();
        for (String htm_1920_1080_link : htm_1920_1080_links) {
            LinkedList<String> targetURLS = SimpleURLCrawlerUtil.getTargetURLS(htm_1920_1080_link, imgSourcePattern);
            imgSources.addAll(targetURLS);
        }
        timeUtils.end();

        List<String> results = new LinkedList<>(imgSources);
        return results;
    }


    //生成分页链接 :      http://www.netbian.com/meinv/index_[?].htm
    //参数: 前缀,后缀,起始页数,结束页数
    private static LinkedList<String> getPageLink(String prefix,String suffix,int start,int end){
        LinkedList<String> results = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            results.add(prefix+i+suffix);
        }
        return results;
    }
}
