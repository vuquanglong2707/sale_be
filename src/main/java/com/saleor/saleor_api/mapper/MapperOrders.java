package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoOrders;
import com.saleor.saleor_api.table.Orders;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import javax.persistence.criteria.Order;
import java.util.List;

//public class MapperOrders {
//}
@Mapper(componentModel = "spring")
public interface MapperOrders extends EntityMapper<Orders, DtoOrders> {
    @Override
    DtoOrders toDto(Orders source);

    @Override
    Orders toEntity(DtoOrders source);

    @Override
    List<DtoOrders> toDtoList(List<Orders> sourceList);

    @Override
    List<Orders> toEntityList(List<DtoOrders> sourceList);
}