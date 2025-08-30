package org.spring.security6.controller;

import org.spring.security6.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1, "iPhone", 999.0),
                    new Product(2, "Samsung", 599.0),
                    new Product(3, "Mac Pro", 1999.0)
            )
    );

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}
