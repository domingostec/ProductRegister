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

    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product);
    }

    public ProductResponse includeProduct(ProductRequest request, Long userId){
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not found"));


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


        if (products.isEmpty()){
            throw new NotFoundException("Your List is Empty");
        }

        return  products.stream()
                .map(this::toResponse)
                .toList();
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

    public void deleteProduct(Long userId, Long productId){

        if(productId == null){
            throw new InvalidArgumentsException("ID cannot be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not Found"));


        Product product = productRepository.findByIdAndUserId(productId, userId)
                .orElseThrow(() -> new NotFoundException(("Product Not Found with id: " + productId)));

        productRepository.delete(product);
    }

}
