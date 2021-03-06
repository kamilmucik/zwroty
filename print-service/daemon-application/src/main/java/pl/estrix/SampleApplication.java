/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.estrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * http://localhost:8099/path/
 * http://localhost:8099/path/hello
 */

@SpringBootApplication
@ComponentScan(basePackages = "pl.estrix")
//@PropertySource("classpath:filter.properties")
@PropertySource("file:c:\\\\sortowanie\\filter.properties")
//@EnableAutoConfiguration
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class SampleApplication {

	private static Logger LOG = LoggerFactory.getLogger(SampleApplication.class);

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) throws Exception  {
		SpringApplication.run(SampleApplication.class, args);
	}

}
