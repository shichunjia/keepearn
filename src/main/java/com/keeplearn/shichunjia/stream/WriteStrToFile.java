package com.keeplearn.shichunjia.stream;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

public class WriteStrToFile {

//    public static void main(String[] args)  {
//        try {
//            BufferedWriter out = new BufferedWriter(new FileWriter("D:\\test\\active2.txt"));
//            for(int i=1;i<=100000000;i++){
//                out.write("fe44654||"+"189"+i+"||"+new Date().getTime()+"\n");
//            }
//            out.close();
//            System.out.println("文件创建成功！");
//        } catch (IOException e) {
//        }
//    }


    public static void main(String[] args) {
        try {
            long start=System.currentTimeMillis();
            Stream<String> lines = Files.lines(Paths.get("D:\\test\\active2.txt"));
            long count = lines.filter(s->s.contains("189999997")).count();
            long end=System.currentTimeMillis();
            System.out.println(count);
            System.out.println(end-start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
