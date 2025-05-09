package kyonggiuniv.bytecrew.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import kyonggiuniv.bytecrew.entity.DiseaseRisk;
import kyonggiuniv.bytecrew.service.PigManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/manage")
public class PigManageController {

    private final PigManageService pigManageService;

    public PigManageController(PigManageService pigManageService) {
        this.pigManageService = pigManageService;
    }

    @Operation(description = "사육장 환경(온도, 습도 등) 리스트 받기")
    @GetMapping("/environment")
    public ResponseEntity<List<BarnEnvironment>> getBarnEnvironment(){
        return ResponseEntity.ok(pigManageService.getBarnEnvironments());
    }

    @Operation(description = "사육장 환경(온도, 습도 등) 정보 넣기")
    @PostMapping("/environment")
    public ResponseEntity<Void> putBarnEnvironment(@RequestBody BarnEnvironment environment){
        pigManageService.saveBarnEnvironment(environment);
        return ResponseEntity.ok().build();
    }


    @Operation(description = "전국 가축 전염 질병 데이터 가져오기")
    @GetMapping("/disease")
    public ResponseEntity<List<DiseaseRisk>> getDisease() throws JsonProcessingException {
        return ResponseEntity.ok(pigManageService.getDisease());
    }

    @Operation(description = "농장 종합 평가 받기")
    @GetMapping("/evaluation")
    public ResponseEntity<String> getEvaluation(){
        return ResponseEntity.ok(pigManageService.getEvaluation());
    }

}
