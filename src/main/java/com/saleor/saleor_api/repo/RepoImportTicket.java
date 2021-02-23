package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.ImportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface RepoImportTicket extends JpaRepository<ImportTicket, Long> {

    Optional<ImportTicket> findByTitleContaining(String title);
    List<ImportTicket> findAllBy(Pageable pageable);
    Optional<ImportTicket> findById(Long id);
    List<ImportTicket> findByTitle(String title);
    Page<ImportTicket> findByTitleContaining(Pageable pageable, String title);
    Optional<ImportTicket> findByTitleContainingOrPhoneContainingOrShipCodeContaining(String title, String phone, String code);

    @Query("select a from ImportTicket a where a.createdDate BETWEEN :creationFromDateTime AND :creationToDateTime")
    Page<ImportTicket> findAllWithCreationDateTimeBefore(Date creationFromDateTime , Date creationToDateTime, Pageable pageable);


    @Query("select a from ImportTicket a where a.createdDate BETWEEN :creationFromDateTime AND :creationToDateTime")
    List<ImportTicket> findAllWithCreationDateTimeAbout(Date creationFromDateTime , Date creationToDateTime);
}
