package cn.e3mall.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import cn.e3mall.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGridResult getContentListByCategoryId(Long categoryId, Integer pn, Integer ps) {
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		PageHelper.startPage(pn, ps);

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> page = new PageInfo<>(list);

		result.setRows(list);
		result.setTotal(page.getTotal());
		return result;
	}

	@Override
	public E3Result saveContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		return E3Result.ok();
	}

	@Override
	public E3Result editContent(TbContent content) {
		if (content.getId() == null) {
			return E3Result.build(403, "参数有误", "content id 不能为空");
		}
		TbContent con = tbContentMapper.selectByPrimaryKey(content.getId());
		if (con == null) {
			return E3Result.build(404, "数据不存在", "该 id:" + content.getId() + " 数据不存在");
		}
		content.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKey(content);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContents(String ids) {
		List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		tbContentMapper.deleteByExample(example);
		return E3Result.ok();
	}

}
