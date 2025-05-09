package kyonggiuniv.bytecrew.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kyonggiuniv.bytecrew.entity.FcmToken;
import kyonggiuniv.bytecrew.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseMessagingService {

    private final FcmTokenRepository fcmTokenRepository;

    public String sendNotification(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ FCM 메시지 전송 성공: {}", response);
            return response;
        } catch (FirebaseMessagingException e) {
            log.error("❌ 전송 실패 [{}]: {}", e.getMessagingErrorCode(), token);
            switch (e.getMessagingErrorCode()) {
                case INVALID_ARGUMENT:
                case UNREGISTERED:
                    return e.getMessagingErrorCode().toString();
                default:
                    throw new RuntimeException("FCM 메시지 전송 중 오류", e);
            }
        }
    }

    public int sendNotificationToAll(String title, String body) {
        List<FcmToken> tokens = fcmTokenRepository.findAll();
        int success = 0;

        for (FcmToken token : tokens) {
            try {
                sendNotification(token.getToken(), title, body);
                success++;
            } catch (Exception e) {
                log.warn("❌ 알림 실패: {}", token.getToken());
            }
        }
        return success;
    }
}
