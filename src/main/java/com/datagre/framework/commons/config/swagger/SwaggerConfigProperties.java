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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ycaihua on 2017/3/31.
 * https://github.com/ycaihua/commons-config
 */
@Data
@ConfigurationProperties(prefix = "datagre.swagger")
public class SwaggerConfigProperties {
    private boolean enabled = false;
    private String pathRegex = "/.*";
    private String pathMapping = "/";
    private String basePackage = "com.datagre";

    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String license;
    private String licenseUrl;
    private ApiInfoConfig apiInfo;

    public SwaggerConfigProperties() {
        apiInfo = new ApiInfoConfig();
        apiInfo.setVersion("1.0.0");
        apiInfo.setTitle("DataGre API");
        apiInfo.setDescription("DataGre application rest api.");
    }
    @Data
    public static class ApiInfoConfig {
        private String version;
        private String title;
        private String description;
        @Override
        public String toString() {
            return "ApiInfoConfig{" +
                    "version='" + version + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SwaggerConfigProperties{" +
                "enabled=" + enabled +
                ", pathRegex='" + pathRegex + '\'' +
                ", pathMapping='" + pathMapping + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", termsOfServiceUrl='" + termsOfServiceUrl + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactUrl='" + contactUrl + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", license='" + license + '\'' +
                ", licenseUrl='" + licenseUrl + '\'' +
                ", apiInfo=" + apiInfo +
                '}';
    }
}
