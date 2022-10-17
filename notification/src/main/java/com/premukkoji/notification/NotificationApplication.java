package com.premukkoji.notification;

import com.premukkoji.amqp.RabbitMQMessageProducer;
import com.rabbitmq.client.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.premukkoji.notification",
                "com.premukkoji.amqp"
        }
)
@EnableEurekaClient
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(
//            RabbitMQMessageProducer rabbitMQMessageProducer,
//            NotificationConfiguration notificationConfiguration
//    ) {
//        return args -> {
//            rabbitMQMessageProducer.publish(
//                    "foo",
//                    notificationConfiguration.getInternalExchange(),
//                    notificationConfiguration.getInternalNotificationRoutingKey()
//            );
//        };
//    }
}