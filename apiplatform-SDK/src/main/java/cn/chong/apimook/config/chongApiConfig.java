package cn.chong.apimook.config;

import cn.chong.apimook.client.DidididiClient;
import cn.chong.apimook.client.UserClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--02 16:17
 * @description
 */

@ComponentScan
@ConfigurationProperties("didididi.api")
@Data
@Configuration
public class chongApiConfig {
    @Value("didididi.api.accessKey")
    private String accessKey;
    @Value("didididi.api.secretKey")
    private String secretKey;

    @Bean
    public UserClient getUserClient(){
        System.out.println(accessKey + secretKey);
        return new UserClient(accessKey, secretKey);
    }

    @Bean
    public DidididiClient getDidididiClient(){
        return new DidididiClient(accessKey, secretKey);
    }

}
