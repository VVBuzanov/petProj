package ru.buzanov.petproj.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.buzanov.petproj.models.Account;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class AccountDao {

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> findAll() {
        Session session = entityManager.unwrap(Session.class);

        List<Account> accounts = session.createQuery("select p from Account p", Account.class)
                .getResultList();

        return accounts;
    }

    public Account findByAccNumber(String accNumber) {
        Session session = entityManager.unwrap(Session.class);

        Account accounts = session.createQuery("select p from Account p where accNumber = :acc", Account.class).
                setParameter("acc", accNumber).
                getResultList().stream().findAny().orElse(null);

        return accounts;
    }

    public void save(Account account) {
        Session session = entityManager.unwrap(Session.class);
        session.save(account);
    }


    public Account findOne(int accId) {
        Session session = entityManager.unwrap(Session.class);

        return session.get(Account.class, accId);
    }

    public void deleteOne(int accId) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(session.get(Account.class, accId));
    }

    public void updateOne(int accId,
                          Account account) {
        Session session = entityManager.unwrap(Session.class);
        Account accountUpdate = session.get(Account.class, accId);
        accountUpdate.setDateClose(account.getDateClose());
    }

    public double getBalance(int accId) {
        return jdbcTemplate.queryForObject("select coalesce(sum(iCredet),0) - coalesce(sum(iDebet),0)   from ledger where iAccId = ?",
                Double.class,
                accId);
    }

    public int ledgerExists(int accId) {
        return jdbcTemplate.queryForObject("select coalesce(max(1),0)  where exists( select 1 from ledger where iaccid = iAccId = ?)",
                Integer.class,
                accId);
    }
}
