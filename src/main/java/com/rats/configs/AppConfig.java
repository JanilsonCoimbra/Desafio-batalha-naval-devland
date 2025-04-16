package com.rats.configs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${custom.connection_string}")
    private String connectionString;

    @Value("${custom.topic_name}")
    private String topicName;

    @Value("${custom.subscription_name}")
    private String subscriptionName;

    public String getConnectionString() {
        return connectionString;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }
}