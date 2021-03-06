/*
 *    系统名称   ： 扒取功能实现
 *    
 *    (C) Copyright davidking 2016
 *    All Rights Reserved.
 *	  
 *    注意： 本内容仅限于网络传阅，禁止商业使用
 */
package cn.wetime.p2pmart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wetime.p2pmart.config.Cache;
import cn.wetime.p2pmart.engine.WebCrawlerEngine;
import cn.wetime.p2pmart.pojo.Product;
import cn.wetime.p2pmart.util.JsonUtil;
import flexjson.JSONDeserializer;
@SuppressWarnings("all")
@Service
@Transactional
public class ProductService extends BaseService {

//	@Autowired
//	private ICache cache;

	public List<Product> saveOrUpdateBatchProducts() {
		List<Product> products = null;
		try {
			products = WebCrawlerEngine.newInstance().doCrawler();
			//需要修改的列表
			List<Product> needModifiedLists = new ArrayList<Product>();
			//需要保存的列表項
			List<Product> needSavedLists = new ArrayList<Product>();
			//需要迁移的列表项
			List<Product> needMovedLists = new ArrayList<Product>();
			//是否需要缓存
			boolean needCache = false;
			for (Product product : products) {
				System.out.println(product+" "+product.getProfitMode());
				String productJson = Cache.newInstance().get(
						product.getProductId());
				
				JSONDeserializer<Product> deserial = new JSONDeserializer<Product>();  
				if (productJson != null && !productJson.equals("")){
					Product cacheProduct = deserial.deserialize(productJson,Product.class);
					
					//修改的商品
					if(cacheProduct.getProgress()!=product.getProgress()){
						needModifiedLists.add(product);
						needCache = true;
					}
					
				} else{
					//需要新保存的商品
					if(product.getProductId()!=null){
						needSavedLists.add(product);
						needCache = true;
					}
					//缓存中加入
					
				}
				if(needCache){
					Cache.newInstance().set(product.getProductId(),
							JsonUtil.serialOffClass(product));
				}
			}

			saveBatchProducts(needSavedLists);
			updateBatchProductsProgress(needModifiedLists);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return products;
	}

	/**
	 * 批量保存产品
	 */
	public void saveBatchProducts(List<Product> products) {
		this.getProductDao().saveBatch(products);
	}

	public void updateBatchProducts(List<Product> products) {

		 this.getProductDao().updateBatch(products);
	}
	
	public Product getProductById(String id){
		return this.getProductDao().get(Product.class, id);
	}
	
	public void updateBatchProductsProgress(List<Product> products) {

		for (Product product : products) {
			Product persist = this.getProductDao().get(Product.class,
					product.getProductId());
			persist.setProgress(product.getProgress());
		}
	}
}
