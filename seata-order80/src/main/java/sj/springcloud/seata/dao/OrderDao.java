package sj.springcloud.seata.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sj.springcloud.seata.entity.Order;

@Mapper
public interface OrderDao {
    /**
     * 创建订单
     */
    void create(Order order);

    /**
     * 修改订单状态，从0改为1
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
