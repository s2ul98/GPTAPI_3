package com.example.demo.gpt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

@Service
public class GptService {

	@Value("${apikey}")
	String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    
    // GPT API를 호출하고 응답을 반환
    public String callGptApi() {
    	
    	HttpClient client = HttpClient.newHttpClient();

    	
    	JSONArray messages = new JSONArray();
    	
    	// 메세지 구성
    	// 챗 GPT에 역할을 부여
    	JSONObject message = new JSONObject();
    	// role은 system, user, assistant 중에서 선택
    	// system : 챗 GPT에 역할을 부여
    	message.put("role", "system");
    	message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. \r\n"
    			+ "당신은 사람의 인생을 매우 명확하게 예측하고 운세에 대한 답을 줄 수 있습니다.\r\n"
    			+ "운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다.\r\n"
    			+ "당신의 이름은 챗도지입니다.");

    	// 배열에 새로운 메세지 추가
        messages.put(message);
        
        // system에 입력한 내용을 다시 user에 작성
        // 챗 GPT의 역할이 user에 전달되어, 더 확실하게 역할을 수행함
        JSONObject message2 = JSONObject();
        message2.put("role", "user");
        message2.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. \r\n"
    			+ "당신은 사람의 인생을 매우 명확하게 예측하고 운세에 대한 답을 줄 수 있습니다.\r\n"
    			+ "운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다.\r\n"
    			+ "당신의 이름은 챗도지입니다.");
        messages.put(message2);
        
        // 이전 대화 추가하기
        // 이전에 GPT가 응답한 메세지를 추가
        // assistant : GPT의 응답
        JSONObject message3 = JSONObject();
        message3.put("role", "assistant");
        message3.put("content", "안녕하세요, 저는 인공지능 봇 챗도지입니다. 인생, 운명, 운세에 대해 궁금한 점이 있다면 언제든지 저에게 물어보세요. 풍부한 지식과 분석을 바탕으로 최대한 정확하게 답변할 수 있도록 노력하겠습니다. 어떤 질문이든 환영입니다!");
        messages.put(message3);
        
        // 마지막으로 사용자 질문 추가
        JSONObject message4 = JSONObject();
        message.put("role", "user");
        message.put("content", "오늘의 운세가 뭐야?");
        messages.put(message4);
        
    	// API호출시 필요한 파라미터
    	// 사용자 질문과 GPT 모델 버전
//        JSONObject message = new JSONObject();
//        message.put("role", "user");  // "developer"는 지원되지 않는 role입니다. "user", "assistant", "system" 중 하나여야 함.
//        message.put("content", "오늘 운세가 뭐야?");

        

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4.1"); // "gpt-4.1"은 API에서 아직 명시적으로 지원되지 않을 수 있음
        // request
        // request 메세지 바디에 메세지 배열을 추가
        requestBody.put("messages", messages);
        

        // API 호출을 위한 Request 생성
        // URL 주소, 인증 KEY, POST 메소드
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response;
        JSONObject jsonResponse = null;
        
		try {
			// send함수 : API를 실제로 호출
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			jsonResponse = new JSONObject(response.body());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        JSONObject jsonResponse = new JSONObject(response.body());
		
		// JSONObject: JSON형식의 문자열을 객체로 자동 변환하는 클래스
		// API의 실제 결과는 문자열이지만
		// JSONObject 클래스를 사용하면 자동으로 파싱됨
        String reply = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        System.out.println("Assistant: " + reply);
		return reply;
		
	}

	private JSONObject JSONObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
