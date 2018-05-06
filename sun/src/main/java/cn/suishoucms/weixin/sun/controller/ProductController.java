package cn.suishoucms.weixin.sun.controller;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;

import cn.suishoucms.weixin.sun.entity.Product;
import cn.suishoucms.weixin.sun.mapper.ProductMapper;
import cn.suishoucms.weixin.sun.service.ProductService;
import cn.suishoucms.weixin.sun.utils.Result;

@Controller
public class ProductController {

	@Resource
	private ProductService productService;
	
	@Resource
	private ProductMapper productMapper;

	@RequestMapping("addOrUpdateProduct")
	@ResponseBody
	public Object addOrUpdateProduct(Product product) throws ExecutionException {

		return productService.addOrUpdateProduct(product);
	}

	@RequestMapping("getProductByCode")
	@ResponseBody
	public Object getProductByCode(Integer storeId, String code) {
		Result result = Result.success("");
		result.data("product", productService.getProductByCode(storeId, code));
		return result;
	}
	
	
	@RequestMapping("selectByPage")
	@ResponseBody
	public Object selectByPage(Integer storeId,Integer offset ,Integer limit){
		
		if(offset==null){
			offset=0;
			
		}
		if(limit==null){
			limit=20;
		}

		Result result = Result.success("");
		Page<Product> page=productMapper.selectByPage(storeId, new PageRowBounds(offset, limit));
		result.data("page",productMapper.selectByPage(storeId, new PageRowBounds(offset, limit)));
		return result;
	}
	
	
	

}
