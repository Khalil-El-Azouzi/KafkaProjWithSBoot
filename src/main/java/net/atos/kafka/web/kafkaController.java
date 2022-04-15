package net.atos.kafka.web;

import net.atos.kafka.entity.Employee;
import net.atos.kafka.entity.PageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("kafka")
public class kafkaController {

    @Autowired
    @Qualifier("kafkaTempSim")
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    @Qualifier("kafkaTempEmp")
    private KafkaTemplate<String, Employee> kafkaTemplateEmp;
    @Autowired
    @Qualifier("kafkaTempPage")
    private KafkaTemplate<String, PageEvent> kafkaTemplatePage;

    @GetMapping("/publish/{message}")
    public ResponseEntity<Object> publishMessage(@PathVariable String message){
        kafkaTemplate.send("testTopicSp",""+ UUID.randomUUID().toString(),""+message);
        return new ResponseEntity<>(
                "Message sent successfully to testTopicSp",
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/publish/{topic}/{message}")
    public ResponseEntity<Object> publishMessageInTopic(@PathVariable String topic,
                                                        @PathVariable String message){
        kafkaTemplate.send(topic,""+UUID.randomUUID().toString(),""+message);
        return new ResponseEntity<>(
                "Message sent successfully to "+topic,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/publish/persons/{name}")
    public ResponseEntity<Employee> publishPerson(@PathVariable String name){
        Employee employee = new Employee(name,"HR",Math.random()*1000);
        kafkaTemplateEmp.send("testTopicSp",
                ""+UUID.randomUUID().toString(),
                employee
        );
        return new ResponseEntity<>(
                employee,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/page-data/{page}/{topic}")
    public ResponseEntity<Object> publishEventPage(@PathVariable String page, @PathVariable String topic){
        PageEvent pageEvent = new PageEvent(page, new Date(), new Random().nextInt(1000));
        kafkaTemplatePage.send(""+topic,
                ""+page,
                pageEvent
        );
        return new ResponseEntity<>(
                pageEvent,
                HttpStatus.ACCEPTED
        );
    }
}
