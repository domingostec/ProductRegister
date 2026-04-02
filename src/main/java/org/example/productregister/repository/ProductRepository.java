package org.example.productregister.repository;

import org.example.productregister.model.Product;
import org.example.productregister.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByUser_Id(Long userId);
    Optional<Product> findByIdAndUserId(Long productId, Long userId);
    List<Product> findByCategoryIgnoreCaseAndUser_Id(String category, Long userId);



}
