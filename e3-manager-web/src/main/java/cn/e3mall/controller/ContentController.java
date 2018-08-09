package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.service.ContentService;

/**
 * content分类
 * 
 * @author leroy
 *
 */
@RequestMapping(value = "/content")
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * 内容查询
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/query/list")
	@ResponseBody()
	public EasyUIDataGridResult queryList(Long categoryId, Integer page, Integer rows) {
		return contentService.getContentListByCategoryId(categoryId, page, rows);
	}

	/**
	 * 新增
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody()
	public E3Result save(TbContent content) {
		return contentService.saveContent(content);
	}

	/**
	 * 修改
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody()
	public E3Result edit(TbContent content) {
		return contentService.editContent(content);
	}

	/**
	 * 批量删除 逗号隔开id
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody()
	public E3Result delete(String ids) {
		return contentService.deleteContents(ids);
	}

}
