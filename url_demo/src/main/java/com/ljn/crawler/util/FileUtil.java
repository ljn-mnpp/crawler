package com.ljn.crawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Author李胖胖
 * @Date 2019/1/11 21:44
 * @Description:
 **/
public class FileUtil {

    //将list集合持久化到文档
    public static void saveList(File file, List<String> list,boolean append){
        FileWriter fw = null;
        BufferedWriter bfw = null;
        try {
            fw = new FileWriter(file,append);
            bfw = new BufferedWriter(fw);
            for (String o : list) {
                bfw.write(o);
                bfw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bfw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
