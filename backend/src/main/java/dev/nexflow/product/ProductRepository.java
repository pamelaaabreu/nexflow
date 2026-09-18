package dev.nexflow.product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsBySkuIgnoreCase(String sku);
    List<Product> findAllByOrderByNameAsc();
}
