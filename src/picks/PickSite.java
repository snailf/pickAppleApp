/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author koudejian
 */
public class PickSite {
    private int mSum = 0;
    
    public PickSite(int sum){
        mSum = sum;
    }
    
    public void start(){
        int length = mSum / 20 + 1;
        for(int i = 1; i < length; i++){
            try {
                this.getPage(i);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void getPage(int page) throws IOException{
        String urls = "http://huaban.com/search/?q=app%20%E5%BC%95%E5%AF%BC%E9%A1%B5&hyubwukg&page=" + page + "&per_page=20&wfl=1";
        Document html = null;
        
        html = Jsoup
					.connect(urls)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5")
					.get();
        

        String htmls = html.toString();
        int start = htmls.indexOf("app.page[\"pins\"]");
        int end = htmls.indexOf("app.page[\"category\"]");

        String jstr = htmls.substring(start + "app.page[\"pins\"] = ".length(), end);

        try {
            JSONArray arr = new JSONArray(jstr);
            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = (JSONObject) arr.get(i);
                
                String pin_id = obj.getString("pin_id");
                String app_name = obj.getString("raw_text");
                String link = obj.getString("link");
                
                html = Jsoup
					.connect("http://218.244.156.133/foreplay/index.php?r=api/addAppSite&id=" + pin_id + "&name=" + app_name + "&url=" + link)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5")
					.get();
                System.out.println("http://218.244.156.133/foreplay/index.php?r=api/addAppSite&id=" + pin_id + "&name=" + app_name + "&url=" + link + html.toString());

            }
        } catch (JSONException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
