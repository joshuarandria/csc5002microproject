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
package vlibtour.vlibtour_lobby_room_proxy;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;

import vlibtour.vlibtour_lobby_room_api.InAMQPPartException;

public class TestScenario {

	@SuppressWarnings("unused")
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
	@Ignore
	@Test
	public void test() throws IOException, TimeoutException, InterruptedException, ExecutionException,
			InAMQPPartException, JsonRpcException {
	}

	@AfterClass
	public static void tearDown() throws InterruptedException, IOException {
		new ProcessBuilder("docker", "stop", "rabbitmq").inheritIO().start().waitFor();
		new ProcessBuilder("docker", "rm", "rabbitmq").inheritIO().start().waitFor();
	}
}
