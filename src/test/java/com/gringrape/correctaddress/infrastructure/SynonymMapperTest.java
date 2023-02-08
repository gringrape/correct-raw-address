package com.gringrape.correctaddress.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SynonymMapperTest {
    @Test
    @DisplayName("동의어 매핑을 추가하면, 해당 매핑에 대한 동의어 변환을 합니다")
    void map() {
        SynonymMapper synonymMapper = new SynonymMapper();

        synonymMapper.addMapping("전남", "전라남도");

        assertEquals("전라남도", synonymMapper.map("전남"));
    }

    @Test
    @DisplayName("매핑 관계가 존재하지 않는 경우에는 질의어를 반환합니다")
    void map_query_not_exist() {
        SynonymMapper synonymMapper = new SynonymMapper();

        assertEquals("전남", synonymMapper.map("전남"));
    }

    @Test
    @DisplayName("주어진 문자열의 앞부분에 대해서 매핑된 결과를 반환합니다.")
    void mapFirst() {
        SynonymMapper synonymMapper = new SynonymMapper();

        synonymMapper.addMapping("전남", "전라남도");

        assertEquals("전라남도 장성 덕진덕산길", synonymMapper.mapFirst("전남 장성 덕진덕산길"));
    }
}
