package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 商品详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long id) {
		return itemService.getItemById(id);
	}

	/**
	 * 商品列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		return itemService.getItemList(page, rows);
	}

	/**
	 * 添加商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		return itemService.addItem(item, desc);
	}

	/**
	 * 编辑商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	@ResponseBody
	public E3Result editItem(TbItem item, String desc) {
		return itemService.editItem(item, desc);
	}
	
	
	/**
	 * 获取商品描述详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rest/item/query/item/desc/{id}",method=RequestMethod.GET)
	@ResponseBody
	public E3Result getDescById(@PathVariable Long id) {
		return itemService.getDescById(id);
	}
	

	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result delItem(String ids) {
		return itemService.delItemByIds(ids);
	}
	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public E3Result instockItem(String ids) {
		return itemService.instockItemByIds(ids);
	}
	/**
	 * 上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public E3Result reshelfItem(String ids) {
		return itemService.reshelfItem(ids);
	}
}
