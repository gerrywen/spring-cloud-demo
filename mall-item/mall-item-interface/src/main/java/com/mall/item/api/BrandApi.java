package com.mall.item.api;

import com.mall.config.FeignConfig;
import com.mall.item.api.hystrix.BrandApiHystrix;
import com.mall.item.pojo.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * program: spring-cloud-mall->BrandApi
 * description: 品牌接口
 * author: gerry
 * created: 2019-11-24 23:11
 **/
@FeignClient(value = "mall-item", fallback = BrandApiHystrix.class, configuration = FeignConfig.class)
@RequestMapping("brand")
public interface BrandApi {
    /**
     * 根据品牌id集合，查询品牌信息
     * @param ids
     * @return
     */
    @GetMapping("list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}