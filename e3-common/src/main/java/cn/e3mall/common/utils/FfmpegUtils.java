package cn.e3mall.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 音频转换
 * 
 * @author leroy 
 * 服务器上需要先安装ffmpeg,lame, ( yum 安装)
 * 服务器上需要先创建对应文件夹 
 * maven依赖
 *
 * 参考文章：https://blog.csdn.net/qq_33563609/article/details/78469071
 */
public final class FfmpegUtils {

	final static Logger logger = LoggerFactory.getLogger(FfmpegUtils.class);
	// 服务器上 ffmpeg 执行文件路径
	final static String FFMPEG_CMD_PATH = "/usr/bin/ffmpeg";
	// 服务器本地转换后临时保存文件的前缀
	final static String FFMPEG_EXCHANGED_LOCAL_AUDIO_PRE = "/ffmpeg/audio/";
	// 上传到七牛文件的前缀
	final static String FFMPEG_EXCHANGED_QINIU_AUDIO_PRE = "/ffmpeg/audio/";

	/*
	 * 先转换文件到本地, commend 执行需要时间
	 * 再上传七牛,
	 * 再删除本地文件
	 */
	public static String audioToMp3(String sourcePath) {
		// 转换后文件的存储地址
		String targetPath = System.currentTimeMillis() + ".mp3";
		List<String> commend = new ArrayList<String>();
		commend.add(FFMPEG_CMD_PATH);
		commend.add("-i");
		commend.add(sourcePath);
		commend.add("-acodec");
		commend.add("libmp3lame");
		commend.add(FFMPEG_EXCHANGED_LOCAL_AUDIO_PRE + targetPath);
		StringBuffer cmdStr = new StringBuffer();
		for (int i = 0; i < commend.size(); i++) {
			cmdStr.append(commend.get(i) + " ");
		}
		logger.info("----cmdStr:" + cmdStr);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(commend);
		try {
			builder.redirectErrorStream(true);
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("音频转换失败");
		}
		try {// 等待文件写入完成再去上传
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("音频转换成功,准备上传七牛");
		//服务器上临时文件的地址
		return FFMPEG_EXCHANGED_LOCAL_AUDIO_PRE + targetPath;
	}
}
