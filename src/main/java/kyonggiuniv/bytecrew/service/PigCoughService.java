package kyonggiuniv.bytecrew.service;


import kyonggiuniv.bytecrew.controller.PigCoughController;
import kyonggiuniv.bytecrew.entity.Barn;
import kyonggiuniv.bytecrew.entity.DiseaseRisk;
import kyonggiuniv.bytecrew.entity.PigCough;
import kyonggiuniv.bytecrew.repository.BarnRepository;
import kyonggiuniv.bytecrew.repository.DiseaseRiskRepository;
import kyonggiuniv.bytecrew.repository.PigCoughRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PigCoughService {
    private final PigCoughRepository pigCoughRepository;
    private final BarnRepository barnRepository;
    private final long riskCoughTime = 3 * 60 * 60 * 1000;
    private final long riskCoughCount = 10;
    private final FirebaseMessagingService firebaseMessagingService;
    private final DiseaseRiskRepository diseaseRiskRepository;
    private final AlarmService alarmService;

    public PigCoughService(PigCoughRepository pigCoughRepository, BarnRepository barnRepository, FirebaseMessagingService firebaseMessagingService, DiseaseRiskRepository diseaseRiskRepository, AlarmService alarmService) {
        this.pigCoughRepository = pigCoughRepository;
        this.barnRepository = barnRepository;
        this.firebaseMessagingService = firebaseMessagingService;
        this.diseaseRiskRepository = diseaseRiskRepository;
        this.alarmService = alarmService;
    }

    public void pigCoughed(String location){
        Barn barn = barnRepository.findByLocation(location);
        pigCoughRepository.save(new PigCough(barn.getId()));
        checkCoughRisk(barn);
    }

    public void checkCoughRisk(Barn barn){
        Date date = new Date(new Date().getTime() - riskCoughTime);
        Long numOfCough = pigCoughRepository.countByBarnIdAndCreatedAtAfter(barn.getId(), date);
        if(numOfCough >= riskCoughCount){
            firebaseMessagingService.sendNotificationToAll(
                    "돼지 기침 이상 알림",
                    barn.getLocation()+" 농가에서 돼지 기침 이상 치수를 넘어섰습니다."
                    );
            DiseaseRisk diseaseRisk = new DiseaseRisk();
            diseaseRisk.setLocation(barn.getLocation());
            diseaseRisk.setLatitude(barn.getLatitude());
            diseaseRisk.setLongitude(barn.getLongitude());
            diseaseRisk.setName("돼지 기침 위험 수치 발생");
            diseaseRiskRepository.save(diseaseRisk);
            alarmService.createAlarm("돼지 기침 위험 수치 발생", barn.getLocation()+" 농가에서 돼지 기침 이상 치수를 넘어섰습니다.");
        }
    }

    public List<PigCoughController.DailyCoughCountDTO> getCoughLogPerDaily(){
        return groupCoughsByDate(pigCoughRepository.findAll());
    }

    private List<PigCoughController.DailyCoughCountDTO> groupCoughsByDate(List<PigCough> coughs) {
        Map<LocalDate, Long> grouped = coughs.stream()
                .collect(Collectors.groupingBy(
                        cough -> convertToLocalDate(cough.getCreatedAt()),
                        Collectors.counting()
                ));

        return grouped.entrySet().stream()
                .map(entry -> new PigCoughController.DailyCoughCountDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(PigCoughController.DailyCoughCountDTO::date))
                .collect(Collectors.toList());
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
