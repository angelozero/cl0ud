package com.angelozero.cl0ud.component;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.auth_jwt.entrypoint.AuthController;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RefreshTokenRequest;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.config.component.JwtComponentTestConfiguration;
import com.angelozero.cl0ud.ztemplate.jwt.RegisterRequestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class JwtControllerComponentTest extends JwtComponentTestConfiguration {

    private static final String REGISTER_URL = "/register";
    private static final String AUTHENTICATE_URL = "/authenticate";
    private static final String REFRESH_TOKEN_URL = "/refresh-token";

    @Autowired
    private AuthController controller;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate.jwt");
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldDoARequestToRegisterAnUser() throws Exception {

        clearDataRepository();
        List<UserEntity> userEntityList = findAllUsers();
        assertTrue(userEntityList.isEmpty());

        RegisterRequest registerRequestFixture = Fixture.from(RegisterRequest.class).gimme(RegisterRequestTemplate.VALID_REGISTER_REQUEST);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(registerRequestFixture)))
                .andExpect(status().isNoContent())
                .andReturn();

        List<UserEntity> usersFinalList = findAllUsers();
        assertFalse(usersFinalList.isEmpty());

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

        clearDataRepository();
    }


    @Test
    public void shouldDoARequestToAuthenticateAnUser() throws Exception {

        clearDataRepository();

        RegisterRequest registerRequestFixture = Fixture.from(RegisterRequest.class).gimme(RegisterRequestTemplate.VALID_REGISTER_REQUEST);

        AuthenticationRequest body = AuthenticationRequest.builder()
                .email(registerRequestFixture.getEmail())
                .password(registerRequestFixture.getPassword())
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(registerRequestFixture)))
                .andExpect(status().isNoContent())
                .andReturn();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(body)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertFalse(getJsonValue(result.getResponse().getContentAsString(), "token").isEmpty());
        clearDataRepository();
    }

    @Test
    public void shouldDoARequestToGenerateARefreshToken() throws Exception {

        clearDataRepository();

        RegisterRequest registerRequestFixture = Fixture.from(RegisterRequest.class).gimme(RegisterRequestTemplate.VALID_REGISTER_REQUEST);

        AuthenticationRequest body = AuthenticationRequest.builder()
                .email(registerRequestFixture.getEmail())
                .password(registerRequestFixture.getPassword())
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(registerRequestFixture)))
                .andExpect(status().isNoContent())
                .andReturn();

        MvcResult authenticatedUser = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(body)))
                .andExpect(status().isOk())
                .andReturn();

        String refreshToken = getJsonValue(authenticatedUser.getResponse().getContentAsString(), "refreshToken");

        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTH_API_URL + REFRESH_TOKEN_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(refreshTokenRequest)))
                .andExpect(status().isOk())
                .andReturn();



        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertFalse(getJsonValue(result.getResponse().getContentAsString(), "token").isEmpty());
        clearDataRepository();
    }

    private List<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    private String getJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("[COMPONENT TEST][PERSON CONTROLLER] - Error to convert an object to json string");
        }
    }

    private String getJsonValue(String jsonString, String key) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return String.valueOf(jsonObject.get(key));
    }
}
