package com.gringrape.correctaddress.infrastructure;

import com.gringrape.correctaddress.Pair;
import com.gringrape.correctaddress.domain.District;
import com.gringrape.correctaddress.domain.DistrictRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BasicDistrictRepository implements DistrictRepository {
    private final DistrictNameTree districtNameTree;

    public BasicDistrictRepository(DistrictNameTree districtNameTree) {
        this.districtNameTree = districtNameTree;
    }

    @Override
    public Pair findRegion(String query) {
        String regionName = findRegionName(query);

        District region = new District(regionName, null);

        List<String> childrenNames = districtNameTree.childDistricts(region.name());
        region.addChildren(childrenNames);

        return Pair.of(
                region,
                leftCut(query, region.name())
        );
    }

    private String findRegionName(String query) {
        List<String> regionNames = districtNameTree.childDistricts();
        return regionNames.stream().filter(query::startsWith)
                .findFirst()
                .orElseThrow();
    }


    @Override
    public Pair<District, String> findCity(String query, District region) {
        District city = region.leftMatchedChild(query);

        List<String> childrenNames = districtNameTree.childDistricts(region.name(), city.name());
        city.addChildren(childrenNames);

        return Pair.of(city, leftCut(query, city.name()));
    }

    private static String leftCut(String query, String left) {
        return query.replaceFirst(left, "").trim();
    }

}
