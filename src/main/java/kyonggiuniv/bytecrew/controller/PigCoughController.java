package kyonggiuniv.bytecrew.controller;

import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.service.PigCoughService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/cough")
public class PigCoughController {

    private final PigCoughService pigCoughService;

    public PigCoughController(PigCoughService pigCoughService) {
        this.pigCoughService = pigCoughService;
    }

    @Operation(description = "기침했을때 전달하는곳")
    @PostMapping("/")
    public ResponseEntity<Void> cough(PigBarnLocation location){
        pigCoughService.pigCoughed(location.location());
        return ResponseEntity.ok().build();
    }


    public record PigBarnLocation(String location){}
}