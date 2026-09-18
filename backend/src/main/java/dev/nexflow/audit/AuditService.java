package dev.nexflow.audit;
import org.springframework.stereotype.Service; import java.util.List;
@Service public class AuditService {
 private final AuditRepository repository; public AuditService(AuditRepository r){repository=r;}
 public void log(String user,String action,String entity,String entityId,String description){AuditEvent e=new AuditEvent();e.setUserEmail(user);e.setAction(action);e.setEntityName(entity);e.setEntityId(entityId);e.setDescription(description);repository.save(e);}
 public List<AuditEvent> recent(){return repository.findTop50ByOrderByCreatedAtDesc();}
}
