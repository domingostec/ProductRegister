package org.example.productregister.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductRequest {

    @NotBlank(message = "The field cannot be empty.")
    @NotNull(message = "The field cannot be empty.")
    @Schema(description = "name", example = "Mouse Pad")
    private String name;

    @DecimalMin(value = "0.01", message = "Please enter a valid value.")
    @DecimalMax(value = "10000000000.00", message = "Please enter a valid value")
    @Schema(description = "price", example = "32.50")
    private BigDecimal price;

    @Min(value = 0, message = "Please enter a valid value.")
    @Max(value = 1000000, message = "Please enter a valid value")
    @Schema(description = "quantity", example = "1")
    private Integer quantity;

    @NotBlank(message = "The field cannot be empty.")
    @NotNull(message = "The field cannot be empty.")
    @Schema(description = "category", example = "Peripherals")
    private String category;

}
