package com.gringrape.correctaddress.infrastructure;

import com.gringrape.correctaddress.Pair;
import com.gringrape.correctaddress.domain.District;
import com.gringrape.correctaddress.domain.DistrictRepository;
import com.gringrape.correctaddress.exceptions.RegionNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BasicDistrictRepository implements DistrictRepository {
    private final DistrictNameTree districtNameTree;

    private final SynonymMapper synonymMapper;

    public BasicDistrictRepository(DistrictNameTree districtNameTree, SynonymMapper synonymMapper) {
        this.districtNameTree = districtNameTree;
        this.synonymMapper = synonymMapper;
    }

    @Override
    public Pair findRegion(String query) {
        String canonicalQuery = synonymMapper.mapFirst(query);
        String regionName = findRegionName(canonicalQuery);

        District region = new District(regionName, null);

        List<String> childrenNames = districtNameTree.childDistricts(region.name());
        region.addChildren(childrenNames);

        return Pair.of(
                region,
                cutLeft(canonicalQuery, region.name())
        );
    }

    private String findRegionName(String query) {
        List<String> regionNames = districtNameTree.childDistricts();
        return regionNames.stream().filter(query::startsWith)
                .findFirst()
                .orElseThrow(RegionNotFoundException::new);
    }


    @Override
    public Pair<District, String> findCity(String query, District region) {
        String canonicalQuery = synonymMapper.mapFirst(query);

        if (region == null) {
            region = findRegionByQuery(canonicalQuery);
        }

        District city = region.leftMatchedChild(canonicalQuery);

        List<String> childrenNames = districtNameTree.childDistricts(region.name(), city.name());
        city.addChildren(childrenNames);

        return Pair.of(city, cutLeft(canonicalQuery, city.name()));
    }

    private District findRegionByQuery(String query) {
        List<String> regionNames = districtNameTree.childDistricts();
        for (String candidate : regionNames) {
            List<String> cityNames = districtNameTree.childDistricts(candidate);
            for (String cityName : cityNames) {
                if (query.startsWith(cityName)) {
                    District region = new District(candidate, null);
                    region.addChildren(cityNames);
                    return region;
                }
            }
        }
        return null;
    }

    private static String cutLeft(String query, String left) {
        return query.replaceFirst(left, "").trim();
    }

}
