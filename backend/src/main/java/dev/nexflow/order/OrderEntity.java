package dev.nexflow.order;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.*;
@Entity @Table(name="orders") public class OrderEntity {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id; @Column(nullable=false,unique=true) private String code; @Column(nullable=false) private String customerName; @Column(nullable=false) private String customerEmail;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal total; @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true) private List<OrderItem> items=new ArrayList<>(); @Column(nullable=false) private OffsetDateTime createdAt; @Column(nullable=false) private OffsetDateTime updatedAt;
 @PrePersist void create(){createdAt=OffsetDateTime.now();updatedAt=createdAt;} @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
 public void addItem(OrderItem i){i.setOrder(this);items.add(i);} public UUID getId(){return id;} public String getCode(){return code;} public void setCode(String v){code=v;} public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;} public String getCustomerEmail(){return customerEmail;} public void setCustomerEmail(String v){customerEmail=v;} public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;} public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus v){status=v;} public List<OrderItem> getItems(){return items;} public OffsetDateTime getCreatedAt(){return createdAt;} public OffsetDateTime getUpdatedAt(){return updatedAt;}
}
