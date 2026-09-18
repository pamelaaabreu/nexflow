package dev.nexflow.audit;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService service;

    public AuditController(AuditService s) {
        service = s;
    }

    @GetMapping
    public List<AuditEvent> list() {
        return service.recent();
    }
}
