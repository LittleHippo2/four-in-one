package com.csse.sync.fourinone.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 读取properties文件
     * @param paramFile
     * @throws Exception
     */
    public static String getProperties(String paramFile,String key) throws Exception{
        Properties props=new Properties();//使用Properties类来加载属性文件
        FileInputStream iFile = new FileInputStream(paramFile);
        props.load(iFile);

        /**begin*******直接遍历文件key值获取*******begin*/
       /* Iterator<String> iterator = props.stringPropertyNames().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println(key+":"+props.getProperty(key));
        }*/
        /**end*******直接遍历文件key值获取*******end*/

        /**begin*******在知道Key值的情况下，直接getProperty即可获取*******begin*/

        String value = props.getProperty(key);
        /*String user=props.getProperty("user");
        String pass=props.getProperty("pass");
        System.out.println("\n"+user+"\n"+pass);*/
        /**end*******在知道Key值的情况下，直接getProperty即可获取*******end*/
        iFile.close();
        return value;

    }

    /**
     * 想properties写入文件
     * @param paramFile
     * @throws IOException
     */
    public static void outputFile(String paramFile, Map<String,String> paramMap) throws Exception {
        ///保存属性到b.properties文件
        Properties props=new Properties();
        new File(paramFile).delete();
        FileOutputStream oFile = new FileOutputStream(paramFile, true);//true表示追加打开
        for(Map.Entry<String,String> entry:paramMap.entrySet()){
            props.setProperty(entry.getKey(),entry.getValue());
        }

        props.store(oFile, "The New properties file Annotations"+"\n"+"Test For Save!");
        oFile.close();
    }

    public static void main(String[] args) throws Exception {

        String rootPath = ResourceUtils.getURL("classpath:").getPath();
        String deployPath = rootPath+"deploy.properties";

        Map<String,String> param = new HashMap<String,String>();
        param.put("app_id","qqq");
        param.put("app_secret","qqqqqq");


        outputFile(deployPath,param);

        String value = getProperties(deployPath,"aa");
        if (value==null){
            System.out.printf("aaaa");
        }
    }
}
