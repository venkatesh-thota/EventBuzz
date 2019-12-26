package com.stackroute;

import com.stackroute.service.EntityRecognition;
import com.stackroute.service.EntityRecognitionImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void findEntityDomainTest() throws IOException {
//		EntityRecognitionImp entityRecognition = new EntityRecognitionImp();
//		entityRecognition.findEntityDomain(null);
//
//	}
}
