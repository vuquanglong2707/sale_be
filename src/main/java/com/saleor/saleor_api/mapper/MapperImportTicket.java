package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperImportTicket  extends EntityMapper<ImportTicket, DTOImportTicket> {

    @Override
    DTOImportTicket toDto(ImportTicket source);

    @Override
    ImportTicket toEntity(DTOImportTicket source);

    @Override
    List<DTOImportTicket> toDtoList(List<ImportTicket> sourceList);

    @Override
    List<ImportTicket> toEntityList(List<DTOImportTicket> sourceList);

}
