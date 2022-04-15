package net.atos.kafka.config;

import net.atos.kafka.entity.Employee;
import net.atos.kafka.entity.PageEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean(name = "producerSimple")
    ProducerFactory<String, String> simpleProducerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:29092,localhost:39092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "producerEmp")
    ProducerFactory<String, Employee> empProducerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:29092,localhost:39092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "producerPage")
    ProducerFactory<String, PageEvent> pageEventProducerFactory(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:29092,localhost:39092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean(name = "kafkaTempSim")
    KafkaTemplate<String,String> simpleKafkaTemplate(){
        return new KafkaTemplate<>(simpleProducerFactory());
    }

    @Bean(name = "kafkaTempEmp")
    KafkaTemplate<String,Employee> employeeKafkaTemplate(){
        return new KafkaTemplate<>(empProducerFactory());
    }

    @Bean(name = "kafkaTempPage")
    KafkaTemplate<String,PageEvent> stringPageEventKafkaTemplate(){
        return new KafkaTemplate<>(pageEventProducerFactory());
    }
}
