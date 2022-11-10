package ru.buzanov.petproj.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzanov.petproj.models.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
}
