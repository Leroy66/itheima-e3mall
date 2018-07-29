package e3.mall.pageHelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class PageHelperTest {

	@Test
	public void testPageHelper() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = ac.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);
		TbItemExample exp = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(exp);
		PageInfo<TbItem> page = new PageInfo<>(list);
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(list.size());
	}
	
	

}
