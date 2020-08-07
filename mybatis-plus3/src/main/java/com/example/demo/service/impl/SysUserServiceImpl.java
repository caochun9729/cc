package com.example.demo.service.impl;

import com.example.demo.entity.SysUser;
import com.example.demo.dao.SysUserDao;
import com.example.demo.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author cc
 * @since 2020-08-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

}
