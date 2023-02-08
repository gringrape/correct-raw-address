package com.gringrape.correctaddress.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistrictNameNode {
    private final String name;
    private final Map<String, DistrictNameNode> children = new HashMap<>();

    public DistrictNameNode(String name) {
        this.name = name;
    }

    public DistrictNameNode addChild(String districtName) {
        if (children.containsKey(districtName)) {
            return children.get(districtName);
        }
        DistrictNameNode newNode = new DistrictNameNode(districtName);
        children.put(districtName, newNode);
        return newNode;
    }

    public List<String> children() {
        return children.keySet().stream().toList();
    }

    public DistrictNameNode child(String districtName) {
        return children.get(districtName);
    }
}
