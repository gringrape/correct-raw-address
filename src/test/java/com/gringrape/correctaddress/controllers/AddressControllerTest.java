package com.gringrape.correctaddress.controllers;


import com.gringrape.correctaddress.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    @DisplayName("GET /address 요청에 성공적으로 응답합니다")
    void correct() throws Exception {
        mockMvc.perform(get("/address")
                .queryParam(
                        "rawAddress", "충청남도 청양군 양사길로 보내주세요~"
                )).andExpect(status().isOk());
    }
}
