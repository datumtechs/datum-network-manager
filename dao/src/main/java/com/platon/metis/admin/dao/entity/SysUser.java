package com.platon.metis.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

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
     * 密码 MD5加密
     */
    private String password;

    /**
     * 用户状态 enabled：可用, disabled:不可用
     */
    private String status;

    /**
     * 是否为管理员 1管理员，0非管理
     */
    private String isMaster;

    /**
     * 最后更新时间
     */
    private Date recUpdateTime;

    private static final long serialVersionUID = 1L;
}