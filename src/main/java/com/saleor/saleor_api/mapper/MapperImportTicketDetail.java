package com.saleor.saleor_api.mapper;

import com.saleor.saleor_api.dto.DTOImportTicketDetail;
import com.saleor.saleor_api.table.ImportTicketDetail;
import com.saleor.saleor_api.utils.EntityMapper;
import org.mapstruct.Mapper;
import java.util.List;
@Mapper(componentModel = "spring")
public interface MapperImportTicketDetail extends EntityMapper<ImportTicketDetail, DTOImportTicketDetail> {
    @Override
    DTOImportTicketDetail toDto(ImportTicketDetail source);

    @Override
    ImportTicketDetail toEntity(DTOImportTicketDetail source);

    @Override
    List<DTOImportTicketDetail> toDtoList(List<ImportTicketDetail> sourceList);

    @Override
    List<ImportTicketDetail> toEntityList(List<DTOImportTicketDetail> sourceList);
}
