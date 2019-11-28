package com.mall.admin.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UmsAdminRoleDTO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;


//    @ApiModelProperty(value = "后台用户数量")
//    private Integer adminCount;
//
//    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
//    private Integer status;
}
