package com.fawry.orderservice.mapper;


import com.fawry.orderservice.entity.OrderItems;
import com.fawry.orderservice.model.OrderItemsModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface OrderItemsMapper {

    OrderItemsModel mapEntityToModel(OrderItems orderItems);

    OrderItems mapModelToEntity(OrderItemsModel orderItemsModel);

}