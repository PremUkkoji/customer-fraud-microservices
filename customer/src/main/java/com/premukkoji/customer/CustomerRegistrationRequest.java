package com.premukkoji.customer;

public record CustomerRegistrationRequest (
    String firstName,
    String lastName,
    String email){
}
