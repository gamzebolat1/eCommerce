package com.gamzebolat.controller;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.Dto.DtoProduct;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;
import com.gamzebolat.service.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServiceImpl productService;


    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/create")
    public DtoProduct createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping(path = "/get/{id}")
    public DtoProduct getProduct(@PathVariable(name="id") int Id){
        return  productService.getProduct(Id);
    }

    @PutMapping(path = "/update/{id}")
    public DtoProduct updateProduct(@PathVariable(name="id") int Id ,@RequestBody Product newProduct){
        return  productService.updateProduct(Id,newProduct);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteProduct(@PathVariable(name="id") int Id){
        productService.deleteProduct(Id);
    }

    @PostMapping(path = "/addCustomer")
    public DtoCustomer AddCustomer(@RequestBody Customer customer){
        return productService.AddCustomer(customer);
    }
}
