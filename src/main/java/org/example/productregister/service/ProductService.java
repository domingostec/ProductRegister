package org.example.productregister.service;

import org.example.productregister.dtos.request.ProductRequest;
import org.example.productregister.dtos.response.ProductResponse;
import org.example.productregister.exceptions.InvalidArgumentsException;
import org.example.productregister.exceptions.NotFoundException;
import org.example.productregister.model.Product;
import org.example.productregister.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private  final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product);
    }

    public ProductResponse includeProduct(ProductRequest request){
         Product newProduct = new Product(
                 request.getName(),
                 request.getPrice(),
                 request.getQuantity(),
                 request.getCategory()
         );

          productRepository.save(newProduct);
          return toResponse(newProduct);
    }

    public List<Product> listProducts(){

        if (productRepository.findAll().isEmpty()){
            throw new NotFoundException("Your List is Empty");
        }

        return productRepository.findAll();
    }

    public List<ProductResponse> listProductsByCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new NotFoundException("To filter, the field cannot be empty.");
        }

        List<Product> products = productRepository.findByCategoryIgnoreCase(category).stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();

        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for category " + category);
        }

        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse updateProduct(Long id,ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        if (request.getName() != null) product.setName(request.getName());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getCategory() != null) product.setCategory(request.getCategory());

        Product updateProduct = productRepository.save(product);
        return toResponse(updateProduct);

    }

    public void deleteProduct(Long id){
        if(id == null){
            throw new InvalidArgumentsException("ID cannot be null");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(("Product Not Found with id: " + id)));
        productRepository.delete(product);
    }

}
