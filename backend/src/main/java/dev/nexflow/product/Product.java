package dev.nexflow.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true) private String sku;
    @Column(nullable=false) private String category;
    @Column(nullable=false, precision=14, scale=2) private BigDecimal price;
    @Column(nullable=false) private int stockQuantity;
    @Column(nullable=false) private int reservedQuantity;
    @Column(nullable=false) private int minStock;
    @Column(nullable=false) private boolean active = true;
    @Column(nullable=false) private OffsetDateTime createdAt;
    @Column(nullable=false) private OffsetDateTime updatedAt;
    @PrePersist void create(){ createdAt=OffsetDateTime.now(); updatedAt=createdAt; }
    @PreUpdate void update(){ updatedAt=OffsetDateTime.now(); }
    public int availableQuantity(){ return stockQuantity-reservedQuantity; }
    public UUID getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getSku(){return sku;} public void setSku(String v){sku=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;} public int getStockQuantity(){return stockQuantity;} public void setStockQuantity(int v){stockQuantity=v;}
    public int getReservedQuantity(){return reservedQuantity;} public void setReservedQuantity(int v){reservedQuantity=v;} public int getMinStock(){return minStock;} public void setMinStock(int v){minStock=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public OffsetDateTime getCreatedAt(){return createdAt;} public OffsetDateTime getUpdatedAt(){return updatedAt;}
}
