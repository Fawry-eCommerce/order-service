package com.fawry.orderservice.mapper;

import com.fawry.orderservice.entity.Orders;
import com.fawry.orderservice.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderItemsMapper.class)
public interface OrderMapper {

    OrderModel mapEntityToModel(Orders orders);
    Orders mapModelToEntity(OrderModel orderModel);




}
