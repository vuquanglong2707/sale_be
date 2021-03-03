package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.ProductCatogories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Repository
public interface RepoProduct extends JpaRepository<Product,Long> {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Override
//    public List<Product> findProductByQuantitySoldLimitedTo(int limit) {
//        return entityManager.createQuery("SELECT u FROM Product u ORDER BY u.quantitySold DESC",
//                Product.class).setMaxResults(limit).getResultList();
//    }
    List<Product> findAll();
    List<Product> findBy();
    List<Product>findAllBy(Pageable pageable);
    Optional<Product>findById(Long id);
    List<Product>findAllById(long id);
    List<Product>findBySkuContaining(String id);
    Optional<Product> findBySku(String query);
    List<Product> findAllBySku(String Sku);
    List<Product>findByIdAndName(Long Id,String Name);

    List<Product> findAllByProductCatogories(ProductCatogories productCatogories);
   Optional<Product> findByName(String name);
    List<Product> findAllByName(String name);
    Page<Product> findAllByName(Pageable pageable,String name);

    @Query(value = "SELECT u FROM Product u ORDER BY u.quantitySold DESC ")
    public List<Product> findProductByQuantitySoldLimitedTo();

//    @Query(value = "SELECT u FROM Product u WHERE u.productCatogories=:id")
//    public List<Product> findAllByProductCatogoriesid(Long id);
//    @Query(value = "SELECT u FROM Product u ORDER BY u.quantitySold DESC ")
//    List<Product> find();
//    @Override
//    public List<Product> findProductBySeatNumberLimitedTo(int limit) {
//        return entityManager.createQuery("SELECT p FROM Passenger p ORDER BY p.seatNumber",
//                Passenger.class).setMaxResults(limit).getResultList();
//    }
}
