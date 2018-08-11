package cn.e3mall.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import cn.e3mall.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${CONTENT_LIST_REDIS_HASHSET_KEY}")
	private String CONTENT_LIST_REDIS_HASHSET_KEY;
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;

	/**
	 * 后台使用
	 */
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
		try {
			jedisClient.hdel("CONTENT_LIST_REDIS_HASHSET_KEY", content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		try {
			jedisClient.hdel("CONTENT_LIST_REDIS_HASHSET_KEY", content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContents(String ids) {
		List<Long> idList = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		for (TbContent tbContent : list) {
			tbContentMapper.deleteByPrimaryKey(tbContent.getId());
			try {// 更新redis
				jedisClient.hdel("CONTENT_LIST_REDIS_HASHSET_KEY", tbContent.getCategoryId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentsByCategoryId(Long categoryId) {

		try {// 去redis缓存里读取数据
			String redisString = jedisClient.hget("CONTENT_LIST_REDIS_HASHSET_KEY", categoryId.toString());
			List<TbContent> redisList = JsonUtils.jsonToList(redisString, TbContent.class);
			if (CollectionUtils.isNotEmpty(redisList)) {
				return redisList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(JsonUtils.objectToJson(e));
		}

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);

		try {// 去redis 写入缓存数据
			jedisClient.hset("CONTENT_LIST_REDIS_HASHSET_KEY", categoryId.toString(), JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(JsonUtils.objectToJson(e));
		}
		return list;
	}

}
