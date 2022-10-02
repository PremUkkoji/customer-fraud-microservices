package com.premukkoji.notification;

import com.premukkoji.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.getToCustomerId())
                        .toCustomerEmail((notificationRequest.getToCustomerEmail()))
                        .sender("premukkoji")
                        .message(notificationRequest.getMessage())
                        .sentAt(LocalDateTime .now())
                        .build()
        );
    }
}
