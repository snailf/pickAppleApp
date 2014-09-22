/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author koudejian
 */
public class HttpHelper {
    
    public static Document httpGet(String url){
        if(url == null){
            return null;
        }
        Document html = null;
        System.out.println(url);
        try {
            html = Jsoup
                    .connect(url)
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5")
                    .header("Content-type", "text/html;charset=utf-8")
                    .get();
            
        } catch (IOException ex) {
            Logger.getLogger(HttpHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return html;
    }
    public static String getUrl(String url){
        if(url == null){
            return null;
        }
        return url.replaceAll("&", "%26")
                .replaceAll(" ", "%20")
                .replaceAll("?", "%3F")
                .replaceAll("|", "%124")
                .replaceAll("%", "%25")
                .replaceAll("+", "%2B")
                .replaceAll("/", "%2F")
                .replaceAll("#", "%23")
                .replaceAll("=", "%3D")
                ;
    }
    
}
