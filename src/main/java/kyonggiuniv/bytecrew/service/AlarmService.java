package kyonggiuniv.bytecrew.service;

import kyonggiuniv.bytecrew.entity.Alarm;
import kyonggiuniv.bytecrew.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    // 6
    @Scheduled(cron = "0 0 6,12,18,21,22,23 * * *")  // 매일 오전 6시, 정오 12시, 오후 6시
    public void createScheduledAlarm() {
        Alarm alarm = new Alarm();
        Date date = new Date();
        alarm.setDate(date);
        alarm.setTitle("돼지 자동 먹이 알림");
        alarm.setDescription(date.getHours() + "시 " + date.getMinutes() + "분에 밥을 자동으로 공급했습니다.");
        alarm.setChecking(false);

        alarmRepository.save(alarm);
    }

    public void createImmediatelyAlarm() {
        Alarm alarm = new Alarm();
        Date date = new Date();
        alarm.setDate(date);
        alarm.setChecking(false);
        alarm.setTitle("돼지 자동 먹이 알림");
        alarm.setDescription(date.getHours() + "시 " + date.getMinutes() + "분에 밥을 자동으로 공급했습니다.");

        alarmRepository.save(alarm);
    }



    // 확인되지 않은 알람 중 최신 1개 조회
    public Optional<Alarm> getUncheckedLatestAlarm() {
        return alarmRepository.findTopByCheckingFalseOrderByDateDesc();
    }

    // 알람 확인 처리
    public void markAlarmAsChecked(Long id) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알람입니다."));
        alarm.setChecking(true);
        alarmRepository.save(alarm);
    }
}

