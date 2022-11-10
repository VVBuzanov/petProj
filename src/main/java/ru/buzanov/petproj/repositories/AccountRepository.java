package ru.buzanov.petproj.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.buzanov.petproj.models.Account;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccNumber(String accNumber);

    @Query("select coalesce(sum(r.sumCredit),0) - coalesce(sum(r.sumDebit),0)   from Ledger r where r.accId = :accId")
    BigDecimal findBalance(@Param("accId") int accId);


}
