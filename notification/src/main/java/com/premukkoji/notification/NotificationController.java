package com.premukkoji.notification;

import com.premukkoji.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/notification")
@Slf4j
public class NotificationController {

    private NotificationService notificationService;

    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(path = "/")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("New notification... {}", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
