/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package picks;

/**
 *
 * @author koudejian
 */
public class Config {
    public static boolean debug(){
        return true;
    }
    //applist api
    public static String APP_LIST_URL = "http://218.244.156.133/foreplay/index.php?r=api/getAppleAppSiteList&limit=400";
    public static String ADD_APP_URL = "http://218.244.156.133/foreplay/index.php?r=api/addAppleAppInfo&";
    
    //阿里云云存储
	public final static String ALIYUN_ACCESS_KEY = "Hyd1SB3qpfTRf4zp";
	public final static String ALIYUN_ACCESS_KEY_SECRET = "vszZADUFjlmt5BoIMtzxywam2hHSsa";
	public final static String ALIYUN_BUCKET_NAME = "foreplay";
	public final static String ALIYUN_IMAGE_URL = "http://foreplay.oss-cn-hangzhou.aliyuncs.com/";
    
    
    public static final String IMAGE_DOCUMENT = "/tmp/";
    
}
