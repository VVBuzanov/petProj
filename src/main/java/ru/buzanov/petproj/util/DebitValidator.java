package ru.buzanov.petproj.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.buzanov.petproj.models.Ledger;
import ru.buzanov.petproj.services.AccountService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DebitValidator implements Validator {
    private final AccountService accountService;

    public DebitValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ledger.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ledger ledger = (Ledger) target;
        BigDecimal balance = accountService.getBalance(ledger.getAccId()).setScale(2, RoundingMode.CEILING);
        if (ledger.getSumDebit().compareTo(balance) == 1) {
            errors.rejectValue("sumDebit", "", "Остаток на счете: " + balance + " -  меньше суммы списания!");
        }
    }
}

