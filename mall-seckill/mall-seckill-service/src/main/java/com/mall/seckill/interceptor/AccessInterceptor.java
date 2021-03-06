package com.mall.seckill.interceptor;

import com.mall.auth.entity.UserInfo;
import com.mall.common.base.response.CodeMsg;
import com.mall.common.base.response.Result;
import com.mall.common.base.utils.JsonUtils;
import com.mall.common.redis.enums.CtimsModelEnum;
import com.mall.common.redis.utils.CommonRedisUtils;
import com.mall.seckill.access.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: 98050
 * @Time: 2018-11-23 23:45
 * @Feature: 接口限流拦截器
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommonRedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null){
                return true;
            }

            //获取用户信息
            UserInfo userInfo = LoginInterceptor.getLoginUser();
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin){
                if (userInfo == null){
                    render(response, CodeMsg.LOGIN_ERROR);
                    return false;
                }
                key += "_" + userInfo.getId();
            }else {
                //不需要登录，则什么也不做
            }
            // 获取Redis
            String count = redisUtils.get(CtimsModelEnum.CTIMS_SECKILL_CAP, key);
            if (count == null){
                redisUtils.set(CtimsModelEnum.CTIMS_SECKILL_CAP, key,"1",seconds * 60);
            }else if(Integer.valueOf(count) < maxCount){
                redisUtils.incr(CtimsModelEnum.CTIMS_SECKILL_CAP, key,1);
            }else {
                render(response,CodeMsg.ACCESS_LIMIT_REACHED);
            }

        }

        return super.preHandle(request, response, handler);
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JsonUtils.serialize(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
