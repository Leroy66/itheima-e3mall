package cn.e3mall.common.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @ClassName: WXServiceNumDTO 
* @Description: 微信服务号关注事件DTO
* @author wzl
* @date 2017年12月11日 下午7:14:21 
*  
*/
@SuppressWarnings("serial")
public class WXTokenDTO implements java.io.Serializable {
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "WXTokenDTO{" +
				"accessToken='" + accessToken + '\'' +
				", expiresIn=" + expiresIn +
				'}';
	}
}
