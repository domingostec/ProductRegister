package org.example.productregister.dtos.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.productregister.dtos.request.ProductRequest;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;


    @NotBlank(message = "Name cannot be null")
    private String name;


    private String numberPhone;

    private List<ProductRequest> products;


    public UserResponse(String email, String name, String numberPhone) {
        this.email = email;
        this.name = name;
        this.numberPhone = numberPhone;
    }

}
