package picks;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
public class MD5 {
	public static String md5Encode(String str)
	{
		StringBuffer buf = new StringBuffer();
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte bytes[] = md5.digest();
			for(int i = 0; i < bytes.length; i++)
			{
				String s = Integer.toHexString(bytes[i] & 0xff);
				if(s.length()==1){
					buf.append("0");
				}
				buf.append(s);
			}
		}
		catch(Exception ex){}
		return buf.toString();
	}
	public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
	    try {
	        in = new FileInputStream(file);
	        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(byteBuffer);
	        BigInteger bi = new BigInteger(1, md5.digest());
	        value = bi.toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if(null != in) {
                try {
                	in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
            }
	    }
	    return value;
    }
    
    public static String getFileName(String url){
        return (MD5.md5Encode(url) + ".png");
    }
    
}
