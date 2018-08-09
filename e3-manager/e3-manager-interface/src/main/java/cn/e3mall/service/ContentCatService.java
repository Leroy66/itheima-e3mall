package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

public interface ContentCatService {

	public List<EasyUITreeNode> getContentCatListByParentId(Long parentId);

	public E3Result createContentCat(Long parentId, String name);

	public E3Result updateContentCatNameById(Long id, String name);

	public E3Result deleteContentCatById(Long id);

}
