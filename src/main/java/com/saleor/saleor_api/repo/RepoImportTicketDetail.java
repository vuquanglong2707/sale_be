package com.saleor.saleor_api.repo;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.table.ImportTicketDetail;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoImportTicketDetail extends JpaRepository<ImportTicketDetail, Long> {
    Optional<ImportTicketDetail> findById(Long id);
    List<ImportTicketDetail> findAllByImportTicket(ImportTicket importTicket);
}
