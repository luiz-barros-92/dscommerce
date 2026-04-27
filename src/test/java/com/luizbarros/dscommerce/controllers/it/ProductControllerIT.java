package com.luizbarros.dscommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizbarros.dscommerce.dto.ProductDTO;
import com.luizbarros.dscommerce.entities.Category;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, invalidToken, adminToken;
	private String productName;
	private ProductDTO productDTO;
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";
		
		productName = "Macbook";
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		invalidToken = adminToken + "typo";
		
		Category category = new Category(2L, "Eletctronics");
		product = new Product(null, "PlaysStation 5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod", 3599.00, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg");
		product.getCategories().add(category);
		productDTO = new ProductDTO(product);
	}
	
	@Test
	public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
		ResultActions result = mockMvc
			.perform(get("/products?name={productName}", productName)
			.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(3L));
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[0].price").value(1250.0));
		result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
	}
	
	@Test
	public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
		ResultActions result = mockMvc
			.perform(get("/products", productName)
			.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));
		result.andExpect(jsonPath("$.content[0].price").value(90.5));
		result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
	}
	
	@Test
	public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(26L));
		result.andExpect(jsonPath("$.name").value("PlaysStation 5"));
		result.andExpect(jsonPath("$.description").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod"));
		result.andExpect(jsonPath("$.price").value(3599.00));
		result.andExpect(jsonPath("$.imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg"));
		result.andExpect(jsonPath("$.categories[0].id").value(2L));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception {
		product.setName("ab");
		productDTO = new ProductDTO(product);
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
		
		result.andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
		product.setDescription("ab");
		productDTO = new ProductDTO(product);
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
		
		result.andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() throws Exception {
		product.setPrice(-75.00);
		productDTO = new ProductDTO(product);
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
		
		result.andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
		product.setPrice(0.00);
		productDTO = new ProductDTO(product);
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
		
		result.andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndProductHasNotCategory() throws Exception {
		product.getCategories().clear();
		productDTO = new ProductDTO(product);
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + adminToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
		
		result.andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + clientToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc
			.perform(post("/products")
			.header("Authorization", "Bearer " + invalidToken)
			.content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
}
