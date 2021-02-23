package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Units;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoUnit extends JpaRepository<Units, Long> {
    List<Units> findBy();
    List<Units> findByName(String name);
}
