package com.warm.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.common.DBTypeEnum;
import com.warm.common.DataSourceSwitch;
import com.warm.system.entity.Order;
import com.warm.system.entity.User;
import com.warm.system.mapper.OrderMapper;
import com.warm.system.mapper.UserMapper;
import com.warm.system.service.db1.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2018-02-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<User> getUserList() {

        return selectList(null);
    }

    @Override
    public void addUser() {
        User user = new User();
        user.setName("cc");
        insert(user);
        int i=1/0;
    }

    @DataSourceSwitch(DBTypeEnum.db2)
    @Override
    public void addTOrder() {
        Order order = new Order();
        order.setOrderNo("999999");
        order.setUserId(121);
        orderMapper.insert(order);
        int i=1/0;
    }

    @DataSourceSwitch(DBTypeEnum.db2)
    @Override
    public BigDecimal getOrderPriceByUserId(Integer userId) {
        return orderMapper.getPriceByUserId(userId);
    }
}
