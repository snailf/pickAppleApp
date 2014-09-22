package picks.adapter;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import picks.Config;


/**
 * 
 * @author snail
 * created at 2013-11-01 12:00:12
 */
public class ALiYunAdapter extends ALiYun{
	public static String mBucketName = Config.ALIYUN_BUCKET_NAME;
	
	public static ALiYun aliyun = ALiYun.getInstance();
	
	public ALiYunAdapter() {
		super();
        // 新建一个Bucket
//      client.createBucket(bucketName);
	}
	public static boolean saveImage(String key, String filePath) throws FileNotFoundException {
        return aliyun.putObject(mBucketName, key, filePath);
    }
	public static InputStream getImage(String key) throws IOException{
		return aliyun.getObject(mBucketName, key);
	}
}
