package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DtoShop;
import com.saleor.saleor_api.dto.DtoVariation;
import com.saleor.saleor_api.table.Shop;
import com.saleor.saleor_api.table.Variation;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

//public interface MapperVariation {
//}
@Mapper(componentModel = "spring")
public interface MapperVariation extends EntityMapper<Variation, DtoVariation> {
    @Override
    DtoVariation toDto(Variation source);

    @Override
    Variation toEntity(DtoVariation source);

    @Override
    List<DtoVariation> toDtoList(List<Variation> sourceList);

    @Override
    List<Variation> toEntityList(List<DtoVariation> sourceList);
}