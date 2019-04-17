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
        
        System.out.println("���濪ʼ......");
        
        // ��ȡ����ַ�б�һ��ʮ����ҳ
        ArrayList<String> urls = new ArrayList<String>();
//        urls.add("http://www.imdb.cn/IMDB250/");
        for(int i=2; i<=10; i++) {
            urls.add("http://www.imdb.cn/imdb250/"+ Integer.toString(i));
        }
        
        String dir = "E:/android/demoByMy/douban/img";  // ͼƬ����Ŀ¼
        
        // ����ѭ������ÿ��ҳ���е�ͼƬ
        for(String url: urls) {
            int index = urls.indexOf(url)+1;
            System.out.println("��ʼ���ص�"+index+"����ҳ�е�ͼƬ...");
            getPictures(url, dir);
            System.out.println("��"+index+"����ҳ�е�ͼƬ������ϣ�\n");
        }
        
        System.out.println("����������ϣ�");
        Date d2 = new Date();
        
        // ������������ʱ�䣬�����
        long seconds = (d2.getTime()-d1.getTime())/1000;
        System.out.println("һ����ʱ�� "+seconds+"��.");
        
    }
    
    // getContent()����: ����ҳ�еĵ�ӰͼƬ���ص�����
    public static void getPictures(String url, String dir){
        
        // ����URL������ַ
        URL urlObj = null;
        try{
            urlObj = new URL(url);

        }
        catch(MalformedURLException e){
            System.out.println("The url was malformed!");
        }

        // URL����
        URLConnection urlCon = null;
        try{
            // ��URL����
            urlCon = urlObj.openConnection(); 
            // ��HTML���ݽ�����UTF-8��ʽ
            Document doc = Jsoup.parse(urlCon.getInputStream(), "utf-8", url);
            // ��ȡ��ӰͼƬ���ڵ�HTML�����
            Elements elems = doc.getElementsByClass("ss-3 clear");
            Elements pic_block = elems.first().getElementsByTag("a");
            
            for(int i=0; i<pic_block.size(); i++) {
                // ��ȡ��ӰͼƬ��url, name
                String picture_url = pic_block.get(i).getElementsByTag("img").attr("src");
                String picture_name = pic_block.get(i).getElementsByClass("bb").text()+".jpg";
                // ��download()��������ӰͼƬ���ص�����
                download(picture_url, dir, picture_name);
                System.out.println("��"+(i+1)+"��ͼƬ������ϣ�");
            }
            
        }
        catch(IOException e){
            System.out.println("There was an error connecting to the URL");
        }

    }
    
    // download()��������ͼƬ��url��ͼƬ���ص�����
    public static void download(String url, String dir, String filename) {  
        try { 
            
            /* httpurl: ͼƬ��url
             * dirfile: ͼƬ�Ĵ���Ŀ¼
             */
            URL httpurl = new URL(url);  
            File dirfile = new File(dir); 
            
            // ���ͼƬ�����Ŀ¼�����ڣ����½���Ŀ¼
            if (!dirfile.exists()) {    
                dirfile.mkdirs();  
            }  
            
            // ����FileUtils.copyURLToFile()ʵ��ͼƬ����
            FileUtils.copyURLToFile(httpurl, new File(dir+filename));  
        } 
        catch(Exception e) {  
            e.printStackTrace();  
        }  
    }


}
