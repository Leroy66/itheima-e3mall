package e3.mall.test;

import org.junit.Test;

public class NormalTest {
	
	
	/**
	 * 将上传的录音转为mp3格式
	 * @param webroot 项目的根目录
	 * @param sourcePath 文件的相对地址
	 */
	public static void ToMp3(String webroot, String sourcePath){
		//File file = new File(sourcePath);
		String targetPath = sourcePath+".mp3";
		//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名
		Runtime run = null;  
		try {  
			run = Runtime.getRuntime();  
			long start=System.currentTimeMillis();  
			//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
			Process p=run.exec(webroot+"/ffmpeg/bin -i "+webroot+sourcePath+" -acodec libmp3lame "+webroot+targetPath);
			//释放进程  
			p.getOutputStream().close();  
			p.getInputStream().close();  
			p.getErrorStream().close();  
			p.waitFor();  
			long end=System.currentTimeMillis();  
			System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");  
			//删除原来的文件  
			//if(file.exists()){  
				//file.delete();  
			//}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}finally{  
			//run调用lame解码器最后释放内存  
			run.freeMemory();  
		}
	}

	@Test
	public void testmmm(){
	    ToMp3("C:/Users/leroy/Desktop/","1");
	}

}
