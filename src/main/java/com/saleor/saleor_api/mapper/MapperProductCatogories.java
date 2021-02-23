package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.dto.DtoProductCatogories;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.ProductCatogories;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperProductCatogories extends EntityMapper<ProductCatogories, DtoProductCatogories> {
    @Override
    DtoProductCatogories toDto(ProductCatogories source);

    @Override
    ProductCatogories toEntity(DtoProductCatogories source);

    @Override
    List<DtoProductCatogories> toDtoList(List<ProductCatogories> sourceList);

    @Override
    List<ProductCatogories> toEntityList(List<DtoProductCatogories> sourceList);
}