/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import picks.tasks.PickImageTask;

/**
 *
 * @author koudejian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length > 0){
            String url = args[0];
//            String url = "https://itunes.apple.com/cn/app/qian-niu-tao-bao-guan-fang/id590217303?mt=8";
            pickApp(new AppModel(url));
        }else{
            /**
             * 获取app列表
             */
            //*
            Document document = HttpHelper.httpGet(Config.APP_LIST_URL);

            Elements body = document.getElementsByTag("body");
            String html = body.toString();
            String data = body.toString().substring("<body>".length(), html.indexOf("</body>"));
            data = data.replaceAll("&quot;", "\"");
    //        System.out.println(data);

            List<AppModel> AppList = new ArrayList<AppModel>();
            try {
                JSONArray arr = new JSONArray(data);
                for(int i = 0; i < arr.length(); i++){
                    JSONObject obj = (JSONObject) arr.get(i);

                    String id = obj.getString("id");
                    String tid = obj.getString("tid");
                    String name = obj.getString("name");
                    String url = obj.getString("url");
                    String isPick = obj.getString("is_picked");

                    AppList.add(new AppModel(id, tid, name, url, isPick));
                }
            } catch (JSONException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(int i = 0; i < AppList.size(); i++){
                pickApp(AppList.get(i));
            }
            //*/
        }  
    }
    
    private static void pickApp(AppModel appModel){
        appModel = PickAppInfo.getAppModel(appModel);
        //*
        String url = Config.ADD_APP_URL + 
                "&id=" + appModel.getId() +
                "&tid=" + appModel.getTid() + 
                "&name=" + URLEncoder.encode(appModel.getName()) + 
                "&images=" + appModel.getImageUrl() +
                "&download_ur=" + appModel.getUrl() +
                "&logo=" + appModel.getApplogs()+
                "&version=" + URLEncoder.encode(appModel.getVersionNmae())+ 
                "&date=" + URLEncoder.encode(appModel.getVersionTime()) + 
                "&developer=" + URLEncoder.encode(appModel.getDeveloper()) + 
                "&descript=" + URLEncoder.encode(appModel.getDescription())
                + "&tag=" + URLEncoder.encode(appModel.getTag())
                ;
        Document appresult = HttpHelper.httpGet(url);
        if(appresult != null){
//            System.out.println(appresult.toString());
            String t = appresult.getElementsByTag("body").toString();
            if(t.indexOf("5") != -1){
                PickImageTask pickImageTask = new PickImageTask(appModel.getImages());
                pickImageTask.start();
                System.out.println("pickdone");
            }else if(t.indexOf("2") != -1){
                System.out.println("pickrepick");
            }
        }
        //*/
    }
}
