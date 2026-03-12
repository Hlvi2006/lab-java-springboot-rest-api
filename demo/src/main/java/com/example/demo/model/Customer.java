package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Random;
import java.util.UUID;

public class Customer {
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @Min(value = 18,message = "age must be greater than 18")
    private int age;
    @NotBlank
    private String address;

    Random random = new Random();

    public Customer(String address, int age, String email, String name) {
        this.id= random.nextLong();
        this.address = address;
        this.age = age;
        this.email = email;
        this.name = name;
    }
    public Customer() {
        this.id= random.nextLong();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
