package ru.buzanov.petproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzanov.petproj.models.Ledger;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Integer> {
    boolean existsByAccId(int accId);


}


