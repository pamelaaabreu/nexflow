package dev.nexflow.product;

import dev.nexflow.audit.AuditService;
import dev.nexflow.common.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository repository; private final AuditService audit;
    public ProductService(ProductRepository repository, AuditService audit){this.repository=repository;this.audit=audit;}
    @Transactional(readOnly=true) public List<ProductDtos.View> list(){return repository.findAllByOrderByNameAsc().stream().map(this::view).toList();}
    @Transactional public ProductDtos.View create(ProductDtos.Request req, String user){
        if(repository.existsBySkuIgnoreCase(req.sku())) throw new ApiException(HttpStatus.CONFLICT,"SKU already exists");
        Product p=new Product(); apply(p,req); p.setReservedQuantity(0); p=repository.save(p);
        audit.log(user,"CREATE","PRODUCT",p.getId().toString(),"Created product "+p.getSku()); return view(p);
    }
    @Transactional public ProductDtos.View update(UUID id, ProductDtos.Request req, String user){
        Product p=get(id); if(!p.getSku().equalsIgnoreCase(req.sku()) && repository.existsBySkuIgnoreCase(req.sku())) throw new ApiException(HttpStatus.CONFLICT,"SKU already exists");
        int originalStock=p.getStockQuantity(); apply(p,req); if(p.getStockQuantity()<p.getReservedQuantity()) p.setStockQuantity(originalStock);
        audit.log(user,"UPDATE","PRODUCT",id.toString(),"Updated product "+p.getSku()); return view(p);
    }
    @Transactional public void deactivate(UUID id,String user){Product p=get(id);p.setActive(false);audit.log(user,"DEACTIVATE","PRODUCT",id.toString(),"Deactivated product "+p.getSku());}
    public Product get(UUID id){return repository.findById(id).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Product not found"));}
    private void apply(Product p, ProductDtos.Request r){p.setName(r.name());p.setSku(r.sku());p.setCategory(r.category());p.setPrice(r.price());p.setStockQuantity(r.stockQuantity());p.setMinStock(r.minStock());}
    public ProductDtos.View view(Product p){return new ProductDtos.View(p.getId(),p.getName(),p.getSku(),p.getCategory(),p.getPrice(),p.getStockQuantity(),p.getReservedQuantity(),p.availableQuantity(),p.getMinStock(),p.isActive(),p.getUpdatedAt());}
}
