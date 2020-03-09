package com.growth.growth;

import com.growth.growth.model.UserDetails;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class UserDetailsApplicationTests {

	@Value("${spring.data.mongodb.database}")
	private String userBucketPath;

	@Autowired
	private MongoOperations mongoOperation=new MongoTemplate(new MongoClient("127.0.0.1"),"growth");

	@Test
	void contextLoads() {
		mongoOperation.save(UserDetails.builder().email("mano.adepu@gmail.com").username("manoharadepu").build());
	}

}
