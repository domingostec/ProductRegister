package org.example.productregister.service;

import org.example.productregister.exceptions.NotFoundException;
import org.example.productregister.model.Product;
import org.example.productregister.model.User;
import org.example.productregister.repository.ProductRepository;
import org.example.productregister.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ValidationService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not Found"));
    }

    public Product validateProduct(Long productId, Long userId) {
        return productRepository.findByIdAndUserId(productId, userId)
                .orElseThrow(() -> new NotFoundException("Product Not Found with id: " + productId));
    }
}
