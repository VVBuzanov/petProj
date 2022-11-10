package ru.buzanov.petproj.dao;


import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.buzanov.petproj.models.Client;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Component
public class ClientDao {

    private final EntityManager entityManager;

    @Autowired
    public ClientDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> findAll() {
        Session session = entityManager.unwrap(Session.class);
        List<Client> clients = session.createQuery("select p from Client p", Client.class)
                .getResultList();

        return clients;
    }

    public void save(Client client) {
        Session session = entityManager.unwrap(Session.class);
        session.save(client);
    }

    public Client findOne(int clientId) {
        Session session = entityManager.unwrap(Session.class);

        return session.get(Client.class, clientId);
    }

    public void deleteOne(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(session.get(Client.class, clientId));
    }

    public void updateOne(int clientId, Client client) {
        Session session = entityManager.unwrap(Session.class);
        Client clientUpdate = session.get(Client.class, clientId);
        clientUpdate.setBirthday(client.getBirthday());
        clientUpdate.setFio(client.getFio());
    }

    public Client findByNamebDay(String name, Date bDay) {
        Session session = entityManager.unwrap(Session.class);
        Client client = session.createQuery("select p from Client p where Fio = :name and Birthday = :bday", Client.class).
                setParameter("name", name).
                setParameter("bday", bDay).
                getResultList().stream().findAny().orElse(null);

        return client;
    }
}
