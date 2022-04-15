package net.atos.kafka.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.log4j.Log4j2;
import net.atos.kafka.entity.Employee;
import net.atos.kafka.entity.PageEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumerConfig {

    @KafkaListener(topics = {"topictest1"}, groupId = "sp-consumer")
    public void onMessage(ConsumerRecord<String,String> message){
        log.info("Receiving message from controller with key : "+message.key()
                +" ,to publish message : "+message.value());
    }

    @KafkaListener(topics = {"testTopicSp"}, groupId = "sp-consumer2")
    public void onPublishPerson(ConsumerRecord<String, Employee> message){
        log.info("Receiving message from controller with key : "+message.key()
                +" ,to publish employee : "+message.value()
                +" , at offset : "+message.offset());
    }

    @KafkaListener(topics = {"testTopicSp2"}, groupId = "sp-consumer3")
    public void onPublishPage(ConsumerRecord<String, String> message) throws Exception {
        PageEvent pageEvent = serdePageEvent(message.value());
        log.info("Receiving message from controller with key : "+message.key()
                +" ,to send details about page : "+pageEvent.getPage()
                + " for date : "+pageEvent.getDate()
                + " the user spend +" +pageEvent.getDuration() +"s"
                +" , at offset : "+message.offset());
    }

    private PageEvent serdePageEvent(String jsonPageEvent) throws Exception{
        JsonMapper jsonMapper = new JsonMapper();
        return jsonMapper.readValue(jsonPageEvent, PageEvent.class);
    }
}
