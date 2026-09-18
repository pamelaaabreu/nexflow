package dev.nexflow.order; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface OrderRepository extends JpaRepository<OrderEntity,UUID>{
 @EntityGraph(attributePaths="items") List<OrderEntity> findAllByOrderByCreatedAtDesc();
 @EntityGraph(attributePaths="items") Optional<OrderEntity> findOneById(UUID id);
 long countByStatus(OrderStatus status);
}
