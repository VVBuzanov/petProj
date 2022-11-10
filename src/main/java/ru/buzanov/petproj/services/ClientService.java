package ru.buzanov.petproj.services;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.repositories.ClientRepository;
import ru.buzanov.petproj.util.ActionType;
import ru.buzanov.petproj.util.EntityType;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientService {

    //private final ClientDao clientDao;
    //private final ClientDao accountDao;
    private final AuditService auditService;

    private final ClientRepository clientRepository;

    public ClientService(/*ClientDao clientDao,*/
                         /*ClientDao accountDao,*/
                         AuditService auditService,
                         ClientRepository clientRepository) {
        //this.clientDao = clientDao;
        //this.accountDao = accountDao;
        this.auditService = auditService;
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
        //return  clientDao.findAll();
    }

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
        //clientDao.save(client);
        auditService.save(EntityType.CLIENT, ActionType.CREATE, client);
    }

    public Client findOne(int id) {
        Client client = clientRepository.findById(id).get();
        //Client client = clientDao.findOne(id);
        Hibernate.initialize(client.getAccounts());

        return client;
    }

    @Transactional
    public void deleteOne(int clientId) {
        clientRepository.deleteById(clientId);
        Client client = new Client();
        client.setId(clientId);
          /*clientDao.deleteOne(clientId);
          Client client = new Client();
          client.setId(clientId);*/
        auditService.save(EntityType.CLIENT, ActionType.DELETE, client);
    }

    @Transactional
    public void updateOne(int clientId,
                          Client client) {
        Client clientUpd = clientRepository.findById(clientId).get();
        clientUpd.setBirthday(client.getBirthday());
        clientUpd.setFio(client.getFio());
        clientRepository.save(clientUpd);
        //clientDao.updateOne(clientId, client);
        auditService.save(EntityType.CLIENT, ActionType.UPDATE, client);
    }

    public Client findByNameBDay(String fio,
                                 Date bDay) {
        return clientRepository.findClientByBirthdayAndFio(bDay, fio);
        //return clientDao.findByNameBDay(name, bDay);
    }
}
