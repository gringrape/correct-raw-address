package com.gringrape.correctaddress.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
    @DisplayName("정확한 행정구역 입력에 대해서 올바른 응답을 반환합니다.")
    void correct() {
        assertEquals(
                "충청남도 청양군 양사길",
                addressService.match("충청남도 청양군 양사길로 보내주세요~~")
        );
    }
}
