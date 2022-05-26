package com.platon.datum.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加密文件配置
 */
@Slf4j
@Configuration
@Component
public class JasyptConfig {
    /*
      第一个配置文件路径为部署环境路径，用于部署环境加载配置文件里的值
      第二个配置文件路径为本地打包环境路径
     */
    static {
        File saltFile = FileUtils.getFile(System.getProperty("user.dir"), "jasypt.properties");
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(saltFile)) {
            properties.load(in);
            String salt = properties.getProperty("jasypt.encryptor.password");
            if (StringUtils.isBlank(salt)) {
                throw new RuntimeException("salt not null");
            }
            salt = salt.trim();
            System.setProperty("JASYPT_ENCRYPTOR_PASSWORD", salt);
            log.debug("salt:{}", salt);
        } catch (IOException e) {
            log.error("error:{}", e.getMessage());
        }
    }
}