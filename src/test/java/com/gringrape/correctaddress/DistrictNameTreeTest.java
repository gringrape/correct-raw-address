package com.gringrape.correctaddress;

import com.gringrape.correctaddress.dto.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class DistrictNameTreeTest {
    @Test
    @DisplayName("하나의 행정구역 주소를 삽입함")
    public void insertAddressNames() {
        // GIVEN
        DistrictNameTree addressNameTree = new DistrictNameTree();
        Address address = new Address("서울특별시", "종로구", "청운동");

        // WHEN
        addressNameTree.insertAddress(address);

        // THEN
        assertEquals(List.of("종로구"), addressNameTree.childDistricts("서울특별시"));
        assertEquals(List.of("청운동"), addressNameTree.childDistricts("서울특별시", "종로구"));
    }
}
