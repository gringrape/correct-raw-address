package com.gringrape.correctaddress.service;

import com.gringrape.correctaddress.Pair;
import com.gringrape.correctaddress.domain.District;
import com.gringrape.correctaddress.domain.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final DistrictRepository districtRepository;

    public AddressService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public String match(String rawAddress) {
        return matchRegion(rawAddress);
    }

    private String matchRegion(String rawAddress) {
        Pair<District, String> pair = districtRepository.findRegion(rawAddress);

        District region = pair.first();
        String remain = pair.second();

        return region.name() + " " + matchCity(remain, region);
    }

    private String matchCity(String query, District region) {
        Pair<District, String> pair = districtRepository.findCity(query, region);

        District city = pair.first();
        String remain = pair.second();

        return city.name() + " " + matchStreet(remain, city);
    }

    private String matchStreet(String remain, District city) {
        District street = city.leftMatchedChild(remain);
        return street.name();
    }
}
