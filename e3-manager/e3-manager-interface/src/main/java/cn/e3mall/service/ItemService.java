package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {

	public TbItem getItemById(Long id);

	public EasyUIDataGridResult getItemList(Integer pn, Integer ps);

	public E3Result addItem(TbItem item, String desc);

	public E3Result editItem(TbItem item, String desc);

	public E3Result getDescById(Long id);

	public E3Result delItemByIds(String ids);

	public E3Result instockItemByIds(String ids);

	public E3Result reshelfItem(String ids);

}
