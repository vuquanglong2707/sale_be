package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperProduct extends EntityMapper<Product, DtoProduct> {
    @Override
    DtoProduct toDto(Product source);

    @Override
    Product toEntity(DtoProduct source);

    @Override
    List<DtoProduct> toDtoList(List<Product> sourceList);

    @Override
    List<Product> toEntityList(List<DtoProduct> sourceList);
}
