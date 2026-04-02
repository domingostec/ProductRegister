package org.example.productregister.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String numberPhone;

    public User(String name, String email, String numberPhone) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> product;
}
