package picks.adapter;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import picks.Config;

/**
 * @author snail
 * created at 2013-11-01 12:00:12
 */

public class ALiYun{
	private String mKeyId = Config.ALIYUN_ACCESS_KEY;
	private String mKeySecret = Config.ALIYUN_ACCESS_KEY_SECRET;
		
	public static ALiYun INSTANCE = new ALiYun();
		
	private static OSSClient client = null;
		
	public ALiYun() {
		// 初始化一个OSSClient
        client = new OSSClient(mKeyId, mKeySecret);
	}
	public boolean putObject(String bucketName, String key, String filePath) throws FileNotFoundException {
		if(client == null){
			client = new OSSClient(mKeyId, mKeySecret);
		}
        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(file.length());
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content, meta);
        // 打印ETag
        if(Config.debug()){
        	System.out.println(result.getETag());
        }
        return (result == null) ? false : true;
    }
    public void listObjects(String bucketName) {
        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }
    public InputStream getObject(String bucketName, String key) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        return objectContent;
//        // 处理Object
//        System.out.print(objectContent);
//        // 关闭流
//        objectContent.close();
    }
    public static ALiYun getInstance(){
    	return INSTANCE;
    }
}
