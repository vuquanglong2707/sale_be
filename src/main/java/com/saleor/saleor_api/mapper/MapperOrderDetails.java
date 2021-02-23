package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoOrderDetail;
import com.saleor.saleor_api.dto.DtoOrders;
import com.saleor.saleor_api.table.OrderDetail;
import com.saleor.saleor_api.table.Orders;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface MapperOrderDetails extends EntityMapper<OrderDetail, DtoOrderDetail> {
    @Override
    DtoOrderDetail toDto(OrderDetail source);

    @Override
    OrderDetail toEntity(DtoOrderDetail source);

    @Override
    List<DtoOrderDetail> toDtoList(List<OrderDetail> sourceList);

    @Override
    List<OrderDetail> toEntityList(List<DtoOrderDetail> sourceList);
}