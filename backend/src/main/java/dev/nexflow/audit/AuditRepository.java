package dev.nexflow.audit; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AuditRepository extends JpaRepository<AuditEvent,UUID>{List<AuditEvent> findTop50ByOrderByCreatedAtDesc();}
