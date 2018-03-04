package cn.suishoucms.weixin.sun.mapper;

import javax.annotation.Resource;

import org.junit.Test;

import cn.suishoucms.weixin.sun.entity.Product;
import common.BaseTest;

public class ProductMapperTest extends BaseTest {
	
	@Resource
	ProductMapper productMapper;
	
	@Test
	public void test(){
	 Product product=	productMapper.selectByPrimaryKey(1);
	 
	// assertNull("OK", product);
	}
}
