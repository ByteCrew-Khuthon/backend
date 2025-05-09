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
    public ResponseEntity<Void> cough(){
        pigCoughService.pigCoughed("경기도 평택시 오성면 양교길 267");
        return ResponseEntity.ok().build();
    }

    @Operation(description = "일자별 기침 횟수 기록 반환")
    @GetMapping("/")
    public ResponseEntity<List<DailyCoughCountDTO>> coughLogPerDaily(){
        return ResponseEntity.ok(pigCoughService.getCoughLogPerDaily());
    }

    public record DailyCoughCountDTO(LocalDate date, long coughCount) {}
}