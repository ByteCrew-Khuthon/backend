package kyonggiuniv.bytecrew.controller;

import kyonggiuniv.bytecrew.service.PigManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/manage")
public class PigManageController {

    private final PigManageService pigManageService;

    public PigManageController(PigManageService pigManageService) {
        this.pigManageService = pigManageService;
    }

    @PostMapping("/temp")
    public ResponseEntity<Void> getBarnEnvironment(BarnEnvironment environment){
        pigManageService.saveBarnEnvironment();
        return ResponseEntity.ok(null);
    }

    public record BarnEnvironment(Double temp, Double humidity){}
}
