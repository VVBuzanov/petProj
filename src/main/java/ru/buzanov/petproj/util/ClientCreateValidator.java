package ru.buzanov.petproj.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.services.ClientService;

;


@Component
public class ClientCreateValidator implements Validator {

    private final ClientService clientService;

    public ClientCreateValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        if (clientService.findByNameBDay(client.getFio(), client.getBirthday()) != null) {
            errors.rejectValue("fio", "", "Клиент " + client.getFio() + " " +
                    client.bday2String() + "г.р. уже существует!");
        }
    }
}
