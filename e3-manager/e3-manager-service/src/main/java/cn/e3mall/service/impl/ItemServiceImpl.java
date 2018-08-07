package cn.e3mall.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItem getItemById(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer pn, Integer ps) {
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		PageHelper.startPage(pn, ps);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> page = new PageInfo<>(list);
		result.setRows(list);
		result.setTotal(page.getTotal());
		return result;
	}

	// 不用考虑两张表的同步吗？？？？
	@Override
	public E3Result addItem(TbItem item, String desc) {
		long id = IDUtils.genItemId();
		item.setId(id);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insert(item);

		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(itemDesc);
		return E3Result.ok();
	}

	@Override
	public E3Result editItem(TbItem item, String desc) {
		TbItem itemModel = tbItemMapper.selectByPrimaryKey(item.getId());
		if (itemModel != null) {
			// 商品状态，1-正常，2-下架，3-删除
			item.setStatus((byte) 1);
			item.setCreated(itemModel.getCreated());
			item.setUpdated(new Date());
			tbItemMapper.updateByPrimaryKey(item);
		}

		if (StringUtils.isNotEmpty(desc)) {
			TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(item.getId());
			if (itemDesc != null) {
				itemDesc.setItemDesc(desc);
				itemDesc.setUpdated(new Date());
				tbItemDescMapper.updateByPrimaryKey(itemDesc);
			}
		}
		return E3Result.ok();
	}

	@Override
	public E3Result getDescById(Long id) {
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return E3Result.ok(itemDesc);
	}

	@Override
	public E3Result delItemByIds(String ids) {
		List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		for (Long id : idList) {
			TbItem itemModel = tbItemMapper.selectByPrimaryKey(id);
			if (itemModel != null) {
				// 商品状态，1-正常，2-下架，3-删除
				itemModel.setStatus((byte) 3);
				itemModel.setUpdated(new Date());
				tbItemMapper.updateByPrimaryKey(itemModel);
			}
		}
		return E3Result.ok();
	}

	@Override
	public E3Result instockItemByIds(String ids) {
		List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		for (Long id : idList) {
			TbItem itemModel = tbItemMapper.selectByPrimaryKey(id);
			if (itemModel != null) {
				// 商品状态，1-正常，2-下架，3-删除
				itemModel.setStatus((byte) 2);
				itemModel.setUpdated(new Date());
				tbItemMapper.updateByPrimaryKey(itemModel);
			}
		}
		return E3Result.ok();
	}

	@Override
	public E3Result reshelfItem(String ids) {
		List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		for (Long id : idList) {
			TbItem itemModel = tbItemMapper.selectByPrimaryKey(id);
			if (itemModel != null) {
				// 商品状态，1-正常，2-下架，3-删除
				itemModel.setStatus((byte) 1);
				itemModel.setUpdated(new Date());
				tbItemMapper.updateByPrimaryKey(itemModel);
			}
		}
		return E3Result.ok();
	}

}
