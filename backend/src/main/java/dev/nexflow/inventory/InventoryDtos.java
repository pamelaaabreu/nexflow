package dev.nexflow.inventory;
import jakarta.validation.constraints.*; import java.time.OffsetDateTime; import java.util.UUID;
public final class InventoryDtos {private InventoryDtos(){}
 public record AdjustmentRequest(@NotNull UUID productId,@NotNull Integer quantity,@NotBlank String reason){}
 public record MovementView(UUID id,UUID productId,String productName,String sku,String type,int quantity,String reference,String createdBy,OffsetDateTime createdAt){}
}
