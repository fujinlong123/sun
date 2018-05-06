package cn.suishoucms.weixin.sun.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.suishoucms.weixin.sun.entity.Product;
import cn.suishoucms.weixin.sun.entity.User;
import cn.suishoucms.weixin.sun.mapper.ProductMapper;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.Result;

@Service
public class ProductService {
	@Resource
	private ProductMapper productMapper;

	@Resource
	private SessionService sessionService;

	@Resource
	private UserService userService;

	public Result addOrUpdateProduct(Product product) throws ExecutionException {
		if (product.getStoreId() == null) {
			return Result.fail("保存失败");
		}
		Product record = productMapper.selectByStoreIdAndCode(product.getStoreId(), product.getCode());
		if (record != null) {
			product.setId(record.getId());
			init(product);
			productMapper.updateByPrimaryKeySelective(product);
		} else {
			init(product);
			productMapper.insertSelective(product);
		}
		return Result.success("保存成功");

	}

	public Product getProductByCode(Integer storeId, String code) {
		return productMapper.selectByStoreIdAndCode(storeId, code);
	}
	
	public List<Product> query(Integer storeId, Integer endId) {
		return productMapper.query(storeId, endId);
	}

	private void init(Product record) throws ExecutionException {
		ShareSession shareSession = sessionService.getShareSession();
		User user = userService.selectByOpenId(shareSession.getWeiXinLoginInfo().getOpenid());
		Date now = new Date();
		if (record.getId() == null) {
			record.setCreateTime(now);
			record.setUpdateTime(now);
			record.setCreatorId(user.getId());
			record.setModifierId(user.getId());
		} else {
			if (record.getCreateTime() == null) {
				record.setCreateTime(now);
			}
			if (record.getCreatorId() == null) {
				record.setCreatorId(user.getId());
			}
			record.setUpdateTime(now);
			record.setModifierId(user.getId());
		}
	}
	
	

}
