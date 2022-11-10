package ru.buzanov.petproj.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzanov.petproj.models.Account;
import ru.buzanov.petproj.models.Audit;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.models.Ledger;
import ru.buzanov.petproj.repositories.AuditRepository;
import ru.buzanov.petproj.util.ActionType;
import ru.buzanov.petproj.util.EntityType;

@Service

public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public void save(EntityType entityType,
                     ActionType actionType,
                     Object obj) {
        int id = 0;
        String json = null;

        if (obj instanceof Account) {
            id = ((Account) obj).getId();
            json = ((Account) obj).toJson();
        }
        if (obj instanceof Client) {
            id = ((Client) obj).getId();
            json = ((Client) obj).toJson();
        }
        if (obj instanceof Ledger) {
            id = ((Ledger) obj).getId();
            json = ((Ledger) obj).toJson();
        }

        Audit audit = new Audit(id, entityType.name(), actionType.name(), json);
        auditRepository.save(audit);
    }
}
