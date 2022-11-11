package ma.octo.assignement.service.implementation;

import ma.octo.assignement.domain.Audit;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditRepository;
import ma.octo.assignement.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {
    Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Audit addAudit(String message, EventType auditType) {
        LOGGER.info("Audit de l'événement {}", auditType);
        LOGGER.info(message);
        Audit audit = new Audit();
        audit.setEventType(auditType);
        audit.setMessage(message);
        return auditRepository.save(audit);
    }
}
