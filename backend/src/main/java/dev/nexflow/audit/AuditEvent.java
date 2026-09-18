package dev.nexflow.audit;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_events")
public class AuditEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)

    private String userEmail;
    @Column(nullable = false)
    
    private String action;
    @Column(nullable = false)
    private String entityName;
    private String entityId;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void create() {
        createdAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String v) {
        userEmail = v;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String v) {
        action = v;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String v) {
        entityName = v;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String v) {
        entityId = v;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String v) {
        description = v;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
