package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers(@RequestHeader(value="API-Key" )String apiKey){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@RequestHeader(value="API-Key" )String apiKey,@PathVariable Long id){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestHeader(value="API-Key" )String apiKey, @Valid @RequestBody Customer customer){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        Customer newCustomer=customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }
    @GetMapping("/email")
    public Customer getCustomerByEmail(@RequestHeader(value="API-Key" )String apiKey,@RequestParam String email){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        return customerService.getCustomerByEmail(email);
    }
    @PatchMapping("/{id}")
    public Customer updateCustomer(@RequestHeader(value="API-Key" )String apiKey,@PathVariable Long id,@Valid @RequestBody Customer customer ){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        return customerService.updateCustomer(id,customer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@RequestHeader(value="API-Key" )String apiKey,@PathVariable Long id){
        if(!"123456".equals(apiKey)) throw new IllegalArgumentException("Invalid API Key");
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
