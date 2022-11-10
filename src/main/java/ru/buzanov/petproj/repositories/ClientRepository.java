package ru.buzanov.petproj.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzanov.petproj.models.Client;

import java.util.Date;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientByBirthdayAndFio(Date birthday, String fio);
}
