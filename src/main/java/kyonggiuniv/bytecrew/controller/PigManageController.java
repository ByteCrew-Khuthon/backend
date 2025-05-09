package kyonggiuniv.bytecrew.controller;

import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import kyonggiuniv.bytecrew.service.PigManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> putBarnEnvironment(BarnEnvironment environment){
        pigManageService.saveBarnEnvironment(environment);
        return ResponseEntity.ok(null);
    }

}
