package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import cn.e3mall.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCatListByParentId(Long parentId) {
		List<EasyUITreeNode> list = new ArrayList<EasyUITreeNode>();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1);
		List<TbContentCategory> ContentCategorys = tbContentCategoryMapper.selectByExample(example);
		for (TbContentCategory cat : ContentCategorys) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent() ? "closed" : "open");
			list.add(node);
		}
		return list;
	}

	@Override
	public E3Result createContentCat(Long parentId, String name) {
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (parentCat != null) {
			TbContentCategory cat = new TbContentCategory();
			cat.setParentId(parentId);
			cat.setName(name);
			cat.setIsParent(false);
			cat.setStatus(1);// 1(正常),2(删除)
			cat.setSortOrder(1);
			cat.setCreated(new Date());
			cat.setUpdated(new Date());
			// 修改sql才会返回id
			tbContentCategoryMapper.insert(cat);
			if (!parentCat.getIsParent()) {
				parentCat.setIsParent(true);
				tbContentCategoryMapper.updateByPrimaryKey(parentCat);
			}
			return E3Result.ok(cat);
		} else {
			System.out.println("数据不存在");
			return E3Result.build(404, "父类数据不存在", "parentId:" + parentId + "数据不存在");
		}

	}

	@Override
	public E3Result updateContentCatNameById(Long id, String name) {
		TbContentCategory cat = tbContentCategoryMapper.selectByPrimaryKey(id);
		if (cat == null) {
			System.out.println("数据不存在");
			return E3Result.build(404, "数据不存在", "id:" + id + "数据不存在");
		} else {
			cat.setName(name);
			cat.setUpdated(new Date());
			tbContentCategoryMapper.updateByPrimaryKey(cat);
		}
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCatById(Long id) {
		TbContentCategory cat = tbContentCategoryMapper.selectByPrimaryKey(id);
		if (cat == null) {
			System.out.println("数据不存在");
			return E3Result.build(404, "数据不存在", "id:" + id + "数据不存在");
		} else {
			cat.setStatus(2);// 1(正常),2(删除)
			cat.setUpdated(new Date());
			tbContentCategoryMapper.updateByPrimaryKey(cat);
			// 处理父类
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(1);
			criteria.andParentIdEqualTo(cat.getParentId());
			int count = tbContentCategoryMapper.countByExample(example);
			if (count == 0) {
				TbContentCategory record = new TbContentCategory();
				record.setIsParent(false);
				record.setUpdated(new Date());
				tbContentCategoryMapper.updateByPrimaryKeySelective(record);
			}
		}
		return E3Result.ok();
	}

}
