package imdb;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.io.*;

public class GetPicByDouban {
	public static void main(String[] args) {
		Date d1 = new Date();
		
		System.out.println("let start...");
		
		ArrayList<String> urls = new ArrayList<String>();
		for(int i=0;i<=10;i++) {
			urls.add("https://movie.douban.com/people/97152342/collect?sort=time&amp;start="+Integer.toString(i*15)+"&amp;filter=all&amp;mode=grid&amp;tags_sort=count");
			
		}
		
		String dir = "E://android/demoByMy/douban/img/";  // 图片储存目录
		
		for(String url : urls) {
			int index = urls.indexOf(url)+1;
			System.out.println("开始下载第"+index+"张图片");
			getPictures(url,dir);
			System.out.println("第"+index+"张图片下载完毕");
		}
		
		System.out.println("end");
		Date d2 = new Date();
		
		long seconds = (d2.getTime() - d1.getTime())/1000;
		System.out.println("一共用时"+seconds);
	
	}
	
	
	
	public static void getPictures(String url, String dir) {
		
		URL urlObj = null;
		
		try {
			urlObj = new URL(url);
		}catch(MalformedURLException e) {
			System.out.println("the url was malformed");
		}
		
		URLConnection urlCon = null;
		try {
			urlCon = urlObj.openConnection();
			Document doc = Jsoup.parse(urlCon.getInputStream(),"utf-8",url);
			Elements up_tag = doc.select(".grid-view");
			Elements up = up_tag.first().getElementsByClass("item");
			
//			System.out.println(pic_block.size());
//			
//			System.out.println(up);
//			System.out.println(elems);
			
			for(int i=0;i<up.size();i++) {
				Elements elems = up.get(i).select(".pic");
				Elements pic_block = elems.first().select("a");
				
				
				
			    String picture_url = pic_block.select("img").attr("src");
			    String picture_name = pic_block.attr("title");
			    download(picture_url,dir,picture_name);
			    System.out.println("一张图片下载完毕");
			}
		}catch(IOException e) {
			System.out.println("this url was malformed");
		}
	}
	
	
	public static void download(String url,String dir,String filename) {
		try { 
			URL httpurl = new URL(url);
			File dirfile = new File(dir);
			
			 if (!dirfile.exists()) {    
	                dirfile.mkdirs();  
	            }  
			 System.out.println(filename);
			 FileUtils.copyURLToFile(httpurl, new File(dir+filename+".jpg"));  
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	


}
