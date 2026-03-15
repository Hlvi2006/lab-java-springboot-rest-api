package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    public void test_GetAllProducts_Success() throws Exception{
        List<Product> products=List.of(
                new Product("Pen",0.54,"equipment",20),
                new  Product("Bag",34.5,"equipment",12));

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products")
                .header("API-Key","123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Pen"))
                .andExpect(jsonPath("$[1].name").value("Bag"));

    }
    @Test
    public void test_getProductBy_Name() throws Exception{
        Product product=new Product("Pen",0.54,"equipment",20);
        when(productService.getProductByName("Pen")).thenReturn(product);

        mockMvc.perform(get("/products/name/{name}","Pen")
                .header("API-Key","123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pen"));
    }

    @Test
    public void test_getProductById_Success() throws Exception{
        Product product=new Product("Pen",0.54,"equipment",20);
        product.setId(1L);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1")
                        .header("API-Key","123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pen"))
                .andExpect(jsonPath("$.price").value(0.54))
                .andExpect(jsonPath("$.category").value("equipment"));
    }
    @Test
    public void test_getProductById_NotFound() throws Exception{
        when(productService.getProductById(99L)).thenThrow(
                new ResourceNotFoundException("Book",99L)
        );

        mockMvc.perform(get("/products/99")
                .header("API-Key","123456"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book can not find with id:"+99L));
    }

    @Test
    public void test_PostProduct_Success() throws Exception{
        Product created=new Product("Pen",0.54,"equipment",20);

        when(productService.addProduct(any(Product.class))).thenReturn(created);

        String requestBody= """
                {
                "name":"Pen",
                "price":0.54,
                "category":"equipment",
                "quantity":20
                }
                """;

        mockMvc.perform(post("/products")
                .header("API-Key","123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pen"))
                .andExpect(jsonPath("$.price").value(0.54))
                .andExpect(jsonPath("$.category").value("equipment"))
                .andExpect(jsonPath("$.quantity").value(20));

    }

    @Test
    public void test_PostEmpty_Name() throws Exception{
        String requestBody= """
                {
                "name":"",
                "price":0.54,
                "category":"equipment",
                "quantity":"20"
                }
                """;

        mockMvc.perform(post("/products")
                .header("API-Key","123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad request"))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    public void test_UpdatedProduct_ReturnsUpdatedProduct() throws Exception{
        Product updatedProduct=new Product("Cup",0.54,"equipment",20);


        when(productService.updateProduct(eq("Pen"),any(Product.class))).thenReturn(updatedProduct);

        String requestBody= """
                {
                "name":"Cup",
                "price":0.54,
                "category":"equipment",
                "quantity":20
                }
                """;

        mockMvc.perform(patch("/products/{name}","Pen")
                .header("API-Key","123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cup"));

    }
    @Test
    public void test_DeleteProduct_Success() throws Exception{
        doNothing().when(productService).deleteProduct(eq("Pen"));

        mockMvc.perform(delete("/products/{name}","Pen")
                .header("API-Key","123456"))
                .andExpect(status().isNoContent());

    }
    @Test
    public void test_GetProductsBy_Range() throws Exception{
        List<Product> products=List.of(
                new Product("Keyboard",9,"equipment",8),
                new Product("Cup",2.54,"equipment",20)
        );
        when(productService.getProductsByPriceRange(1,10)).thenReturn(products);

        mockMvc.perform(get("/products/price")
                        .header("API-Key","123456")
                        .param("min","1")
                .param("max","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));

    }
}
