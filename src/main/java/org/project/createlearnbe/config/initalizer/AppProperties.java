package org.project.createlearnbe.config.initalizer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private boolean initMock;

    public boolean isInitMock() {
        return initMock;
    }

    public void setInitMock(boolean initMock) {
        this.initMock = initMock;
    }
}
