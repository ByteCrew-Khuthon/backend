package kyonggiuniv.bytecrew.controller;

import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import kyonggiuniv.bytecrew.service.PigManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/manage")
public class PigManageController {

    private final PigManageService pigManageService;

    public PigManageController(PigManageService pigManageService) {
        this.pigManageService = pigManageService;
    }

    @GetMapping("/environment")
    public ResponseEntity<List<BarnEnvironment>> getBarnEnvironment(){
        return ResponseEntity.ok(pigManageService.getBarnEnvironments());
    }

    @PostMapping("/environment")
    public ResponseEntity<Void> putBarnEnvironment(BarnEnvironment environment){
        pigManageService.saveBarnEnvironment(environment);
        return ResponseEntity.ok(null);
    }

}
