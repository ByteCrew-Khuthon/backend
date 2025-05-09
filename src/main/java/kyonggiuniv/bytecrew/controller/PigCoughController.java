package kyonggiuniv.bytecrew.controller;

import io.swagger.v3.oas.annotations.Operation;
import kyonggiuniv.bytecrew.service.PigCoughService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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

    @Operation(description = "주변 ")
    @GetMapping("/")
    public ResponseEntity<List<DailyCoughCountDTO>> coughLogPerDaily(){
        return ResponseEntity.ok(pigCoughService.getCoughLogPerDaily());
    }

    public record PigBarnLocation(String location){}

    public record DailyCoughCountDTO(LocalDate date, long coughCount) {}
}