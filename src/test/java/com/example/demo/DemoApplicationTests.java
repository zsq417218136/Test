package com.example.demo;

import com.drools.core.KieTemplate;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private KieTemplate kieTemplate;

	private KieSession kieSession;
	private Object object;



	@Test
	void contextLoads() throws InterruptedException {
		Thread.sleep(1000);
		this.kieSession=kieTemplate.getKieSession("rule.drl");
		object=12d;
		kieSession.insert(object);
		kieSession.fireAllRules();
	}



}
