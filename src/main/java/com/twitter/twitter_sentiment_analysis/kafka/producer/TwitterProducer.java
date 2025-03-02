package com.twitter.twitter_sentiment_analysis.kafka.producer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static com.twitter.twitter_sentiment_analysis.constants.ExceptionConstants.twitter_bearer_token_not_found;

@Slf4j
@Component
public class TwitterProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${twitter.bearer-token}")
    private String bearerToken;

    public TwitterProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void streamTweets(){
        if(StringUtils.isEmpty(bearerToken)){
            throw new IllegalArgumentException(twitter_bearer_token_not_found);
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true); // Required for OAuth2.0
        //cb.setOAuth2BearerToken(bearerToken);   // Set the Bearer Token
        Configuration configuration = cb.build();
    }
}
