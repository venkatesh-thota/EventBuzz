package com.stackroute.config;


import com.stackroute.domain.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;


    @Configuration
    @EnableKafka
    public class KafkaConsumerConfig {

        @Bean
        public ConsumerFactory<String,Object> consumerFactory(){
            Map<String,Object> configs = new HashMap<>();
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
            configs.put(ConsumerConfig.GROUP_ID_CONFIG,"kafkaconsumer");
            return new DefaultKafkaConsumerFactory<>(configs);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String,Object> kafkaListenerContainerFactory(){
            ConcurrentKafkaListenerContainerFactory<String,Object> factory = new ConcurrentKafkaListenerContainerFactory<String,Object>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }



        @Bean
        public ConsumerFactory<String, User> userConsumerFactory(){
            Map<String,Object> configs = new HashMap<>();
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            configs.put(ConsumerConfig.GROUP_ID_CONFIG,"kafkauserconsumer");


            return new DefaultKafkaConsumerFactory<>(configs,new StringDeserializer(),new org.springframework.kafka.support.serializer.JsonDeserializer<>((User.class)));
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, User> kafkaUserListenerContainerFactory(){
            ConcurrentKafkaListenerContainerFactory<String,User> factory = new ConcurrentKafkaListenerContainerFactory<String,User>();
            factory.setConsumerFactory(userConsumerFactory());
            return factory;
        }
    }

