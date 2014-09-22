/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author koudejian
 */
public class PickAppInfo {
    
    public static AppModel getAppModel(AppModel appModel){
        String url = null;
        if(appModel != null){
            url = appModel.getUrl();
        }
//        System.out.println(appModel.getId() + ":" + appModel.getName() );
//        url = "https://itunes.apple.com/cn/app/qian-niu-tao-bao-guan-fang/id590217303?mt=8";
        Document document = HttpHelper.httpGet(url);
        if(document == null){
            return null;
        }
        //title
        if(appModel.getName() == null){
            Elements left_title = document.getElementsByClass("left");
            if(left_title != null){
                
                String title = getTextStr("<h1>", "</h1>", left_title.toString());
                if(title != null){
                    appModel.setName(title);
//                    System.out.println(title);
                }
            }
            if(appModel.getName() == null){
                return null;
            }
        }
        //*
        Element left = document.getElementById("left-stack");
        if(left == null){
            return null;
        }
        String left_content = left.toString();
        appModel.setApplogs(getLogo(left_content));

        //其他内容
        int p = isSubString("<li class=\"genre\">", left_content);
        String temp = left_content.substring(p + "<li class=\"genre\">".length(), left_content.length());
        String tag = getTextStr("<a href=\"" + url + "\">", "</a>", temp);
        tag = replaceBlank(tag);
        temp = getTextStr("<li class=\"release-date\">", null, temp);
        String date = getTextStr("</span>", "</li>", temp);
        date = date.replace("年", "-").replace("月", "-").replace("日", "");
        temp = getTextStr("<li>", null, temp);
        String version = getTextStr("</span>", "</li>", temp);
        temp = getTextStr("<li>", null, temp);
        temp = getTextStr("<li>", null, temp);
        String developer = getTextStr("</span>", "</li>", temp);
        
        Elements right = document.getElementsByClass("center-stack");
        if(right == null){
            return null;
        }
        appModel.setImages(getAppImages(document));
        String descript = getTextStr("class=\"product-review\">", "</div>", right.toString()).replaceAll("<[A-z/ =']*>", "");
        descript = descript.replace("<h4> 内容提要 </h4>", "");
        descript = replaceBlank(descript);
        
        appModel.setDescription(descript);
        appModel.setDeveloper(developer);
        appModel.setVersionTime(date);
        appModel.setVersionNmae(version);
        appModel.setTag(tag);
        
        return appModel;
        
        //*/
    }

    /**
     * get logo
     * @param source
     * @return 
     */
    public static String getLogo(String source){
        String logo = null;
        int position = isSubString("src-swap-high-dpi=\"", source);
        if(position == -1){
            return null;
        }
        String temp = source.substring(position + "src-swap-high-dpi=\"".length(), source.length());
        int end = isSubString("\"", temp);
        if(end == -1){
            return null;
        }
        logo = temp.substring(0, end);
//        System.out.println("logo -> " + logo);
        return logo;
    }
    /**
     * 获取images
     * @param url
     * @return 
     */
    public static List<String> getAppImages(Document document){
        List<String> result = new ArrayList<String>();

        if(document == null){
            return null;
        }
        Elements element = document.getElementsByClass("lockup");
        if(element == null){
            return null;
        }
//        System.out.println(element.toString());
        String key = "src=\"";
        String endKey = "\"";
        for(int i = 0; i < element.size(); i++){
            Element tmp = element.get(i);
            String source = tmp.toString();
            int position = isSubString(key, source);
            if(position == -1){
                break;
            }
            String temp = source.substring(position + key.length(), source.length());
            int end = temp.indexOf(endKey);
            if(end == -1){
                break;
            }
            String imageUrl = temp.substring(0, end);
            
            if(!"https://s.mzstatic.com/htmlResources/610E/frameworks/images/p.png".equals(imageUrl)){
//                System.out.println(imageUrl);
                result.add(imageUrl);
            }
            
        }
        return result;
    }
    
    private static int isSubString(String key, String source){
        if(source == null){
            return -1;
        }
        return source.indexOf(key);
    }
    public static String getTextStr(String start, String end, String source){
        String result = null;
        if(source == null){
            return null;
        }
        if(start != null){
            int p = isSubString(start, source);
            result = source.substring(p + start.length(), source.length());
            source = result;
        }
        if(end != null){
            int p = isSubString(end, source);
            result = source.substring(0, p);
        }
        if(result != null){
//            System.out.println( "\n\n\n\n\n" + result + "\n\n\n\n\n");
        }
        
        return result;
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
