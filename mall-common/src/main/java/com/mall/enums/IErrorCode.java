package com.mall.enums;

/**
 * program: chengjie-ts->IErrorCode
 * description: 错误返回实现类
 * author: gerry
 * created: 2019-08-05 11:13
 **/
public interface IErrorCode {
    long getStatus();

    String getMessage();
}
