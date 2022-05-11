package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.controller.CustomerController;
import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import com.kaneki.springboot.bankapplication.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RestServiceImple implements RestService {
    private RestTemplate restTemplate;

    private String restCustomerUrl;
    private String restUserUrl;

    @Autowired
    public RestServiceImple(RestTemplate restTemplate,
                            @Value("${crm.rest.customer.url}") String restCustomerUrl,
                            @Value("${crm.rest.user.url}") String restUserUrl) {
        this.restTemplate = restTemplate;
        this.restCustomerUrl = restCustomerUrl;
        this.restUserUrl = restUserUrl;
    }

    private HttpHeaders httpHeaders() {
        String username = CustomerController.username;
        Object password = CustomerController.password;

        String plainCreds = username+":"+password.toString();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public List<Customer> getCustomerList() {
        HttpEntity<String> request = new HttpEntity<>(httpHeaders());

        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(restCustomerUrl, HttpMethod.GET, request,
                new ParameterizedTypeReference<List<Customer>>() {});

        List<Customer> customers = responseEntity.getBody();

        return customers;
    }

    @Override
    public Customer findCustomerById(int id) {
        HttpEntity<String> request = new HttpEntity<>(httpHeaders());
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(restCustomerUrl + "/" + id, HttpMethod.GET, request, Customer.class);
        Customer customer = responseEntity.getBody();
        return customer;
    }

    @Override
    public void saveCustomer(Customer customer) {
        HttpEntity<Customer> request = new HttpEntity<>(customer, httpHeaders());
        int customerId = customer.getId();

        if(customerId == 0){
            restTemplate.exchange(restCustomerUrl, HttpMethod.POST, request, Customer.class);
        } else {
            restTemplate.exchange(restCustomerUrl, HttpMethod.PUT, request, Customer.class);
        }
    }

    @Override
    public void deleteCustomerById(int id) {
        HttpEntity<Customer> request = new HttpEntity<>(httpHeaders());
        if(id != 0) {
            restTemplate.exchange(restCustomerUrl + "/" + id, HttpMethod.DELETE, request, String.class);
        }
    }

    @Override
    public List<Transaction> getCustomerTransactions(int customerId){
        HttpEntity<String> request = new HttpEntity<>(httpHeaders());
        ResponseEntity<List<Transaction>> responseEntity = restTemplate.exchange(restCustomerUrl + "/transactions/" + customerId, HttpMethod.GET,
                request, new ParameterizedTypeReference<List<Transaction>>() {});
        List<Transaction> transactions = responseEntity.getBody();
        return transactions;
    }

    @Override
    public void saveTransactions(int customerId, Transaction transaction) {
        HttpEntity<Transaction> request = new HttpEntity<>(transaction, httpHeaders());
        ResponseEntity<Transaction> responseEntity = restTemplate.exchange(restCustomerUrl + "/transactions/" + customerId, HttpMethod.POST, request, Transaction.class);
        Transaction transaction1 = responseEntity.getBody();
    }

    @Override
    public List<User> findAllUser() {
        HttpEntity<String> request = new HttpEntity<>(httpHeaders());
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(restUserUrl, HttpMethod.GET, request,
                new ParameterizedTypeReference<List<User>>() {});
        List<User> users = responseEntity.getBody();
        return users;
    }

    @Override
    public void saveUser(CrmUser crmUser) {
        HttpEntity<CrmUser> request = new HttpEntity<>(crmUser, httpHeaders());
        restTemplate.exchange(restUserUrl, HttpMethod.POST, request, CrmUser.class);
    }
}