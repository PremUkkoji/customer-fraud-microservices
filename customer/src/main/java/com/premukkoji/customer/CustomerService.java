package com.premukkoji.customer;

import com.premukkoji.amqp.RabbitMQMessageProducer;
import com.premukkoji.clients.fraud.FraudCheckResponse;
import com.premukkoji.clients.fraud.FraudClient;
import com.premukkoji.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private RestTemplate restTemplate;
    private FraudClient fraudClient;
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    public CustomerService(CustomerRepository customerRepository,
                           RestTemplate restTemplate,
                           FraudClient fraudClient,
                           RabbitMQMessageProducer rabbitMQMessageProducer){
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        this.customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to premukkoji microservices...",
                        customer.getFirstName() + " " + customer.getLastName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");
    }

}
