package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService{
    HashMap <Long,Product> products=new  HashMap<>();


    public Product addProduct(Product product){
        products.put(product.getId(),product);
        return product;
    }
    public List<Product> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    public Product getProductByName(String name){
        for(Product p:products.values()){
            if(p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        throw new RuntimeException("Product not found");
    }
    public Product getProductById(Long id){
        Product p=products.get(id);
        if  ( p == null ){
            throw new RuntimeException("Product not found");
        }
        return p;
    }

    public Product updateProduct(String name,Product product){
        Product p=getProductByName(name);
        if(product.getName()== null) throw new RuntimeException("Name not found");
        if(product.getPrice()==0) throw new RuntimeException("Price not found");

        p.setName(product.getName());
        return p;

    }
    public void deleteProduct(String name){
        Product product=getProductByName(name);
        products.remove(product.getId());
    }

    public List<Product> getAllProductsByCategory(String category){
        ArrayList<Product> productsList=new ArrayList<>();
        for(Product p:products.values()){
            if(p.getCategory().equals(category)){
                productsList.add(p);
            }
        }
        return productsList;
    }
    public List<Product> getProductsByPriceRange(double min,double max){
        if(min>max) throw new  RuntimeException("min must be greater than max");

        ArrayList<Product> productsList=new ArrayList<>();
        for(Product p:products.values()){
            if(p.getPrice()>=min && p.getPrice()<=max){
                productsList.add(p);
            }
        }
        return productsList;
    }

}
