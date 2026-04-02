package org.example.productregister.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer quantity ;
    private String category;

    public Product(String name, BigDecimal price, Integer quantity , String category){
        this.name = name;
        this.price = price;
        this.quantity  = quantity ;
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;
}
