package cn.e3mall.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * QiNiuUploadUtils请求工具类
 * 
 * @author Leroy
 */
public final class QiNiuUploadUtils {

	final static Logger logger = LoggerFactory.getLogger(QiNiuUploadUtils.class);
	final static String QI_NIU_RESULT_URL_PRE = "访问的前缀";
	final static String QI_NIU_ACCESSKEY = "账号里会提供";
	final static String QI_NIU_SECRETKEY = "账号里会提供";
	final static String QI_NIU_BUCKET = "账号里会提供";

	/**
	 * 上传获取七牛token
	 * 
	 * @param paramData
	 * @return
	 */
	public static String getUpToken() {
		String upToken = "";
		try {
			logger.info("QiNiuUploadUtils getUpToken 获取七牛上传token");
			Auth auth = Auth.create(QI_NIU_ACCESSKEY, QI_NIU_SECRETKEY);
			StringMap putPolicy = new StringMap();
			long expireSeconds = 300;
			upToken = auth.uploadToken(QI_NIU_BUCKET, null, expireSeconds, putPolicy);
			logger.info(String.format("QiNiuUploadUtils getUpToken 返回值upToken: %s", upToken));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(String.format("QiNiuUploadUtils getUpToken error: %s", e));
		}
		return upToken;
	}

	/**
	 * 本地文件上传
	 * @param key
	 * @param localFilePath
	 * @return
	 */
	public static String simpleUploadLocalFile(String key, String localFilePath){
		String upToken = getUpToken();
		try {
			// 构造一个带指定Zone对象的配置类
			Configuration cfg = new Configuration(Zone.zone0());
			// ...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			logger.info(String.format("simpleUploadLocalFile key[%s]  upToken[%s] localFilePath[%s]", key, upToken, localFilePath));
			Response response = uploadManager.put(localFilePath, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			logger.info("simpleUploadLocalFile 返回结果：response:[%s] key:[%s] hash:[%s]", JsonUtils.objectToJson(response), putRet.key,
					putRet.hash);
			// 拼接完整路径
			String fullUrl = QI_NIU_RESULT_URL_PRE + putRet.key;
			logger.info("simpleUploadLocalFile 返回最终url:" + fullUrl);
			return fullUrl;
		} catch (QiniuException e) {
			e.printStackTrace();
			logger.info("上传七牛失败：" + JsonUtils.objectToJson(e));
		}
		return "";
	}
	
	
	/**
	 * 字节数组上传
	 * @param key
	 * @param uploadBytes
	 * @return
	 */
	public static String simpleUploadBytes(String key,  byte[] uploadBytes){
		String upToken = getUpToken();
		try {
			// 构造一个带指定Zone对象的配置类
			Configuration cfg = new Configuration(Zone.zone0());
			// ...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			logger.info(String.format("simpleUploadBytes key[%s]  upToken[%s]", key, upToken));
			Response response = uploadManager.put(uploadBytes, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			logger.info(String.format("simpleUploadBytes 返回结果：response:[%s] key:[%s] hash:[%s]", JsonUtils.objectToJson(response), putRet.key,
					putRet.hash));
			// 拼接完整路径
			String fullUrl = QI_NIU_RESULT_URL_PRE + putRet.key;
			logger.info("simpleUploadBytes 返回最终url:" + fullUrl);
			return fullUrl;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("上传七牛失败：" + JsonUtils.objectToJson(e));
		}
		return "";
	}
}
