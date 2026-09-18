package dev.nexflow.order;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.*;
public final class OrderDtos {private OrderDtos(){}
 public record CreateRequest(@NotBlank String customerName,@Email @NotBlank String customerEmail,@NotEmpty List<@Valid ItemRequest> items){}
 public record ItemRequest(@NotNull UUID productId,@Min(1) int quantity){}
 public record StatusRequest(@NotNull OrderStatus status){}
 public record ItemView(UUID productId,String productName,int quantity,BigDecimal unitPrice,BigDecimal total){}
 public record View(UUID id,String code,String customerName,String customerEmail,BigDecimal total,OrderStatus status,List<ItemView> items,OffsetDateTime createdAt,OffsetDateTime updatedAt){}
}
