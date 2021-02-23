package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoShop;
import com.saleor.saleor_api.table.Shop;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

//public class MapperShop {
//}

@Mapper(componentModel = "spring")
public interface MapperShop extends EntityMapper<Shop, DtoShop> {
    @Override
    DtoShop toDto(Shop source);

    @Override
    Shop toEntity(DtoShop source);

    @Override
    List<DtoShop> toDtoList(List<Shop> sourceList);

    @Override
    List<Shop> toEntityList(List<DtoShop> sourceList);
}