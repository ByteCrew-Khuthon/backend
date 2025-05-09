package kyonggiuniv.bytecrew.controller;

import kyonggiuniv.bytecrew.service.PigCoughService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class PigCoughController {

    private final PigCoughService pigCoughService;

    public PigCoughController(PigCoughService pigCoughService) {
        this.pigCoughService = pigCoughService;
    }

    @PostMapping("/cough")
    public ResponseEntity<Void> cough(PigBarnLocation location){
        pigCoughService.pigCoughed(location.location());
        return ResponseEntity.ok().build();
    }


    public record PigBarnLocation(String location){}
}
