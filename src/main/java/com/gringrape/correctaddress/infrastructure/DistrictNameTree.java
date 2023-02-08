package com.gringrape.correctaddress.infrastructure;

import com.gringrape.correctaddress.dto.Address;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
final public class DistrictNameTree {
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        File file = ResourceUtils.getFile("classpath:addresses.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");
                this.insertAddress(new Address(values[0], values[1], values[2]));
            }
        }
    }

    final private DistrictNameNode root = new DistrictNameNode("");

    public void insertAddress(Address address) {
        DistrictNameNode node = root;
        for (String districtName : address.districtNames()) {
            node = node.addChild(districtName);
        }
    }

    public List<String> childDistricts(String... districtNames) {
        DistrictNameNode node = root;
        for (String districtName : districtNames) {
            node = node.child(districtName);
        }

        return node.children();
    }
}
