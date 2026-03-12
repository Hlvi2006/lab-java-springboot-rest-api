package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/products")
@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createNewProduct(@RequestHeader(value="API-Key",required = true) String apiKey, @Valid @RequestBody Product product) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Product created= productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@RequestHeader(value="API-Key") String apiKey,@PathVariable Long id){
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Product product=productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader(value="API-Key") String apiKey) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Product> products=productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PatchMapping("/{name}")
    public Product updateProduct(@RequestHeader(value="API-Key") String apiKey,@PathVariable String name,@Valid @RequestBody Product product){
        return productService.updateProduct(name,product);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@RequestHeader(value="API-Key") String apiKey,@PathVariable String name) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Product product=productService.getProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>>  getProductsByCategory(@RequestHeader(value="API-Key") String apiKey,@PathVariable String category) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Product> product=productService.getAllProductsByCategory(category);
         return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>>  getProductsByPriceRange(@RequestHeader(value="API-Key") String apiKey,@RequestParam Double min, @RequestParam Double max) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByPriceRange(min, max)) ;
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<Product> deleteProduct(@RequestHeader(value="API-Key") String apiKey,@PathVariable String name) {
        if(!"123456".equals(apiKey)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        productService.deleteProduct(name);
        return ResponseEntity.noContent().build();
    }

}
