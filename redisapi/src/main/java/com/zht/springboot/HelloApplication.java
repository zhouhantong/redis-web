package com.zht.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;

import static org.springframework.boot.Banner.*;

/**
 * Created by Administrator on 2018/3/19.
 */
@Controller
//不需要配置谁就排掉谁
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
@Configuration
public class HelloApplication {
    @RequestMapping("hello")
    @ResponseBody
    public String hello(@Value("${name}")String name){
        return "Hello word!"+name+"周寒通";
    }

    /**
     * 消息转换器，默认会根据你的字符集设置编码，可以自定义
     * @return
     */
    /*@Bean
    public StringHttpMessageConverter stringHttpMessageConverter(){
        StringHttpMessageConverter converter=new StringHttpMessageConverter(Charset.forName("ISO-8859-1"));
        return converter;
    }*/
    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(HelloApplication.class);
        application.setBannerMode(Mode.OFF);//关闭banner
        application.run(args);
    }
}
