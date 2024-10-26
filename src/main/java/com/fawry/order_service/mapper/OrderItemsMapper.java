package com.fawry.order_service.mapper;


import com.fawry.order_service.entity.OrderItems;
import com.fawry.order_service.model.OrderItemsModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface OrderItemsMapper {

    OrderItemsModel mapEntityToModel(OrderItems orderItems);


    OrderItems mapModelToEntity(OrderItemsModel orderItemsModel);

}