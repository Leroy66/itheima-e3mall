package cn.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * 
 * @author leroy
 *
 */
@Controller
public class IndexController {

	/**
	 * 直接访问localhost:8082,会访问index.html,
	 * 找不到文件就会去找controller,然后找到index,返回index.jsp
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}
}
