package kyonggiuniv.bytecrew.controller;

import kyonggiuniv.bytecrew.entity.Alarm;
import kyonggiuniv.bytecrew.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    // 가장 최근의 check = false 알람 조회
    @GetMapping("/latest")
    public ResponseEntity<Alarm> getLatestUncheckedAlarm() {
        return alarmService.getUncheckedLatestAlarm()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // 알람 확인 처리
    @PostMapping("/{id}/check")
    public ResponseEntity<Void> checkAlarm(@PathVariable long id) {
        alarmService.markAlarmAsChecked(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/immediate")
    public ResponseEntity<String> createImmediateAlarm() {
        alarmService.createImmediatelyAlarm();
        return ResponseEntity.ok("알람이 즉시 생성되었습니다.");
    }

}
