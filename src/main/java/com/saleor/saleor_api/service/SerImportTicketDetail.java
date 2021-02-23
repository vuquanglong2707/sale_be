package com.saleor.saleor_api.service;

import com.saleor.saleor_api.repo.RepoImportTicketDetail;
import com.saleor.saleor_api.table.ImportTicketDetail;
import com.saleor.saleor_api.table.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SerImportTicketDetail {
    @Autowired
    RepoImportTicketDetail repoImportTicketDetail;
    public Optional<ImportTicketDetail> GetByID(Long id)
    {
        return  repoImportTicketDetail.findById(id);
    }
}
