package kyonggiuniv.bytecrew.service;

import kyonggiuniv.bytecrew.controller.PigManageController;
import kyonggiuniv.bytecrew.entity.Barn;
import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import kyonggiuniv.bytecrew.entity.DiseaseRisk;
import kyonggiuniv.bytecrew.repository.BarnEnvironmentRepository;
import kyonggiuniv.bytecrew.repository.BarnRepository;
import kyonggiuniv.bytecrew.repository.DiseaseRiskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PigManageService {

    private final BarnRepository barnRepository;
    private final BarnEnvironmentRepository barnEnvironmentRepository;
    private final FirebaseMessagingService firebaseMessagingService;
    private final DiseaseRiskRepository diseaseRiskRepository;

    public PigManageService(BarnRepository barnRepository, BarnEnvironmentRepository barnEnvironmentRepository, FirebaseMessagingService firebaseMessagingService, DiseaseRiskRepository diseaseRiskRepository) {
        this.barnRepository = barnRepository;
        this.barnEnvironmentRepository = barnEnvironmentRepository;
        this.firebaseMessagingService = firebaseMessagingService;
        this.diseaseRiskRepository = diseaseRiskRepository;
    }


    public void saveBarnEnvironment(BarnEnvironment barnEnvironment){
        Barn barn = barnRepository.findById(barnEnvironment.getBarnId()).get();
        barnEnvironmentRepository.save(barnEnvironment);
        checkBarnEnvironment(barn, barnEnvironment);
    }

    private void checkBarnEnvironment(Barn barn, BarnEnvironment barnEnvironment) {
        if (barnEnvironment.getTemp() > barn.getWantedTemperature() + 2 || barnEnvironment.getTemp() < barn.getWantedTemperature() - 2) {
            //설정온도와 +- 2도 이상 차이나면 실행
            firebaseMessagingService.sendNotificationToAll(
                    "사육장 이상 온도 알림",
                    "사육장 온도가 현재 " + barnEnvironment.getTemp() + "도 입니다."
            );
        }
        ;
        if (barnEnvironment.getHumidity() > barn.getWantedHumidity() - 10 || barnEnvironment.getHumidity() < barn.getWantedHumidity() + 10) {
            //설정습도와 +- 10% 이상 차이나면 실행
            firebaseMessagingService.sendNotificationToAll(
                    "사육장 이상 습도 알림",
                    "사육장 습도가 현재 " + barnEnvironment.getHumidity() + "% 입니다."
            );
        }
    }

    public List<BarnEnvironment> getBarnEnvironments(){
        return barnEnvironmentRepository.findAll();
    }

    public List<DiseaseRisk> getDisease(){
        return diseaseRiskRepository.findAll();
    }

}
