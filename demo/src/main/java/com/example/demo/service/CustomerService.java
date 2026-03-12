package com.example.demo.service;

import com.example.demo.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomerService {
    HashMap<Long, Customer> customers = new HashMap<>();

    public Customer createCustomer(Customer customer){
        if(customer == null) throw new IllegalArgumentException("Customer is null");
        Customer newCustomer=new Customer();
        newCustomer.setAge(customer.getAge());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setName(customer.getName());
        customers.put(newCustomer.getId(), newCustomer);

        return newCustomer;
    }

    public List<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }
    public Customer getCustomerById(Long id){
        return customers.get(id);
    }

    public Customer getCustomerByEmail(String email){
        if(email == null) throw new IllegalArgumentException("Email is null");
        for(Customer customer:customers.values()){
            if(customer.getEmail().equals(email)) return customer;
        }
        throw new IllegalArgumentException("Customer with email "+email+" not found");
    }

    public Customer updateCustomer(Long id,Customer customer){
        Customer updatedCustomer=customers.get(id);
        if(customer == null && updatedCustomer == null) throw new IllegalArgumentException("Customer is null");

        if(customer.getName() != null) updatedCustomer.setName(customer.getName());
        if(customer.getAge() != 0) updatedCustomer.setAge(customer.getAge());
        if(customer.getAddress() != null) updatedCustomer.setAddress(customer.getAddress());
        if(customer.getEmail() != null) updatedCustomer.setEmail(customer.getEmail());
        return updatedCustomer;
    }
    public void deleteCustomer(Long id){
        Customer customer=customers.get(id);
        if(customer == null) throw new IllegalArgumentException("Customer with id "+id+" not found");
        customers.remove(id);
    }
}
