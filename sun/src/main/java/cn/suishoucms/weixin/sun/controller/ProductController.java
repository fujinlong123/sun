package cn.suishoucms.weixin.sun.controller;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suishoucms.weixin.sun.entity.Product;
import cn.suishoucms.weixin.sun.service.ProductService;
import cn.suishoucms.weixin.sun.utils.Result;

@Controller
public class ProductController {

	@Resource
	private ProductService productService;

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

}
