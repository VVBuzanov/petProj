package ru.buzanov.petproj.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.buzanov.petproj.models.Client;
import ru.buzanov.petproj.services.ClientService;
import ru.buzanov.petproj.util.ClientCreateValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ClientCreateValidator clientCreateValidator;

    public ClientController(ClientService clientService,
                            ClientCreateValidator clientCreateValidator) {
        this.clientService = clientService;
        this.clientCreateValidator = clientCreateValidator;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientService.findAll());

        return "client/index";

    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "client/new";
    }

    @PostMapping()
    public String saveClient(@ModelAttribute("client") @Valid Client client,
                             BindingResult bindingResult) {
        clientCreateValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            return "client/new";
        }

        clientService.save(client);

        return "redirect:/client";
    }


    @GetMapping("/{id}")
    public String showClient(@PathVariable("id") int id,
                             Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("accounts", clientService.findOne(id).getAccounts());

        return "client/show";
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable("id") int id) {
        clientService.deleteOne(id);

        return "redirect:/client";
    }

    @GetMapping("/{id}/edit")
    public String editClient(Model model,
                             @PathVariable("id") int id) {
        model.addAttribute(clientService.findOne(id));

        return "client/edit";
    }

    @PatchMapping("/{id}")
    public String updateClient(@PathVariable("id") int id,
                               @ModelAttribute("client") @Valid Client client,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/edit";
        }

        clientService.updateOne(id, client);

        return "redirect:/client/" + id;
    }
}
