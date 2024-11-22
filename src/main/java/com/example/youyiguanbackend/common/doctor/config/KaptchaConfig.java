package com.example.youyiguanbackend.common.doctor.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {


    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties prop = new Properties();
        prop.setProperty(Constants.KAPTCHA_BORDER, "no");
        prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        //验证码的取值范围
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
        //验证码字符个数
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");

        Config config = new Config(prop);

        kaptcha.setConfig(config);

        return kaptcha;
    }
}
