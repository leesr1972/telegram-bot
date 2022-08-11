package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void addNotification(NotificationTask notificationTask) {
        notificationRepository.save(notificationTask);
    }

    public NotificationTask getNotification(Long id) {
        return notificationRepository.getById(id);
    }

    public void deleteNotification(NotificationTask notificationTask) {
        notificationRepository.delete(notificationTask);
    }

    public List<NotificationTask> findNotificationsForSend() {
        return notificationRepository.findNotificationTaskByDateTime(LocalDateTime.now().
                truncatedTo(ChronoUnit.MINUTES));
    }
}
