package com.example.demo.gpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.gpt.service.GptService;

// GPTService의 기능을 확인하는 테스트 클래스

@SpringBootTest
public class GptServiceTest {
	
	@Autowired
	GptService service;
	
	@Test
	void API호출테스트() {
		
		String res = service.callGptApi();
		
		System.out.println(res);
	}

}
