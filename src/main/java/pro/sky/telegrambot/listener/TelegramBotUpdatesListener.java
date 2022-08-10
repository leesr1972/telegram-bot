package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification_Task;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            if (messageText.equals("/start")) {
                SendMessage message1 = new SendMessage(chatId, "Hello!");
                SendResponse response = telegramBot.execute(message1);
            } else {
                Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                Matcher matcher = pattern.matcher(messageText);
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String item = matcher.group(3);
                    LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.
                            ofPattern("dd.MM.yyyy HH:mm"));
                    Notification_Task notificationTask = new Notification_Task();
                    notificationTask.setChat_id(chatId);
                    notificationTask.setNotification_text(item);
                    notificationTask.setDateTime(dateTime);
                    notificationService.addNotification(notificationTask);
                    SendMessage message2 = new SendMessage(chatId, "Ваша задача принята.");
                    SendResponse response = telegramBot.execute(message2);
                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void sendNotification() {
        List<Notification_Task> notificationTasks = notificationService.findNotificationsForSend();
        notificationTasks.forEach(notification_task -> {
            telegramBot.execute(new SendMessage(notification_task.getChat_id(),
                    notification_task.getDateTime().toString() + " " +
                            notification_task.getNotification_text()));
                });
        notificationTasks.forEach(notificationService::deleteNotification);
    }
}
