package com.app.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import com.app.controller.ApiController;
import com.app.entity.User;
import com.app.repository.BoardRepository;
import com.app.repository.UserRepository;
import com.app.service.impl.BoardServiceImpl;
import com.app.service.impl.TicTacToeServiceImpl;
import com.app.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@AutoConfigureMockMvc
@WebMvcTest(controllers=ApiController.class)
@Import(ApiController.class)
public class ApiControllerTest {
	@Autowired
	private MockMvc mvc;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@MockBean
    private BoardRepository boardRepository;

    @MockBean
    private UserServiceImpl userService; 
    
    @MockBean
    private UserRepository userRepository; 
    
    @MockBean
    private BoardServiceImpl boardService; 
    
    @MockBean
    private TicTacToeServiceImpl ticTacToeService;


    @Configuration
    @EnableWebSecurity
    public static class TestSecurityConfig {
     
		@Bean
        public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable()) 
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    .anyRequest().permitAll()
                    );

            return http.build();
        }
    }
	
//	@Test
//	public void saveUser() throws UnsupportedEncodingException, Exception {
//		User newUser = new User(22L,"aaaa","pa55word",null);
//
//		
//		mvc.perform(
//				post("/api/user")
//				.content(mapper.writeValueAsString(newUser))
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(201));
//	}
	
	
	@Test
	public void errorSavingUser() throws UnsupportedEncodingException, Exception {
		User newUser = new User(22L,"","pa55word",null);
		
		mvc.perform(
				post("/api/user")
				.content(mapper.writeValueAsString(newUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());				
	}
	
	@Test
	public void getAllUsers() throws Exception {

		mvc.perform(
				get("/api/user")
				.accept(MediaType.ALL))
				.andExpect(status().isOk());
	}
	
	@Test
	public void startGame() throws Exception {

		mvc.perform(
				post("/api/start/1")
				.accept(MediaType.ALL))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void playGame() throws Exception {
		Game game = new Game();
		
		mvc.perform(
				post("/api/play")
				.content(mapper.writeValueAsString(game))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isNotFound());
	}
	
	
	@Data
	class Game{
		private long boardId;
		private int position;
		private String name;
	}

	
	
}
