package com.gamzebolat.controller;

import com.gamzebolat.Dto.CustomerDto;
import com.gamzebolat.Dto.ProductDto;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;
import com.gamzebolat.facade.CustomerFacadeImpl;
import com.gamzebolat.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServiceImpl productService;
    private final CustomerFacadeImpl customerFacade;


    @PostMapping(path = "/create")
    public ProductDto createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping(path = "/get")
    public ProductDto getProduct(@RequestParam(name = "id") int Id){
        return productService.getProduct(Id);
    }
    @PutMapping(path = "/update/{id}")
    public ProductDto updateProduct(@PathVariable(name="id") int Id , @RequestBody Product newProduct){
        return  productService.updateProduct(Id,newProduct);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteProduct(@PathVariable(name="id") int Id){
        productService.deleteProduct(Id);
    }

    @PostMapping(path = "/addCustomer")
    public CustomerDto AddCustomer(@RequestBody Customer customer){
        return customerFacade.addCustomer(customer);
    }

    @GetMapping(path="/getAll")
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }
}
