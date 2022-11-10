package ru.buzanov.petproj.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.buzanov.petproj.models.Account;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.models.Ledger;
import ru.buzanov.petproj.services.AccountService;
import ru.buzanov.petproj.util.AccountValidator;
import ru.buzanov.petproj.util.DebitValidator;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountValidator accountValidator;
    private final DebitValidator debitValidator;

    public AccountController(AccountService accountService,
                             AccountValidator accountValidator,
                             DebitValidator debitValidator) {
        this.accountService = accountService;
        this.accountValidator = accountValidator;
        this.debitValidator = debitValidator;
    }


    @GetMapping("/{id}")
    public String getAccount(@PathVariable("id") int id,
                             Model model) {
        model.addAttribute("account", accountService.findOne(id));

        return "account/show";
    }

    @GetMapping("/{id}/edit")
    public String editAccount(@PathVariable("id") int id,
                              Model model) {
        model.addAttribute("account", accountService.findOne(id));

        return "account/edit";
    }

    @PatchMapping("/{id}")
    public String updateAccount(@PathVariable("id") int id,
                                @ModelAttribute("account") @Valid Account account,
                                BindingResult bindingResult) {
        accountValidator.validateUpdate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/edit";
        }

        accountService.updateOne(id, account);

        return "redirect:/account/" + account.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable("id") int accid,
                                @RequestParam("clientId") int clientId) {

        accountService.deleteOne(accid);

        return "redirect:/client/" + clientId;
    }

    @GetMapping("/new")
    public String newAccount(@ModelAttribute("client") Client client,
                             Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("client", client);

        return "account/new";
    }

    @PostMapping()
    public String saveAccount(@ModelAttribute("client") Client client,
                              @ModelAttribute("account") @Valid Account account,
                              BindingResult bindingResult) {
        accountValidator.validateCreate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/new";
        }

        accountService.save(account, client);

        return "redirect:/client/" + client.getId();
    }

    @GetMapping("/{id}/debit")
    public String debitAccount(@PathVariable("id") int id,
            /*Model model*/
                               @ModelAttribute("ledger") Ledger ledger) {
        /* Ledger ledger = new Ledger();*/
        ledger.setAccId(id);
        /*model.addAttribute("ledger", ledger);*/

        return "account/debit";
    }

    @PostMapping("/{id}/debit")
    public String debitAccountSave(@ModelAttribute("ledger") @Valid Ledger ledger,
                                   BindingResult bindingResult,
                                   @PathVariable("id") int id) {
        ledger.setAccId(id);
        debitValidator.validate(ledger, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/debit";
        }

        accountService.debit(id, ledger.getSumDebit());

        return "redirect:/account/" + id;
    }

    @GetMapping("/{id}/credit")
    public String creditAccount(@PathVariable("id") int id,
                                Model model) {
        model.addAttribute("accId", id);

        return "account/credit";
    }

    @PostMapping("/{id}/credit")
    public String creditAccountSave(@PathVariable("id") int id,
                                    @RequestParam("summa") BigDecimal summa) {
        accountService.credit(id, summa);

        return "redirect:/account/" + id;
    }
}
