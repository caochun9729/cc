package com.cc.crawl;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * java 爬数据
 */
public class Demo {

    public  Document getDocument(String url){
        try {
            return Jsoup.connect(url).get();


        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static void main(String[] args) {

        Demo t=new Demo();//实例化
        Document doc=t.getDocument("https://s.weibo.com/top/summary?cate=realtimehot");//双引号里面填写网址
        Elements elements1=doc.select("tbody"); //双引号里面填写母标签
        Elements elements2=elements1.select("tr");//双引号填写子标签，大家可以多尝试
        Elements elements3=elements2.select("a");//双引号填写子标签，大家可以多尝试
        for(int i=0;i<51;i++){
            String A  =elements2.get(i).text();
            String B = elements3.get(i).text();
            System.out.println(A);
        }//拿稳微博热搜示例 犹豫每一行文字都是在tr里面 所以便循环输出一键搞定
        //这个for循环的意思是选取 tbody标签下的所有tr标签从0开始循环输出 犹豫微博热搜最多50个所以只循环50次
    }

}
