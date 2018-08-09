package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	public EasyUIDataGridResult getContentListByCategoryId(Long categoryId, Integer pn, Integer ps);

	public E3Result saveContent(TbContent content);

	public E3Result editContent(TbContent content);

	public E3Result deleteContents(String ids);

}
