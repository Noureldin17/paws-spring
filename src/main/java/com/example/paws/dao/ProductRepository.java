package com.example.paws.dao;
import java.util.List;
import com.example.paws.entities.Product;
import com.example.paws.entities.PetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByPetType(PetType petType);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p "
            + "LEFT JOIN p.petType pt "
            + "WHERE (:categoryId IS NULL OR p.category.categoryId = :categoryId) "
            + "AND (:petTypeName IS NULL OR pt.name = :petTypeName) "
            + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> filterProducts(
            Pageable pageable,
            @Param("categoryId") Long categoryId,
            @Param("petTypeName") String petTypeName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
