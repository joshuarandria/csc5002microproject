/**
This file is part of the course CSC5002.

Copyright (C) 2017-2020 Télécom SudParis

This is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This software platform is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the muDEBS platform. If not, see <http://www.gnu.org/licenses/>.

Initial developer(s): Denis Conan
Contributor(s):
 */
// CHECKSTYLE:OFF
package vlibtour.vlibtour_group_communication_proxy;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.http.client.Client;

import vlibtour.vlibtour_lobby_room_api.InAMQPPartException;

public class TestScenario {

	private static Client c;

	@BeforeClass
	public static void setUp() throws IOException, InterruptedException, URISyntaxException {
		Runtime r = Runtime.getRuntime();
		Process wc = r.exec("netstat -nlp | grep \"[[:space:]]5672\"");
		wc.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(wc.getInputStream()));
		String line = "";
		while ((line = b.readLine()) != null) {
			if (line.contains(":5672")) {
				System.out.println("Warning: Already a RabbitMQ server on address :::5672 =>  stop it");
				new ProcessBuilder("docker", "stop", "rabbitmq").inheritIO().start().waitFor();
				new ProcessBuilder("docker", "rm", "rabbitmq").inheritIO().start().waitFor();
			}
		}
		Thread.sleep(1000);
		new ProcessBuilder("docker", "run", "-itd", "--name", "rabbitmq", "-p", "5672:5672", "-p", "15672:15672",
				"rabbitmq:3.10-management").inheritIO().start().waitFor();
		Thread.sleep(10000);
		c = new Client("http://127.0.0.1:15672/api/", "guest", "guest");
		Thread.sleep(1000);
	}

	// Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	@Test
	public void test()
			throws IOException, TimeoutException, InterruptedException, ExecutionException, InAMQPPartException {
		System.out.println();
		System.out.println("______________ TestScenario  ______________");
		System.out.println();

		new ProcessBuilder("docker", "exec", "rabbitmq", "rabbitmqctl", "list_queues").inheritIO().start().waitFor();

		// VLibTourGroupCommunicationSystemProxy myProxy1 = new
		// VLibTourGroupCommunicationSystemProxy("tour1", "group0",
		// "user1","password","vhost");
		VLibTourGroupCommunicationSystemProxy myProxy1 = new VLibTourGroupCommunicationSystemProxy("tour1", "group0",
				"user1");
		VLibTourGroupCommunicationSystemProxy myProxy2 = new VLibTourGroupCommunicationSystemProxy("tour1", "group0",
				"user2");
		VLibTourGroupCommunicationSystemProxy myProxy3 = new VLibTourGroupCommunicationSystemProxy("tour1", "group0",
				"user3");

		Assert.assertNotNull(c.getExchanges().stream()

				.filter(q -> q.getName().equals(VLibTourGroupCommunicationSystemProxy.EXCHANGE_NAME)));

		Consumer consumer1 = new DefaultConsumer(myProxy1.getChannel()) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				String message = new String(body, StandardCharsets.UTF_8);
				System.out.println(" [1] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		Consumer consumer2 = new DefaultConsumer(myProxy2.getChannel()) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				String message = new String(body, StandardCharsets.UTF_8);
				System.out.println(" [2] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		Consumer consumer3 = new DefaultConsumer(myProxy3.getChannel()) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				String message = new String(body, StandardCharsets.UTF_8);
				System.out.println(" [3] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		myProxy1.setConsumer(consumer1);
		myProxy2.setConsumer(consumer2);
		myProxy3.setConsumer(consumer3);

		myProxy1.publish("*.all.#", "helloworld");
		myProxy2.publish("*.tour1_group0_user1.Position", "Position(myProxy2.user)");
		myProxy2.publish("*.all.#", "only user2");
		myProxy1.publish("#", "#");
		myProxy3.publish("*.tour1_group0_user1.*", "from 3 to user1");

		myProxy1.startConsumption();
		myProxy2.startConsumption();
		myProxy3.startConsumption();

		myProxy1.close();
		myProxy2.close();
		myProxy3.close();

		System.out.println();
		System.out.println("______________ TestScenario  ______________");
		System.out.println();
		new ProcessBuilder("docker", "exec", "rabbitmq", "rabbitmqctl", "list_queues").inheritIO().start().waitFor();
	}

	@AfterClass
	public static void tearDown() throws InterruptedException, IOException {
		new ProcessBuilder("docker", "stop", "rabbitmq").inheritIO().start().waitFor();
		new ProcessBuilder("docker", "rm", "rabbitmq").inheritIO().start().waitFor();
	}
}
