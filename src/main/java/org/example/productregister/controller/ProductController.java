package org.example.productregister.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.example.productregister.dtos.request.ProductRequest;
import org.example.productregister.dtos.response.ProductResponse;
import org.example.productregister.model.Product;
import org.example.productregister.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Operation(summary = "Inclui o objeto produto", description = "Retorna um objeto response que posteriormente será um product")
    @PostMapping("/user/{userId}")
    public ResponseEntity<ProductResponse> includeProduct(
            @Valid @RequestBody ProductRequest request, @PathVariable Long userId) {
        ProductResponse response = productService.includeProduct(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponse>> listAllProducts(@PathVariable Long userId) {
        List<ProductResponse> products = productService.listProducts(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}/category/{category}")
    public ResponseEntity<List<ProductResponse>> listProductsByCategory(
            @Parameter(description = "Categoria do produto", example = "electronics")
            @PathVariable String category,
            @PathVariable Long userId) {
        List<ProductResponse> products = productService.listProductsByCategory(category, userId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request,
            @PathVariable Long userId) {
        ProductResponse response = productService.updateProduct(userId, productId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long userId,
                                              @PathVariable Long productId) {
        productService.deleteProduct(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
