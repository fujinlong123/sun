package test.test;



import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageRowBounds;

import cn.suishoucms.weixin.sun.entity.Product;
import cn.suishoucms.weixin.sun.mapper.ProductMapper;
import common.BaseTest;

public class Pageplugin extends BaseTest {

	@Resource
	private ProductMapper productMapper;
	
	
	@Test
	public void test(){
		PageRowBounds pb=new PageRowBounds(2, 10);
		List<Product> productList=productMapper.selectByPage(17, pb);
		System.out.println();
	}
	
	
}
