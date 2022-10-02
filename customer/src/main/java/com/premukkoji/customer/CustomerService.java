package com.premukkoji.customer;

import com.premukkoji.clients.fraud.FraudCheckResponse;
import com.premukkoji.clients.fraud.FraudClient;
import com.premukkoji.clients.notification.NotificationClient;
import com.premukkoji.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private RestTemplate restTemplate;
    private FraudClient fraudClient;
    private NotificationClient notificationClient;

    public CustomerService(CustomerRepository customerRepository,
                           RestTemplate restTemplate,
                           FraudClient fraudClient,
                           NotificationClient notificationClient){
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
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

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to premukkoji microservices...",
                                customer.getFirstName() + " " + customer.getLastName())
                )
        );
    }

}
