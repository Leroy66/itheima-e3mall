package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.service.ContentCatService;

/**
 * content分类
 * 
 * @author leroy
 *
 */
@RequestMapping(value = "/content/category")
@Controller
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;

	@RequestMapping(value = "/list")
	@ResponseBody()
	public List<EasyUITreeNode> list(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		return contentCatService.getContentCatListByParentId(parentId);
	}

	@RequestMapping(value = "/create")
	@ResponseBody()
	public E3Result createContentCat(Long parentId, String name) {
		return contentCatService.createContentCat(parentId, name);
	}

	@RequestMapping(value = "/update")
	@ResponseBody()
	public E3Result updateContentCatNameByParentId(Long id, String name) {
		return contentCatService.updateContentCatNameById(id,name);
	}
	@RequestMapping(value = "/delete")
	@ResponseBody()
	public E3Result delete(Long id) {
		return contentCatService.deleteContentCatById(id);
	}
}
