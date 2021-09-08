//package pl.estrix.frontend;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
///**
// * Basic integration tests.
// *
// * @author Phillip Webb
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SpringBootFacesApplication.class)
//@WebAppConfiguration
//@IntegrationTest("server.port:0")
//@DirtiesContext
//public class SpringBootFacesApplicationTests {
//
//	@Value("${local.server.port}")
//	private int port;
//
//	@Value("${management.context-path:}")
//	private String actuatorPath;
//
//	@Test
//	public void testJsfWelcomePage() throws Exception {
//		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port, String.class);
//		assertEquals(HttpStatus.OK, entity.getStatusCode());
//		assertTrue("Wrong body:\n" + entity.getBody(), entity.getBody().contains("javax.faces.ViewState"));
//	}
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Test
//    public void testActuatorHealthPage() throws Exception {
//        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port + actuatorPath + "/health", Map.class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//
//        Map<String, Object> body = entity.getBody();
//        assertTrue("Wrong body:\n" + entity.getBody(), body.containsKey("status"));
//    }
//
//}