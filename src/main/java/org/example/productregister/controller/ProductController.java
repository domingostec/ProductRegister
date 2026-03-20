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

    @Operation(summary = "Inclui o objeto produto", description = "retorna um objeto response que posteriormente será um product")
    @PostMapping("/include")
    public ResponseEntity<ProductResponse> includeProduct(
            @Valid  @RequestBody ProductRequest request){
        ProductResponse response = productService.includeProduct(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<?> listAllProducts(){
        List<Product> products = productService.listProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{category}")
    public List<ProductResponse> listProductsByGenre(
            @Parameter(description = "Requer uma String referente a categoria do obj como parametro", example = "/eletronics")
            @Valid @PathVariable String category){
            return productService.listProductsByCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProcut(@PathVariable Long id,
                                                @RequestBody ProductRequest request){

        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable  Long id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
