import com.example.PetStore.entities.Product;
import com.example.PetStore.exceptions.ProductAdvice;
import com.example.PetStore.controllers.ProductController;
import com.example.PetStore.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private MockMvc mvc;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private JacksonTester<Product> jsonProduct;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ProductAdvice())
                .build();
        productRepository.save(new Product(1L,"Toto", 5.99f, 4));
    }

    @Test
    public void canGetByIdWhenExists() throws Exception {
        given(productRepository.getOne(1L))
                .willReturn(new Product(1L,"Toto", 5.99f, 4));

        MockHttpServletResponse response = mvc.perform(
                get("/products/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProduct.write(new Product(1L,"Toto", 5.99f, 4))
        );
    }
}
