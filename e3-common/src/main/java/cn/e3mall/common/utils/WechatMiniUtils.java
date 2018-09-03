package cn.e3mall.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.e3mall.common.pojo.WXTokenDTO;


/**
 * 微信小程序部分工具类
 * 
 * @author Leroy
 */
public final class WechatMiniUtils {
	final static Logger logger = LoggerFactory.getLogger(WechatMiniUtils.class);
	final static String WX_MINI_ACCESSTOKEN_FETCH_URL_TPL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	final static String WX_MINI_APPID = "具体账号的 appid";
	final static String WX_MINI_APPSECRET = "具体账号的 appsecret";

	/**
	 * 获取小程序的token
	 * 
	 * @return
	 */
	public static String getWXminiAccessToken() {
		// 微信获取url路径，小程序appId,appSecret.
		try {
			String wxAccessTokenReqURL = String.format(WX_MINI_ACCESSTOKEN_FETCH_URL_TPL, WX_MINI_APPID,
					WX_MINI_APPSECRET);
			logger.info("WechatMiniUtils getWXminiAccessToken request url:" + wxAccessTokenReqURL);
			String wxAccessTokenRes = HttpUtils.sendGet(wxAccessTokenReqURL);
			logger.info("WechatMiniUtils getWXminiAccessToken response：" + wxAccessTokenRes);
			WXTokenDTO wxToken = JsonUtils.jsonToPojo(wxAccessTokenRes, WXTokenDTO.class);
			return wxToken.getAccessToken();
		} catch (Exception e) {
			logger.info("WechatMiniUtils getWXminiAccessToken response：" + JsonUtils.objectToJson(e));
		}
		return "";
	}
	
	
	/**
	 * 获取二维码，返回的是  byte[]
	 * @param sceneStr
	 * @param page
	 * @param accessToken
	 * @param savePath
	 * @return
	 */
	public static byte[] getMiniQrCode(String sceneStr,String page ,String accessToken,String savePath) {
		RestTemplate rest = new RestTemplate();
		try {
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
			Map<String, Object> param = new HashMap<>();
			param.put("scene", sceneStr);
			if(StringUtils.isNoneBlank(page)){
				param.put("page", page);
			}
			param.put("width", 430);
			param.put("auto_color", false);
			Map<String, Object> line_color = new HashMap<>();
			line_color.put("r", 0);
			line_color.put("g", 0);
			line_color.put("b", 0);
			param.put("line_color", line_color);
			logger.info("调用生成微信URL接口传参:" + JsonUtils.objectToJson(param));
			MultiValueMap headers = new LinkedMultiValueMap<>();
			HttpEntity requestEntity = new HttpEntity(param, headers);
			ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class,
					new Object[0]);
			logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + JsonUtils.objectToJson(entity.getBody()));
			return entity.getBody();
		} catch (Exception e) {
			logger.info("调用小程序生成微信永久小程序码URL接口异常" + JsonUtils.objectToJson(e));
		}
		return null;
	}
}
