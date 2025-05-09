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

    // 일정 주기마다 알람 자동 생성 (예: 1분마다) 60초
    @Scheduled(fixedRate = 60000)
    public void createScheduledAlarm() {
        Alarm alarm = new Alarm();
        Date date = new Date();
        alarm.setDate(date);
        alarm.setTitle("돼지 자동 먹이 알림");
        alarm.setDescription(String.valueOf(date.getHours())+"시 "+String.valueOf(date.getMinutes())+"분에 밥을 자동으로 공급했습니다.");
        alarm.setChecking(false);

        alarmRepository.save(alarm);
    }

    // 확인되지 않은 알람 중 최신 1개 조회
    public Optional<Alarm> getUncheckedLatestAlarm() {
        return alarmRepository.findTopByCheckingFalseOrderByDateDesc();
    }

    // 알람 확인 처리
    public void markAlarmAsChecked(int id) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알람입니다."));
        alarm.setChecking(true);
        alarmRepository.save(alarm);
    }
}

