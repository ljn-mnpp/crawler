package com.ljn.crawler.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author李胖胖
 * @Date 2019/1/11 21:05
 * @Description: 爬取目标资源工具类
 **/
public class SimpleURLCrawlerUtil {

    //测试链接
    public static void main(String[] args) throws IOException {
        String urlStr = "http://www.netbian.com/desk/21359-1920x1080.htm";
        URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("Accept","*/*");
        urlConnection.addRequestProperty("Accept-Charset","UTF-8");
        urlConnection.addRequestProperty("Accept-Language","zh-CN,zh");
        urlConnection.addRequestProperty("Content-type","UTF-8");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String s = null;

        Pattern p = Pattern.compile("http://img\\.netbian\\.com/file/.+?\\.jpg");
        Matcher m = p.matcher("");

        List<String> results = new ArrayList<>();
        while ((s =bf.readLine())!=null){
            System.out.println(s);
            m.reset(s);
            while (m.find()){
                String group = m.group();
                results.add(group);
            }
        }
        bf.close();
        CollectionUtil.sout(results);
    }
    /**
     <div class="list">
     <ul><li><a href="/desk/21290.htm" title="��-KiKi��ֽ ����ʱ�䣺2018-11-23" target="_blank"><img src="http://img.netbian.com/file/newc/5f95bd72f8f76f39075bf3b9b7baf90d.jpg" alt="��-KiKi��ֽ" /><b>��-KiKi��ֽ</b></a></li><li><a href="/desk/21275.htm" title="�����崿��Ů������ֽ ����ʱ�䣺2018-11-20" target="_blank"><img src="http://img.netbian.com/file/newc/febec1874efd2feba4fa1a02f383da0e.jpg" alt="�����崿��Ů������ֽ" /><b>�����崿��Ů������ֽ</b></a></li><li>
     <div class="pic_box"><a href="http://pic.netbian.com/" target="_blank"><img src="http://img.netbian.com/file/2018/1225/604a688cd6f79161236e6250189bc25b.jpg" alt="4K/5K/8K�����ֽ" /></a></div><p><a href="http://pic.netbian.com/" target="_blank" style="color:#FF0000;">��4K/5K/8K�����ֽ��</a></p>
     </li><li><a href="/desk/21281.htm" title="��Ů�����ɫ��ɴ�����ֽ ����ʱ�䣺2018-11-20" target="_blank"><img src="http://img.netbian.com/file/newc/4d77ba96c64499db95829de5e542f257.jpg" alt="��Ů�����ɫ��ɴ�����ֽ" /><b>��Ů�����ɫ��ɴ�����ֽ</b></a></li><li><a href="/desk/21268.htm" title="���� ë������ȹ��Ů��ֽ ����ʱ�䣺2018-11-16" target="_blank"><img src="http://img.netbian.com/file/newc/a5429d666648922c450b33b89b84ce14.jpg" alt="���� ë������ȹ��Ů��ֽ" /><b>���� ë������ȹ��Ů��ֽ</b></a></li><li><a href="/desk/20786.htm" title="���� ��ɫ�����Ը������Ů��ֽ ����ʱ�䣺2018-11-16" target="_blank"><img src="http://img.netbian.com/file/newc/96f562d68478a3c452333c51997459da.jpg" alt="���� ��ɫ�����Ը������Ů��ֽ" /><b>���� ��ɫ�����Ը������Ů��ֽ</b></a></li><li><a href="/desk/21253.htm" title="��˼�� ��ɫȹ��������Ů��ֽ ����ʱ�䣺2018-11-13" target="_blank"><img src="http://img.netbian.com/file/newc/5677c993737c8b5f55a19ec1e081e21a.jpg" alt="��˼�� ��ɫȹ��������Ů��ֽ" /><b>��˼�� ��ɫȹ��������Ů��ֽ</b></a></li><li><a href="/desk/21252.htm" title="������ �Ӽ�������Ů��ֽ ����ʱ�䣺2018-11-13" target="_blank"><img src="http://img.netbian.com/file/newc/e95de9c5a894e64233aa98c3d2597314.jpg" alt="������ �Ӽ�������Ů��ֽ" /><b>������ �Ӽ�������Ů��ֽ</b></a></li><li><a href="/desk/21251.htm" title="������ ����������Ů��ֽ ����ʱ�䣺2018-11-13" target="_blank"><img src="http://img.netbian.com/file/newc/7a8a7bd8c4b6fae389932f11f5fa15aa.jpg" alt="������ ����������Ů��ֽ" /><b>������ ����������Ů��ֽ</b></a></li><li><a href="/desk/21241.htm" title="��˼�� �Ը�������Ůģ�ر�ֽ ����ʱ�䣺2018-11-09" target="_blank"><img src="http://img.netbian.com/file/newc/d47b32f68304f1e062fedfd14d9293bd.jpg" alt="��˼�� �Ը�������Ůģ�ر�ֽ" /><b>��˼�� �Ը�������Ůģ�ر�ֽ</b></a></li><li><a href="/desk/21240.htm" title="����������Ů��˼����ֽ ����ʱ�䣺2018-11-09" target="_blank"><img src="http://img.netbian.com/file/newc/998cbfe3142fe509265e56ab0c0a3d6e.jpg" alt="����������Ů��˼����ֽ" /><b>����������Ů��˼����ֽ</b></a></li><li><a href="/desk/21225.htm" title="���� ������Ůģ�ر�ֽ ����ʱ�䣺2018-11-05" target="_blank"><img src="http://img.netbian.com/file/newc/63511f8e12480d19905986f7d112e6ba.jpg" alt="���� ������Ůģ�ر�ֽ" /><b>���� ������Ůģ�ر�ֽ</b></a></li><li><a href="/desk/21224.htm" title="����Ů�񵤵�������Ů��ֽ ����ʱ�䣺2018-11-05" target="_blank"><img src="http://img.netbian.com/file/newc/2e7f395a08fe0e146ee8595a7621d2a0.jpg" alt="����Ů�񵤵�������Ů��ֽ" /><b>����Ů�񵤵�������Ů��ֽ</b></a></li><li><a href="/desk/21217.htm" title="ѩ����ֽ ����ʱ�䣺2018-11-01" target="_blank"><img src="http://img.netbian.com/file/newc/e2c99d15942820c02aa4231f3435352e.jpg" alt="ѩ����ֽ" /><b>ѩ����ֽ</b></a></li><li><a href="/desk/21202.htm" title="������ֽ ����ʱ�䣺2018-10-29" target="_blank"><img src="http://img.netbian.com/file/newc/aa2bebef01eda96fc198845977911162.jpg" alt="������ֽ" /><b>������ֽ</b></a></li><li><a href="/desk/21205.htm" title="ƽ��ҹ�ɰ���Ů����ֽ ����ʱ�䣺2018-10-29" target="_blank"><img src="http://img.netbian.com/file/newc/1616bf6aa71ea757991c692374a67be2.jpg" alt="ƽ��ҹ�ɰ���Ů����ֽ" /><b>ƽ��ҹ�ɰ���Ů����ֽ</b></a></li><li><a href="/desk/21204.htm" title="����Ů������ֽ ����ʱ�䣺2018-10-29" target="_blank"><img src="http://img.netbian.com/file/newc/fbdbfd8a46857923bd712acc603cba78.jpg" alt="����Ů������ֽ" /><b>����Ů������ֽ</b></a></li><li><a href="/desk/21203.htm" title="������Ů������ֽ ����ʱ�䣺2018-10-29" target="_blank"><img src="http://img.netbian.com/file/newc/3355bcd36ceb2a2df6abefec6f878d09.jpg" alt="������Ů������ֽ" /><b>������Ů������ֽ</b></a></li></ul>
     <div>
     **/


    //核心递归爬取方法 参数: 种子链接  目标模式  结果集
    public static LinkedList<String> getTargetURLS(String urlStr, String target_pattern){
        LinkedList<String> results = new LinkedList<>();
        try {
            //链接目标url,获取流
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bfr = null;

            try {
                bfr = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

                //设置匹配模式
                Pattern p_target = Pattern.compile(target_pattern);
                Matcher m_target = p_target.matcher("");

                String s = null;
                while ((s = bfr.readLine())!=null){
                    m_target.reset(s);
                    while (m_target.find()){
                        String group = m_target.group();
                        results.add(group);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bfr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }


}
