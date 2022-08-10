package pl.estrix.backend.user.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.estrix.spring.config.SpringBootFacesApplication;
import pl.estrix.spring.config.WebSecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootFacesApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
@Ignore
public class UserLoginServiceTest {

    private String PASSWORD = "test";

    @Autowired
    private PasswordEncoder standardPasswordEncoder;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
	public void passwordHashTest() throws Exception {
        ShaPasswordEncoder encoder=new ShaPasswordEncoder(256);
        encoder.setIterations(1024);
        String encodedPassword = encoder.encodePassword(PASSWORD,"");
//        System.out.println("encodedPassword: " + encodedPassword);

	}
}
