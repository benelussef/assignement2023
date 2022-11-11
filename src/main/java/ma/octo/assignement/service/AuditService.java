package ma.octo.assignement.service;

import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.util.EventType;

public interface AuditService {
    Audit addAudit(String message, EventType auditType);

}
