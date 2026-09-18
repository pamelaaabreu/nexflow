package dev.nexflow.inventory;
import dev.nexflow.product.Product; import jakarta.persistence.*; import java.time.OffsetDateTime; import java.util.UUID;
@Entity @Table(name="inventory_movements")
public class InventoryMovement {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id") private Product product;
 @Enumerated(EnumType.STRING) @Column(name="movement_type",nullable=false) private MovementType type;
 @Column(nullable=false) private int quantity; private String reference; @Column(nullable=false) private String createdBy; @Column(nullable=false) private OffsetDateTime createdAt;
 @PrePersist void create(){createdAt=OffsetDateTime.now();}
 public UUID getId(){return id;} public Product getProduct(){return product;} public void setProduct(Product v){product=v;} public MovementType getType(){return type;} public void setType(MovementType v){type=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;} public String getReference(){return reference;} public void setReference(String v){reference=v;} public String getCreatedBy(){return createdBy;} public void setCreatedBy(String v){createdBy=v;} public OffsetDateTime getCreatedAt(){return createdAt;}
}
