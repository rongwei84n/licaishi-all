package com.auts.lcscli;

import com.alibaba.fastjson.parser.ParserConfig;
import com.auts.lcscli.util.UidGeneraterInit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
//import org.springframework.web.client.RestTemplate;

/**
 *  理财师端入口.
 *  @author rongwei.huang
 */
@SpringBootApplication(scanBasePackages = { "com.auts.lcscli.**" })
@PropertySources({ @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true) })
@MapperScan("com.auts.lcscli.dao.**")
//@EnableCircuitBreaker
//@EnableDiscoveryClient
//@EnableEurekaClient
public class LicasiCliServerMain {
    private static Logger logger = LogManager.getLogger(LicasiCliServerMain.class);

    static {
        ParserConfig.getGlobalInstance().addAccept("com.auts.lcscli.model.");
    }

//    /**
//     * rest接口.
//     * @return rest模板
//     */
//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    /**
     * 项目启动方法入口.
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(LicasiCliServerMain.class, args);

        UidGeneraterInit.instance.start();

        logger.info("startup success!");
    }

}
