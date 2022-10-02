package com.premukkoji.customer;

import com.premukkoji.clients.fraud.FraudCheckResponse;
import com.premukkoji.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private RestTemplate restTemplate;
    private FraudClient fraudClient;

    public CustomerService(CustomerRepository customerRepository,
                           RestTemplate restTemplate,
                           FraudClient fraudClient){
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
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
    }

}
