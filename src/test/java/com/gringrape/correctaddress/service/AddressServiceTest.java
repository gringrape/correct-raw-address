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

    @Test
    @DisplayName("동의어 행정구역에 대해서도 매칭된 결과를 반환합니다.")
    void correct_synonym() {
        assertEquals(
                "전라남도 장성군 도동길",
                addressService.match("전남 장성 도동길로 보내주세요~~")
        );
    }

    @Test
    @DisplayName("도시레벨에서 두단어인 경우도 변환됩니다.")
    void correct_synonym_city() {
        assertEquals(
                "경기도 안산시 상록구 강촌로",
                addressService.match("경기 안산 상록 강촌로로 보내주세요~~")
        );
    }
}
