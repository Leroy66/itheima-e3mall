package cn.e3mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.JsonUtils;

/**
 * 页面控制器
 * 
 * @author leroy
 *
 */
@Controller
public class PictureController {

	/**
	 * 并没有接受数据，没有上传服务器
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "pic/upload")
	public String picUpload() {
		List<String> imgList = imagesResources();
		Random r = new Random();
		int index = r.nextInt(imgList.size());
		Map map = new HashMap();
		map.put("error", 0);
		map.put("url", imgList.get(index));
		return JsonUtils.objectToJson(map);
	}

	private List<String> imagesResources() {
		List<String> imgList = new ArrayList<String>();
		imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3651936737,1395733516&fm=27&gp=0.jpg");
		imgList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1123510543,1480723284&fm=27&gp=0.jpg");
		imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1579116934,2015076696&fm=27&gp=0.jpg");
		imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3797866960,2852353684&fm=27&gp=0.jpg");
		imgList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2524529283,695478125&fm=27&gp=0.jpg");
		imgList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=46118457,3547114960&fm=27&gp=0.jpg");
		imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2196846333,3740415138&fm=27&gp=0.jpg");
		imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3312879786,1917248499&fm=27&gp=0.jpg");
		imgList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=293407890,1214763655&fm=27&gp=0.jpg");
		imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4238405886,381438205&fm=27&gp=0.jpg");
		return imgList;
	}
}
