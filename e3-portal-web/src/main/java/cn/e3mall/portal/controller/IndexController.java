package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.pojo.TbContent;
import cn.e3mall.service.ContentService;

/**
 * 首页
 * 
 * @author leroy
 *
 */
@Controller
public class IndexController {

	@Value("${INDEX_PAGE_BANNER_CONTENT_ID}")
	private Long INDEX_PAGE_BANNER_CONTENT_ID;

	@Autowired
	private ContentService contentService;

	/**
	 * 直接访问localhost:8082,会访问index.html,
	 * 找不到文件就会去找controller,然后找到index,返回index.jsp
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		List<TbContent> list = contentService.getContentsByCategoryId(INDEX_PAGE_BANNER_CONTENT_ID);
		model.addAttribute("ad1List", list);
		return "index";
	}
}
