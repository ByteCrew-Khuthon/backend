package kyonggiuniv.bytecrew.service;


import kyonggiuniv.bytecrew.entity.Barn;
import kyonggiuniv.bytecrew.entity.PigCough;
import kyonggiuniv.bytecrew.repository.BarnRepository;
import kyonggiuniv.bytecrew.repository.PigCoughRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PigCoughService {
    private final PigCoughRepository pigCoughRepository;
    private final BarnRepository barnRepository;
    private final long riskCoughTime = 3 * 60 * 60 * 1000;
    private final long riskCoughCount = 10;

    public PigCoughService(PigCoughRepository pigCoughRepository, BarnRepository barnRepository) {
        this.pigCoughRepository = pigCoughRepository;
        this.barnRepository = barnRepository;
    }

    public void pigCoughed(String location){
        Barn barn = barnRepository.findByLocation(location);
        pigCoughRepository.save(new PigCough(barn.getId()));
    }

    public boolean haveCoughRisk(Barn barn){
        Date date = new Date(new Date().getTime() - riskCoughTime);
        Long numOfCough = pigCoughRepository.countByBarnIdAndCreatedAtAfter(barn.getId(), date);
        if(numOfCough >= riskCoughCount){
            return true;
        }
        return false;
    }

}
