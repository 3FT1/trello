package com.example.trello.notification;

import com.example.trello.workspace.Workspace;
import com.slack.api.Slack;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Value("${SLACK_URL}")
    private String slackUrl;

    @Transactional
    public void sendSlack(NotificationType notificationType ,Workspace workspace) {
        Slack slack =Slack.getInstance();
        String message = NotificationType.createMessage(notificationType);
        try {
            slack.send(slackUrl,message);
            Notification notification = Notification.builder()
                    .content(notificationType.getMessage())
                    .notificationType(notificationType)
                    .workspace(workspace)
                    .build();
            notificationRepository.save(notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }






}
