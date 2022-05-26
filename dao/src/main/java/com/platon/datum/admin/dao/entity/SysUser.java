package com.platon.datum.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 
 * 系统用户表 管理系统用户登陆信息
 */
@Getter
@Setter
@ToString
public class SysUser implements Serializable {
    /**
     * 序号
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 钱包地址
     */
    private String address;

    /**
     * 用户状态 1：可用, 0:不可用
     */
    private Integer status;

    /**
     * 是否为管理员 1管理员，0非管理
     */
    private Integer isAdmin;

    /**
     * 最后更新时间
     */
    private LocalDateTime recUpdateTime;

    /**
     * 创建时间
     */
    private LocalDateTime recCreateTime;

    private static final long serialVersionUID = 1L;
}