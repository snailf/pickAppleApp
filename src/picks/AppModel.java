/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

import java.util.List;

/**
 *
 * @author koudejian
 */
public class AppModel {
    String id = "0";   //site id
    String tid = "0";
    String url = null;
    String name = null;
    String is_picked = null;
    List<String> images = null;
    
    
    String applog = null;
    String applogs = null;
    String download = null;
    String versionTime = null;
    String versionNmae = null;
    String developer = null;
    String description = null;
    String tag = null;
    public AppModel(String url){
        this.url = url;
    }
    public AppModel(String id, String tid, String name, String url, String is_picked){
        this.id = id;
        this.tid = tid;
        this.url = url;
        this.name = name;
        this.is_picked = is_picked;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_picked(String is_picked) {
        this.is_picked = is_picked;
    }

    public void setApplog(String applog) {
        this.applog = applog;
    }

    public void setApplogs(String applogs) {
        this.applogs = applogs;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public void setVersionTime(String versionTime) {
        this.versionTime = versionTime;
    }

    public void setVersionNmae(String versionNmae) {
        this.versionNmae = versionNmae;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTid() {
        return tid;
    }

    public String getIs_picked() {
        return is_picked;
    }

    public String getApplog() {
        return applog;
    }

    public String getApplogs() {
        return applogs;
    }

    public String getDownload() {
        return download;
    }

    public String getVersionTime() {
        return versionTime;
    }

    public String getVersionNmae() {
        return versionNmae;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getDescription() {
        return description;
    }
    
    public void setImages(List<String> images){
        this.images = images;
    }
    
    public List<String> getImages(){
        return this.images;
    }
    
    public String getName(){
        return name;
    }
    
    public String getId(){
        return id;
    }
    
    public String getUrl(){
        return url;
    }
    
    
    public String getImageUrl(){
        String result = "";
        for(int i = 0; i < images.size(); i ++){
            if(i == 0){
                result += Config.ALIYUN_IMAGE_URL + MD5.getFileName(images.get(i));
            }else{
                result += ";" + Config.ALIYUN_IMAGE_URL + MD5.getFileName(images.get(i));
            }
        }
        return result;
    }
}
