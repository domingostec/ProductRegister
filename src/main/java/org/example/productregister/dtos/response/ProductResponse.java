package org.example.productregister.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.productregister.model.Product;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    @Schema(description = "name", example = "Mouse Pad")
    private String name;

    @Schema(description = "price", example = "32.50")
    private BigDecimal price;

    @Schema(description = "quantity", example = "1")
    private Integer quantity;

    @Schema(description = "category", example = "Peripherals")
    private String category;

    public ProductResponse(Product product){
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.category = product.getCategory();
    }

}
