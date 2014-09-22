package picks.tasks;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import picks.Config;
import picks.MD5;
import picks.adapter.ALiYunAdapter;


/**
 * download images and 
 * @author snail
 * created at 2013-11-01 16:31:02
 * modified at 2013-11-03 12:24:45
 */
public class PickImageTask{

	private List<String> imageList = null;
	
	public PickImageTask(List<String> images) {
		this.imageList = images;
	}
	
	public void start() {
//		System.out.println("\nstart pick images! total:" + imageList.size());
		for(int i = 0; i < imageList.size(); i++){
			String temp = imageList.get(i);
			if(Config.debug()){
				System.out.println("url" + temp.toString());
			}
			if(downloadImages(temp, MD5.getFileName(temp))){
                try {
                    //下载成功，上传云服务器
                    if(!ALiYunAdapter.saveImage(MD5.getFileName(temp), Config.IMAGE_DOCUMENT + MD5.getFileName(temp))){
//                      System.out.println(temp.toString());
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PickImageTask.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		}
		
	}
    
    
    
	/**
	 * pick image from urls and store by name.
	 * @param urls : image's url
	 * @param name : image's name
	 * @return boolean : if success return true,or return false.
	 */
	public boolean downloadImages(String urls, String name){
		try {
			URL url = new URL(urls);
			File outFile = new File(Config.IMAGE_DOCUMENT + name);
			OutputStream os = new FileOutputStream(outFile);
			InputStream is = url.openStream();
			byte[] buff = new byte[10240];
			while(true) {
				int readed = is.read(buff);
				if(readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			is.close(); 
	        os.close();
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(Config.debug()){
				e.printStackTrace();
			}
			return false;
		}
	}
}
