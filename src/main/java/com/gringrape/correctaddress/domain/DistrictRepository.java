package com.gringrape.correctaddress.domain;

import com.gringrape.correctaddress.Pair;

public interface DistrictRepository {
    Pair<District, String> findRegion(String query);

    Pair<District, String> findCity(String query, District region);
}
