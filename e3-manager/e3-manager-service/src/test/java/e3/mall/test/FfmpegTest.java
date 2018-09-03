package e3.mall.test;

import java.io.File;

public class FfmpegTest {
 
	public static void main(String[] args) {
        String sPath = "http://image1.gopopon.com/1535594367726_866018037063455_video_repeat";
        String tPath = "C:/Users/leroy/Desktop/XXXX";
        try {
            new FfmpegTest().changeAmrToMp3(sPath,tPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void changeAmrToMp3(String sourcePath, String targetPath) throws Exception {
        String webroot = "C:/Users/leroy/Desktop/ffmpeg/bin";
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start=System.currentTimeMillis();
            System.out.println(new File(webroot).getAbsolutePath());
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            //wav转pcm
            //Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
            //mp3转pcm
            Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            long end=System.currentTimeMillis();
            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
    } 
    
    
}
