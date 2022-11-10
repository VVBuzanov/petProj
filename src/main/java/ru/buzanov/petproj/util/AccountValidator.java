package ru.buzanov.petproj.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.buzanov.petproj.models.Account;
import ru.buzanov.petproj.services.AccountService;

import java.math.BigDecimal;


@Component
public class AccountValidator {
    private final AccountService accountService;

    public AccountValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    public void validateCreate(Account account, Errors errors) {

        if (accountService.findByAccNumber(account.getAccNumber()) != null) {
            errors.rejectValue("accNumber", "", "Такой счет уже существует!");
        }
    }

    public void validateDelete(Account account, Errors errors) {

        if (accountService.ledgerExists(account.getId())) {
            errors.rejectValue("deleteError", "", "По счету были движения. Удалять нельзя!");
        }
    }

    public void validateUpdate(Account account, Errors errors) {

        if (account.getDateClose() != null && accountService.getBalance(account.getId()).compareTo(BigDecimal.ZERO) != 0) {
            errors.rejectValue("dateClose", "", "На счёте есть остатки!");
        }
    }
}
