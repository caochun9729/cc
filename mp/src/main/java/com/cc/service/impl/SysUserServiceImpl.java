package com.cc.service.impl;

import com.cc.entity.SysUser;
import com.cc.dao.SysUserDao;
import com.cc.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2019-11-12
 */
@Service("SysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

}
