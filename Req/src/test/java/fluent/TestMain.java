package fluent;

import java.io.InputStream;


//import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Request;

import cn.com.davidking.util.FileUtil;
import cn.com.davidking.util.StringUtil;

public class TestMain {

	public static void main(String[] args) throws Exception{
		String url = "http://imgwx4.2345.com/dypcimg/tv/img/0/15/s47163.jpg?1470012740";
		
		String fileName = StringUtil.getJpgFileName(url);
		InputStream is = Request
			.Get(url)
			.execute()
			.returnContent().asStream();
		
		FileUtil.writeJpgToDisk(is,"TV", fileName);
//		BufferedImage image = null;  
//        image=ImageIO.read(is); 
//		ImageIO.write(image, "jpg", new File(FileUtil.dir+fileName)); 
		
		
//		System.out.println(fileName);
//		String srt = StringUtil.getSuffixByUrl(url);
//		
//		System.out.println(srt);
	}

}
