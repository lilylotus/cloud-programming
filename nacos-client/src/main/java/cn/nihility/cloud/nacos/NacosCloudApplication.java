package cn.nihility.cloud.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosCloudApplication.class, args);
    }

}
