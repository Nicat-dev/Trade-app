package com.nm.ms.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountApplication.class, args);
    }

}
