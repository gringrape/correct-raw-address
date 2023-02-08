package com.gringrape.correctaddress.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class District {
    private final String name;
    private final District parent;
    private final Map<String, District> children = new HashMap<>();

    public District(String name, District parent) {
        this.name = name;
        this.parent = parent;
    }

    public String name() {
        return name;
    }

    public void addChild(String districtName) {
        if (children.containsKey(districtName)) {
            return;
        }

        children.put(
                districtName, new District(districtName, this)
        );
    }

    public Map<String, District> children() {
        return children;
    }

    public District leftMatchedChild(String query) {
        return children.values().stream()
                .filter(i -> query.startsWith(i.name()))
                .findFirst().orElseThrow();
    }

    public void addChildren(List<String> childrenNames) {
        childrenNames.forEach(this::addChild);
    }
}
