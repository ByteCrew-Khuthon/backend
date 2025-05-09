package kyonggiuniv.bytecrew.service;

import kyonggiuniv.bytecrew.repository.BarnRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PigManageService {

    private final BarnRepository barnRepository;

    public PigManageService(BarnRepository barnRepository) {
        this.barnRepository = barnRepository;
    }

    @Scheduled(fixedRate = 6 * 60 * 1000)
    public void putFeed(){
        //
    }

    public void saveBarnEnvironment(){
        return;
    }


}
