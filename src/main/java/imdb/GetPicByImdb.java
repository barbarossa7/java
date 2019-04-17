package imdb;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.io.FileUtils;

public class GetPicByImdb {
	public static void main(String[] args) {
        Date d1 = new Date();
        
        System.out.println("爬虫开始......");
        
        // 爬取的网址列表，一共十个网页
        ArrayList<String> urls = new ArrayList<String>();
//        urls.add("http://www.imdb.cn/IMDB250/");
        for(int i=2; i<=10; i++) {
            urls.add("http://www.imdb.cn/imdb250/"+ Integer.toString(i));
        }
        
        String dir = "E:/android/demoByMy/douban/img";  // 图片储存目录
        
        // 利用循环下载每个页面中的图片
        for(String url: urls) {
            int index = urls.indexOf(url)+1;
            System.out.println("开始下载第"+index+"个网页中的图片...");
            getPictures(url, dir);
            System.out.println("第"+index+"个网页中的图片下载完毕！\n");
        }
        
        System.out.println("程序运行完毕！");
        Date d2 = new Date();
        
        // 计算程序的运行时间，并输出
        long seconds = (d2.getTime()-d1.getTime())/1000;
        System.out.println("一共用时： "+seconds+"秒.");
        
    }
    
    // getContent()函数: 将网页中的电影图片下载到本地
    public static void getPictures(String url, String dir){
        
        // 利用URL解析网址
        URL urlObj = null;
        try{
            urlObj = new URL(url);

        }
        catch(MalformedURLException e){
            System.out.println("The url was malformed!");
        }

        // URL连接
        URLConnection urlCon = null;
        try{
            // 打开URL连接
            urlCon = urlObj.openConnection(); 
            // 将HTML内容解析成UTF-8格式
            Document doc = Jsoup.parse(urlCon.getInputStream(), "utf-8", url);
            // 提取电影图片所在的HTML代码块
            Elements elems = doc.getElementsByClass("ss-3 clear");
            Elements pic_block = elems.first().getElementsByTag("a");
            
            for(int i=0; i<pic_block.size(); i++) {
                // 提取电影图片的url, name
                String picture_url = pic_block.get(i).getElementsByTag("img").attr("src");
                String picture_name = pic_block.get(i).getElementsByClass("bb").text()+".jpg";
                // 用download()函数将电影图片下载到本地
                download(picture_url, dir, picture_name);
                System.out.println("第"+(i+1)+"张图片下载完毕！");
            }
            
        }
        catch(IOException e){
            System.out.println("There was an error connecting to the URL");
        }

    }
    
    // download()函数利用图片的url将图片下载到本地
    public static void download(String url, String dir, String filename) {  
        try { 
            
            /* httpurl: 图片的url
             * dirfile: 图片的储存目录
             */
            URL httpurl = new URL(url);  
            File dirfile = new File(dir); 
            
            // 如果图片储存的目录不存在，则新建该目录
            if (!dirfile.exists()) {    
                dirfile.mkdirs();  
            }  
            
            // 利用FileUtils.copyURLToFile()实现图片下载
            FileUtils.copyURLToFile(httpurl, new File(dir+filename));  
        } 
        catch(Exception e) {  
            e.printStackTrace();  
        }  
    }


}
