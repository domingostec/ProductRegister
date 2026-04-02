package org.example.productregister.service;

import org.example.productregister.dtos.request.ProductRequest;
import org.example.productregister.dtos.response.ProductResponse;
import org.example.productregister.exceptions.InvalidArgumentsException;
import org.example.productregister.exceptions.NotFoundException;
import org.example.productregister.model.Product;
import org.example.productregister.model.User;
import org.example.productregister.repository.ProductRepository;
import org.example.productregister.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private  final ProductRepository productRepository;
    private final ValidationService validationService;

    public ProductService(ProductRepository productRepository,ValidationService validationService){
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product);
    }

    public ProductResponse includeProduct(ProductRequest request, Long userId){
        User user =  validationService.validateUser(userId);

        Product newProduct = new Product(
                 request.getName(),
                 request.getPrice(),
                 request.getQuantity(),
                 request.getCategory()
         );

        newProduct.setUser(user);
          productRepository.save(newProduct);
          return toResponse(newProduct);
    }


    public List<ProductResponse> listProducts(Long userId){
            List<Product> products = productRepository.findByUser_Id(userId);

        return  products.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> listProductsByCategory(String category, Long userId) {

        List<Product> products = productRepository.findByCategoryIgnoreCaseAndUser_Id(category, userId);

        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse updateProduct(Long userId, Long productId,ProductRequest request){

        User user = validationService.validateUser(userId);
        Product product = validationService.validateProduct(productId, userId);

        if (request.getName() != null) product.setName(request.getName());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getCategory() != null) product.setCategory(request.getCategory());

        Product updateProduct = productRepository.save(product);
        return toResponse(updateProduct);

    }

    public void deleteProduct(Long userId, Long productId){

        Product product = validationService.validateProduct(productId, userId);

        productRepository.delete(product);
    }

}
