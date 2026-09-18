package dev.nexflow.product;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
public final class ProductDtos {
    private ProductDtos(){}
    public record Request(@NotBlank String name, @NotBlank String sku, @NotBlank String category,
                          @NotNull @DecimalMin("0.01") BigDecimal price, @Min(0) int stockQuantity, @Min(0) int minStock){}
    public record View(UUID id, String name, String sku, String category, BigDecimal price, int stockQuantity,
                       int reservedQuantity, int availableQuantity, int minStock, boolean active, OffsetDateTime updatedAt){}
}
