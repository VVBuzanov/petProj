package ru.buzanov.petproj.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzanov.petproj.dao.AccountDao;
import ru.buzanov.petproj.dao.ClientDao;
import ru.buzanov.petproj.models.Account;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.models.Ledger;
import ru.buzanov.petproj.repositories.AccountRepository;
import ru.buzanov.petproj.repositories.ClientRepository;
import ru.buzanov.petproj.repositories.LedgerRepository;
import ru.buzanov.petproj.util.ActionType;
import ru.buzanov.petproj.util.EntityType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {

    //private final ClientDao clientDao;
    //private final AccountDao accountDao;
    private final LedgerRepository ledgerRepository;
    private final AuditService auditService;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(ClientDao clientDao,
                          AccountDao accountDao,
                          LedgerRepository ledgerRepository,
                          AuditService auditService,
                          AccountRepository accountRepository,
                          ClientRepository clientRepository) {
        //.clientDao = clientDao;
        //this.accountDao = accountDao;
        this.ledgerRepository = ledgerRepository;
        this.auditService = auditService;
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Account> findAll() {
        /*return  accountDao.findAll();*/
        return accountRepository.findAll();
    }

    @Transactional
    public void save(Account account,
                     Client client) {
        Client foundClient = clientRepository.findById(client.getId()).get();
        foundClient.setAccounts(Collections.singletonList(account));
        account.setDateOpen(new Date());
        account.setClient(foundClient);
        accountRepository.save(account);

        /*Client foundClient = clientDao.findOne(client.getId());
        foundClient.setAccounts(Collections.singletonList(account));
        account.setDateOpen(new Date());
        account.setClient(foundClient);
        accountDao.save(account);*/
        auditService.save(EntityType.ACCOUNT, ActionType.CREATE, account);
    }

    public Account findOne(int accId) {
        Account account = accountRepository.findById(accId).get();
        account.setBalance(accountRepository.findBalance(accId).setScale(2, RoundingMode.CEILING));

        /*Account account =accountDao.findOne(accId);
        account.setBalance(getBalance(accId));*/

        return account;
    }

    @Transactional
    public void deleteOne(int accId) {
        /*
        accountDao.deleteOne(accId);
        Account account = new Account();
        account.setId(accId);*/

        accountRepository.deleteById(accId);
        Account account = new Account();
        account.setId(accId);
        auditService.save(EntityType.ACCOUNT, ActionType.DELETE, account);
    }

    @Transactional
    public void updateOne(int accId,
                          Account account) {
        Account accountUpd = accountRepository.findById(accId).get();
        accountUpd.setDateClose(account.getDateClose());
        accountRepository.save(accountUpd);
        /*accountDao.updateOne(accId, account);*/
        auditService.save(EntityType.ACCOUNT, ActionType.UPDATE, account);
    }

    public Account findByAccNumber(String accNumber) {
        return accountRepository.findByAccNumber(accNumber);
        /*return accountDao.findByAccNumber(accNumber);*/

    }

    public BigDecimal getBalance(int accId) {
        return accountRepository.findBalance(accId);
        //return accountDao.getBalance(accId);
    }

    public boolean ledgerExists(int accId) {
        return ledgerRepository.existsByAccId(accId);
        //return accountDao.ledgerExists(accId);
    }

    @Transactional
    public void debit(int accId,
                      BigDecimal summ) {
        Ledger ledger = new Ledger(accId, new BigDecimal(0), summ, new Date());
        ledgerRepository.save(ledger);
        auditService.save(EntityType.LEDGER, ActionType.CREATE, ledger);
    }

    @Transactional
    public void credit(int accId, BigDecimal summ) {
        Ledger ledger = new Ledger(accId, summ, new BigDecimal(0), new Date());
        ledgerRepository.save(ledger);
        auditService.save(EntityType.LEDGER, ActionType.CREATE, ledger);
    }
}
