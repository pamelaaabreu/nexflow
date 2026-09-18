package dev.nexflow.inventory; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement,UUID>{List<InventoryMovement> findTop100ByOrderByCreatedAtDesc();}
