package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long chat_id;
    String notification_text;
    LocalDateTime dateTime;

    public NotificationTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chatId) {
        this.chat_id = chatId;
    }

    public String getNotification_text() {
        return notification_text;
    }

    public void setNotification_text(String notificationText) {
        this.notification_text = notificationText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chat_id, that.chat_id) && Objects.equals(notification_text, that.notification_text) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat_id, notification_text, dateTime);
    }

    @Override
    public String toString() {
        return "Notification_Task{" +
                "id=" + id +
                ", chatId=" + chat_id +
                ", notificationText='" + notification_text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
