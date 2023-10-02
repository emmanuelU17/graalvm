package com.graalvm.auth;

import com.graalvm.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends AbstractIntegrationTest {

    private final String url = "/api/v1/auth";

    @Test
    void login() throws Exception {
        // payload
        var dto = new AuthDTO("TestUser", "password");

        // request
        this.MOCKMVC
                .perform(post(this.url)
                        .content(this.MAPPER.writeValueAsString(dto))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void privateRoute() throws Exception {
        //payload
        var dto = new AuthDTO("TestUser", "password");

        // login request
        MvcResult result = this.MOCKMVC
                .perform(post(this.url)
                        .content(this.MAPPER.writeValueAsString(dto))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        // jwt
        String res = result.getResponse().getContentAsString();

        // priv route req
        this.MOCKMVC
                .perform(get(this.url)
                        .header("Authorization", "Bearer %s".formatted(res))
                )
                .andExpect(status().isOk());
    }

    @Test
    void privRouteException() throws Exception {
        // priv route req
        this.MOCKMVC
                .perform(get(this.url)
                        .header("Authorization", "Bearer ")
                )
                .andExpect(status().isUnauthorized());
    }

}