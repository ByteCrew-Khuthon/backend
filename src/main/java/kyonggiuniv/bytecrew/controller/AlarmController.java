package kyonggiuniv.bytecrew.controller;

import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.entity.Alarm;
import kyonggiuniv.bytecrew.service.AlarmService;
import kyonggiuniv.bytecrew.service.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final FirebaseMessagingService firebaseMessagingService;

    // 가장 최근의 check = false 알람 조회
    @Operation(description = "아직 확인 안한 알람 받기")
    @GetMapping("/latest")
    public ResponseEntity<Alarm> getLatestUncheckedAlarm() {
        return alarmService.getUncheckedLatestAlarm()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // 알람 확인 처리
    @Operation(description = "알람 확인 처리")
    @PostMapping("/{id}/check")
    public ResponseEntity<Void> checkAlarm(@PathVariable long id) {
        alarmService.markAlarmAsChecked(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "알람 생성 API. 발표용임")
    @PostMapping("/immediate")
    public ResponseEntity<String> createImmediateAlarm() {
        Alarm alarm =alarmService.createImmediatelyAlarm();
        String realToken ="dH00fniqC-4KSPAkJErh7U:APA91bFgR_-MhCW5s6-qJ575DEPeFnQ4VFv_k0Ydps0ViEd1h-0o7aMszJ7ngrDG9TGe61sZR9ddeSiV2nFiuR13TqE7GqdKFJZGnM5-hQZ43fiDyA2c2g0";
        firebaseMessagingService.sendNotification(realToken,alarm.getTitle(), alarm.getDescription());
        return ResponseEntity.ok("알람이 즉시 생성되었습니다.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Alarm>> getAllAlarms() {
        return ResponseEntity.ok(alarmService.getAllAlarmsOrderByDate());
    }

}
