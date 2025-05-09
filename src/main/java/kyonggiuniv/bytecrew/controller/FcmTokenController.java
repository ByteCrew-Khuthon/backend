package kyonggiuniv.bytecrew.controller;

import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.dto.FcmTokenRequest;
import kyonggiuniv.bytecrew.dto.NotificationRequest;
import kyonggiuniv.bytecrew.entity.FcmToken;
import kyonggiuniv.bytecrew.repository.FcmTokenRepository;
import kyonggiuniv.bytecrew.service.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FcmTokenController {

    private final FcmTokenRepository fcmTokenRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    @Operation(description = "FCM Token 전달")
    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody FcmTokenRequest request) {
        String token = request.getFcmToken();

        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body("FCM 토큰이 비어있습니다.");
        }

        if (fcmTokenRepository.findByToken(token).isEmpty()) {
            FcmToken fcmToken = new FcmToken();
            fcmToken.setToken(token);
            fcmTokenRepository.save(fcmToken);
        }

        return ResponseEntity.ok("✅ 토큰 등록 완료");
    }

    @PostMapping("/notify-test")
    public ResponseEntity<Void> notifyTest() {
        String realToken ="dmGa7fw-ktuJKlz3Ywnjmb:APA91bFrxSvOlobBsVrplCbodRZxcQnbl1e7nP06E_XJqP_XJie8LgglUPWQLLHVI1CBe-PNrVrT4j-laZRjoxiR6UW2C49HNkjBC58AUFwqJoqOQLHbk";
        firebaseMessagingService.sendNotification(realToken,"test", "test");
        return ResponseEntity.ok(null);
    }
}
