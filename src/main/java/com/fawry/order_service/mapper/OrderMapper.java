package com.fawry.order_service.mapper;

import com.fawry.order_service.entity.Orders;
import com.fawry.order_service.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderItemsMapper.class)
public interface OrderMapper {

    OrderModel mapEntityToModel(Orders orders);
    Orders mapModelToEntity(OrderModel orderModel);




}
