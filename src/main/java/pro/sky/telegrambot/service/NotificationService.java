package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.model.Notification_Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void addNotification(Notification_Task notificationTask) {
        notificationRepository.save(notificationTask);
    }

    public Notification_Task getNotification(Long id) {
        return notificationRepository.getById(id);
    }

    public void deleteNotification(Notification_Task notificationTask) {
        notificationRepository.delete(notificationTask);
    }

    public List<Notification_Task> findNotificationsForSend() {
        return notificationRepository.findNotificationTaskByDateTime(LocalDateTime.now().
                truncatedTo(ChronoUnit.MINUTES));
    }
}
