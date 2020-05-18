
/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.soa.soap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import uet.soa.soap.GetSumRequest;
import uet.soa.soap.GetSumResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalculatorWebServiceTests {

	@LocalServerPort
	private int port;

	@Test
	public void testSum() throws Exception {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(ClassUtils.getPackageName(GetSumRequest.class));
		marshaller.afterPropertiesSet();
		WebServiceTemplate ws = new WebServiceTemplate(marshaller);
		GetSumRequest request = new GetSumRequest();
		request.setFirstNumber(1);
		request.setSecondNumber(2);
		GetSumResponse response = (GetSumResponse) ws.marshalSendAndReceive("http://localhost:"+ port + "/ws", request);
		System.out.println("Result is: " + response.getResult());
		assertThat(response.getResult() == 3);
    }

}