package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;

public interface ItemService {

	public TbItem getItemById(Long id);

	public EasyUIDataGridResult getItemList(Integer pn, Integer ps);

}
