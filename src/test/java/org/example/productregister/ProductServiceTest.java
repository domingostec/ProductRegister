package org.example.productregister;

import org.example.productregister.dtos.response.ProductResponse;
import org.example.productregister.dtos.request.ProductRequest;
import org.example.productregister.exceptions.NotFoundException;
import org.example.productregister.model.Product;
import org.example.productregister.repository.ProductRepository;
import org.example.productregister.service.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    //Injeção Mockada do ProducutService
    @InjectMocks
    private ProductService service;

    //  Mock do ProductRepository - simula o comportamento sem acessar banco real
    @Mock
    private ProductRepository repo;

    @Nested
    class includeProductTest {

        @Test
        void deveIncluirProdutos() {

            //Arrange - Preparação
            ProductRequest request = new ProductRequest(
                    "Notebook",
                    BigDecimal.valueOf(3500.0),
                    10,
                    "Eletronicos"
            );

            Product produtoSimulado = new Product(
                    request.getName(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getCategory()
            );

            //Config Mock - quando o metodo repo.save(produtoSimulado) for chamado retorne produto simulado
            when(repo.save(produtoSimulado)).thenReturn(produtoSimulado);

            //Act - execução
            ProductResponse response = service.includeProduct(request);

            //Assert - validação

            // Espera que o nome do objeto response seja "Notebook" - teste green
            assertEquals("Notebook", response.getName());

            // Espera que o preço do objeto response seja 3500.00 - teste green
            assertEquals(BigDecimal.valueOf(3500.00), response.getPrice());

            // Espera que a quantidade do objeto response seja 10 - teste green
            assertEquals(10, response.getQuantity());

            // Espera que a categoria do objeto response seja "Eletronicos" - teste green
            assertEquals("Eletronicos", response.getCategory());

            // Verifica se o metodo save(Qualquer objeto da classe produto)  foi usado - verificação green
            verify(repo, times(1)).save(produtoSimulado);
        }
    }

    @Nested
    class testListProducts {

        @Test
        void deveListarComSucesso() {
            //Arrange - Preparação
            List<Product> produtosSimulados = List.of(
                    new Product("Notebook", BigDecimal.valueOf(3500.0), 6, "Eletronicos"),
                    new Product("Mouse", BigDecimal.valueOf(150.0), 50, "Periféricos")
            );

            //Mock Config - Quando o metodo repo.findAll() for chamado retorne produtosSimulados
            when(repo.findAll()).thenReturn(produtosSimulados);

            //Act - execução
            List<Product> resultado = service.listProducts();

            //Assert - Validação

            // Espera que o numero de elementos retornados da lista resultado seja 2 - teste green
            assertEquals(2, resultado.size());

            // Espera que o nome do indice 0 da lista resultado seja "Notebook" - teste green
            assertEquals("Notebook", resultado.get(0).getName());

            // Verifica se o metodo findAll foi acionado - verificação green
            verify(repo, times(1)).findAll();
        }

        @Test
        void deveRetornarNotFoundException(){
            List<Product> products = new ArrayList<>();

            when(repo.findAll()).thenReturn(products);

            assertThrows(NotFoundException.class, () -> service.listProducts());

            verify(repo, times(1)).findAll();

        }
    }

    @Nested
    class testListProductByCategory {

        @Test
        void deveFiltrarComSucessoCasoCategoriaSejaValida() {
            String category = "Eletronicos";

            //Arrange - preparação
            List<Product> produtosSimulados = List.of(
                    new Product("Notebook", BigDecimal.valueOf(3500.0), 6, "Eletronicos"),
                    new Product("Mouse", BigDecimal.valueOf(150.0), 50, "Periféricos"),
                    new Product("Teclado", BigDecimal.valueOf(256), 6, "Eletronicos")
            );

            // Config Mock - Quando o metodo findByCategoryIgnoreCase for chamado com o parametro category retorne a lista de produtosSimulados
            when(repo.findByCategoryIgnoreCase(category)).thenReturn(produtosSimulados);

            //Act - ação
            List<ProductResponse> resultado = service.listProductsByCategory(category);

            //Assert - validação

            //Espera que o numero de elementos retornados da lista resultado seja 2 - teste green
            assertEquals(2, resultado.size());

            // Espera que o nome do indice 0 da lista resultado seja "Notebook" - teste green
            assertEquals("Notebook", resultado.get(0).getName());

            //Espera que o nome do indice 1 da lista resultado seja "Teclado" - teste green
            assertEquals("Teclado", resultado.get(1).getName());

            // Verifica se o metodo findByCategoryIgonoreCase(category) foi acionado - verificação green
            verify(repo, times(1)).findByCategoryIgnoreCase(category);
        }

        @Test
        void deveLancarNotFoundExceptionQuandoCategoriaForNula(){

            String input = "";

            //Action - Chame a Exception NotFoundException quando ultilizar service.listProductByCategory(input)
            assertThrows(NotFoundException.class, () -> service.listProductsByCategory(input));

            verify(repo,never()).findByCategoryIgnoreCase(any());
        }

        @Test
        void deveLancarNotFoundExceptionQuandoCategoriaNaoExiste(){
            String input = "Games";

            when(repo.findByCategoryIgnoreCase(input)).thenReturn(List.of());

            assertThrows(NotFoundException.class, () -> service.listProductsByCategory(input));

            verify(repo, times(1)).findByCategoryIgnoreCase(input);
        }
    }

    @Nested
    class testeDeleteById {

        @Test
        void deveDeletarComSucessoCasoIdSejaValido() {
            //Arrange - preparação
            Long id = 1L;
            Product produtoSimulado = new Product("Notebook", BigDecimal.valueOf(3500.0), 6, "Eletronicos");

            // Config Mock - quando o metodo repo.findById(id) for chamado retorne produtoSimulado
            when(repo.findById(id)).thenReturn(Optional.of(produtoSimulado));

            //Act - Ação
            service.deleteProduct(id);

            //Assert - validação (Verifica se o metodo delete(produtoSimulado) foi acionado - verificação green)
            verify(repo, times(1)).delete(produtoSimulado);
        }

        @Test
        void deveLancarNotFoundExceptionQuandoIdNaoExiste(){

            // Config Mock - quando o metodo findByid(99L) for acionado retorne um Optional vazio
            when(repo.findById(99L)).thenReturn(Optional.empty());


            //Act - Ação + Assert (Espera que ao usar service.deleteProduct(99l) solte uma NotFoundException)
            assertThrows(NotFoundException.class, () -> service.deleteProduct(99L));


            // Verifica se o metodo delete não foi acionado - verificação green
            verify(repo,never()).delete(any());
        }
    }

    @Nested
    class testeUpdateProcut{

        @Test
        void deveAtualizarProdutoComSucesso(){

            Long id = 1L;
            Product product = new Product(
                    "Mouse",
                    BigDecimal.valueOf(220.00),
                    2,
                    "Perifericos"
            );

            product.setId(id);

            ProductRequest request = new ProductRequest(
                    "Notebook",
                    BigDecimal.valueOf(2300.99),
                    9,
                    "Eletronics"
            );

            Product productAtualizado = new Product(
                    request.getName(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getCategory()
            );
            productAtualizado.setId(id);

            when(repo.findById(id)).thenReturn(Optional.of(product));
            when(repo.save(product)).thenReturn(productAtualizado);

            ProductResponse response = service.updateProduct(id, request);

            assertNotNull(response);
            assertEquals("Notebook", response.getName());
            assertEquals(BigDecimal.valueOf(2300.99), response.getPrice());
            assertEquals("Eletronics", response.getCategory());

            verify(repo).save(product);
        }

        @Test
        void deveRetornarNotFoundExceptionQuandoIdNaoExiste(){

            Long id = 1L;
            Product product = new Product(
                    "Mouse",
                    BigDecimal.valueOf(220.00),
                    2,
                    "Perifericos"
            );
            product.setId(id);

            ProductRequest request = new ProductRequest(
                    "Notebook",
                    BigDecimal.valueOf(2300.99),
                    9,
                    "Eletronics"
            );

            // Mock configurado para retorar um Optional vazio para findById(99L)
            when(repo.findById(99L)).thenReturn(Optional.empty());


            //Act - Ação + Assert (Espera que ao usar service.updateProduct(99l, request) solte uma NotFoundException)
            assertThrows(NotFoundException.class, () -> service.updateProduct(99L, request));


            // Verifica se o metodo findById(99l) foi acionado - verificação green
           verify(repo, times(1)).findById(99L);

           // verifica se o metodo save.product nunca é acionado
           verify(repo, never()).save(product);

        }


    }


}

