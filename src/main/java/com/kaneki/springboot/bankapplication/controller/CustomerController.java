package com.kaneki.springboot.bankapplication.controller;

import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import com.kaneki.springboot.bankapplication.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private RestService restService;

    public static String username;
    public static Object password;

    private List<Transaction> transactions;
    private int customerId = 0;

    public CustomerController() {
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String listCustomers(Model model, @CurrentSecurityContext(expression = "authentication")Authentication authentication) {
        username = authentication.getName();
        password = authentication.getCredentials();
        List<Customer> customers = restService.getCustomerList();

        model.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/showFormToAddCustomer")
    public String showFormToAddCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer-form";
        }
        if (customer.getId() != 0) {
            customer.setTransactions(transactions);
        }
        restService.saveCustomer(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/showFormToUpdateCustomer")
    public String showformForUpdate(@RequestParam("customerId") int id,
                                    Model model) {
        Customer customer = restService.findCustomerById(id);
        transactions = new ArrayList<>();
        for(Transaction transaction : customer.getTransactions()) {
            transactions.add(transaction);
        }

        model.addAttribute("customer", customer);

        return "customer-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") int id) {
        restService.deleteCustomerById(id);
        return "redirect:/customers/list";
    }

    @GetMapping("/getTransactions")
    public String getCustomerTransactions(@RequestParam("customerId") int id,Model model) {
        customerId = id;
        List<Transaction> transactions = restService.getCustomerTransactions(id);
        Customer customer = restService.findCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("transactions",transactions);
        return "transactions-list";
    }

    @PostMapping("/saveTransaction")
    public String saveCustomerTransactions(@ModelAttribute("transaction") Transaction transaction,
                                           Model model) {
        int customerId = this.customerId;
        try {
            restService.saveTransactions(customerId, transaction);
            return "redirect:/customers/list";
        } catch (RuntimeException exp) {
            model.addAttribute("transaction", new Transaction());
            model.addAttribute("notSufficientFund","Insufficient balance. Please enter valid amount");
            return "transaction-form";
        }
    }

    @GetMapping("/showFormToAddTransaction")
    public String showFormToAddTransaction(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "transaction-form";
    }
}