package kyonggiuniv.bytecrew.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiuniv.bytecrew.controller.PigManageController;
import kyonggiuniv.bytecrew.entity.Barn;
import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import kyonggiuniv.bytecrew.entity.DiseaseRisk;
import kyonggiuniv.bytecrew.repository.BarnEnvironmentRepository;
import kyonggiuniv.bytecrew.repository.BarnRepository;
import kyonggiuniv.bytecrew.repository.DiseaseRiskRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PigManageService {

    private final BarnRepository barnRepository;
    private final BarnEnvironmentRepository barnEnvironmentRepository;
    private final FirebaseMessagingService firebaseMessagingService;
    private final DiseaseRiskRepository diseaseRiskRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ChatClient chatClient;
    private final PigCoughService pigCoughService;
    private final AlarmService alarmService;

    @Autowired
    public PigManageService(BarnRepository barnRepository, BarnEnvironmentRepository barnEnvironmentRepository, FirebaseMessagingService firebaseMessagingService, DiseaseRiskRepository diseaseRiskRepository, ChatModel chatModel, PigCoughService pigCoughService, AlarmService alarmService) {
        this.barnRepository = barnRepository;
        this.barnEnvironmentRepository = barnEnvironmentRepository;
        this.firebaseMessagingService = firebaseMessagingService;
        this.diseaseRiskRepository = diseaseRiskRepository;
        this.chatClient = ChatClient
                .builder(chatModel)
                .build();
        this.pigCoughService = pigCoughService;
        this.alarmService = alarmService;
    }


    public void saveBarnEnvironment(BarnEnvironment barnEnvironment){
        Barn barn = barnRepository.findById(barnEnvironment.getBarnId().longValue());
        barnEnvironmentRepository.save(barnEnvironment);
        checkBarnEnvironment(barn, barnEnvironment);
    }

    private void checkBarnEnvironment(Barn barn, BarnEnvironment barnEnvironment) {
        if (barnEnvironment.getTemp() > barn.getWantedTemperature() + 2 || barnEnvironment.getTemp() < barn.getWantedTemperature() - 2) {
            //설정온도와 +- 2도 이상 차이나면 실행
            firebaseMessagingService.sendNotificationToAll(
                    "사육장 이상 온도 알림",
                    "사육장 온도가 현재 " + barnEnvironment.getTemp() + "도 입니다."
            );
            alarmService.createAlarm("사육장 이상 온도 알림", "사육장 온도가 현재 " + barnEnvironment.getTemp() + "도 입니다.");
        }
        ;
        if (barnEnvironment.getHumidity() > barn.getWantedHumidity() - 10 || barnEnvironment.getHumidity() < barn.getWantedHumidity() + 10) {
            //설정습도와 +- 10% 이상 차이나면 실행
            firebaseMessagingService.sendNotificationToAll(
                    "사육장 이상 습도 알림",
                    "사육장 습도가 현재 " + barnEnvironment.getHumidity() + "% 입니다."
            );
            alarmService.createAlarm("사육장 이상 습도 알림", "사육장 습도가 현재 " + barnEnvironment.getHumidity() + "% 입니다.");
        }
    }

    public List<BarnEnvironment> getBarnEnvironments(){
        return barnEnvironmentRepository.findAll();
    }

    public List<DiseaseRisk> getDisease() throws JsonProcessingException {
        String url = "http://211.237.50.150:7080/openapi/3eb0feb49f52e6ae704a852010525c2e3f42ba7465a10da8f5e28ee53a337be5/json/Grid_20151204000000000316_1/44201/45200";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode rows = root
                .path("Grid_20151204000000000316_1")
                .path("row");

        List<DiseaseRisk> resultList = new ArrayList<>();

        for (JsonNode node : rows) {
            if(node.path("LVSTCKSPC_NM").asText().contains("돼지")){
                DiseaseRisk item = new DiseaseRisk();
                String location = node.path("FARM_LOCPLC").asText();
                Coordinates coordinates = getCoordinates(location);

                item.setName(node.path("LKNTS_NM").asText());
                item.setLocation(location);
                item.setLatitude(coordinates.latitude);
                item.setLongitude(coordinates.longitude);
                resultList.add(item);
            }
        }
        resultList.addAll(diseaseRiskRepository.findAll());
        return resultList;
    }

    public Coordinates getCoordinates(String address){ //코드 별로
        String kakaoApiKey = "028226f3dce00ae1f0d3a4513ceffc0b";
        String kakaoCoordinatesUrl = "https://dapi.kakao.com/v2/local/search/address.json?query={query}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+kakaoApiKey);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("query", address);


        HttpEntity<String> entity = new HttpEntity<>(headers);
        JsonNode response = restTemplate.exchange(
                kakaoCoordinatesUrl,
                HttpMethod.GET,
                entity,
                JsonNode.class,
                uriVariables
        ).getBody();

        double latitude = response.get("documents").get(0).get("x").asDouble();
        double longitude = response.get("documents").get(0).get("y").asDouble();

        return new Coordinates(latitude, longitude);
    }

    public String getEvaluation(){
        Barn barn = barnRepository.findById(1L);
        StringBuilder sb = new StringBuilder();
        sb.append("현재 돼지농장 농장주는 이런 농장을 운영하고 있어.\n");
        sb.append(barn.getDescription());
        sb.append("\n");
        sb.append("그리고 현재 농장의 돼지 기침 데이터는 이렇게 되어있어.(데이터 형식은 날짜+기침횟수야.)\n");
        sb.append(pigCoughService.getCoughLogPerDaily().toString());
        sb.append("\n");
        sb.append("마지막으로 현재 농장의 온습도 데이터는 다음과 같아.\n");
        sb.append(getBarnEnvironments().toString());
        return chatClient.prompt().user(sb.toString()).call().content();
    }

    public record Coordinates(double latitude, double longitude){}

}
