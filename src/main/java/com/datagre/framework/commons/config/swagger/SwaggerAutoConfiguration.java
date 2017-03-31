/**
 * Copyright 2016-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datagre.framework.commons.config.swagger;

import lombok.extern.apachecommons.CommonsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by ycaihua on 2017/3/31.
 * https://github.com/ycaihua/commons-config
 */
@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
@ConditionalOnBean(Docket.class)
@ConditionalOnProperty(prefix = "datagre.swagger",name = "enabled",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(SwaggerConfigProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SwaggerAutoConfiguration {
    private final static Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);
    private SwaggerConfigProperties configProperties;

    public SwaggerAutoConfiguration(SwaggerConfigProperties properties) {
        this.configProperties = properties;
    }

    @PostConstruct
    public void validate() {
        log.info("===>>Swagger auto config : {}", this.configProperties);
    }

    @Bean
    public Docket swaggerSpringfoxDocket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                configProperties.getContactName(),
                configProperties.getContactUrl(),
                configProperties.getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                configProperties.getApiInfo().getTitle(),
                configProperties.getApiInfo().getDescription(),
                configProperties.getApiInfo().getVersion(),
                configProperties.getTermsOfServiceUrl(),
                contact,
                configProperties.getLicense(),
                configProperties.getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(configProperties.getBasePackage()))
                .paths(regex(configProperties.getPathRegex()))
                .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
