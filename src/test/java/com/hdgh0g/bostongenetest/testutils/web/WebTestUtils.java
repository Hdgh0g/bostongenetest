package com.hdgh0g.bostongenetest.testutils.web;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.springframework.test.web.servlet.MockMvc;

public final class WebTestUtils {

    public static MockMvcRequestSpecification given(MockMvc mockMvc) {
        return RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON);
    }

}
