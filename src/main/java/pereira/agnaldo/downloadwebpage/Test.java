package pereira.agnaldo.downloadwebpage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Author: CalcYu
 * Date: 2019/3/28
 */
public class Test {
    public static void main(String[] args) {
        File savePath = new File("./tmp/");
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        File input = new File(savePath, "input.html");
        if (!input.exists()) {
            try {
                input.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Document doc = Jsoup.connect("http://baidu.com").get();
            Elements js = doc.select("script[src]");
            Elements css = doc.select("link[href$=.css]");
            Elements img = doc.select("img[src]");
            for (Element e : js) {
                System.out.println(e.tagName()+"："+e.attr("abs:src"));
            }
            for (Element e : css) {
                System.out.println(e.tagName()+"："+e.attr("abs:href"));
            }
            for (Element e : img) {
                System.out.println(e.tagName()+"："+e.attr("abs:src"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
