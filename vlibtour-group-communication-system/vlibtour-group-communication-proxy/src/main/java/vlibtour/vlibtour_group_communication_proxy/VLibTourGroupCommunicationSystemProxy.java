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
package vlibtour.vlibtour_group_communication_proxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import vlibtour.vlibtour_lobby_room_api.VLibTourLobbyService;

/**
 * The AMQP/RabbitMQ Proxy (for clients) of the VLibTour Group Communication
 * System.
 * 
 * @author Denis Conan
 */
public class VLibTourGroupCommunicationSystemProxy {
	/**
	 * the name of the exchange.
	 */
	public static final String EXCHANGE_NAME = "topic_logs";
	/**
	 * the connection to the RabbitMQ broker.
	 */
	private Connection connection;
	/**
	 * the channel for consuming.
	 */
	private Channel channel;
	/**
	 * the collection of binding keys for producing.
	 */
	private String[] bindingKeys;
	/**
	 * the message to produce.
	 */
	private String message;
	/**
	 * the consumer thread.
	 */
	private Consumer consumer;
	/**
	 * the name of the queue.
	 */
	private String queueName;

	/**
	 * Constructor 1: setHost
	 */
	public VLibTourGroupCommunicationSystemProxy(final String tour, final String group, final String user)
			throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		queueName = channel.queueDeclare().getQueue();
		// System.out.println("queuname = " + queueName);
		bindingKeys = new String[] { "*.all.#", "*." + tour + "_" + group + "_" + user + ".#" };
		for (String bindingKey : bindingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		}
	}

	/**
	 * Constructor 2: setURI
	 * 
	 * @throws URISyntaxException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public VLibTourGroupCommunicationSystemProxy(final String Url)
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(Url);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		queueName = channel.queueDeclare().getQueue();
		// System.out.println("queuname = " + queueName);
		String userId = factory.getUsername();
		String password = factory.getPassword();
		String tourId = factory.getHost().split(VLibTourLobbyService.GROUP_TOUR_USER_DELIMITER)[0];
		String groupId = tourId + VLibTourLobbyService.GROUP_TOUR_USER_DELIMITER + userId;
		bindingKeys = new String[] { "*.all.#", "*." + tourId + "_" + groupId + "_" + userId + ".#" };
		for (String bindingKey : bindingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		}

	}

	/**
	 * publishes a message.
	 * 
	 * @throws IOException communication problem with the broker.
	 */
	public void publish(final String routingKey, final String msg) throws IOException {
		this.message = msg;
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
		// System.out.println(" [" + this.channel + "] Sent to'" + routingKey + "':'" + msg + "'");
	}

	/**
	 * constructs a consumer that will receive log messages from several binding
	 * keys.
	 * <p>
	 * The method
	 * {@link DefaultConsumer#handleDelivery(String, Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])}
	 * is called when consuming.
	 * 
	 * @throws IOException      the communication problems.
	 * @throws TimeoutException broker to long to connect to.
	 */
	public void setConsumer(Consumer consumer) throws IOException, TimeoutException {
		// System.out.println("consumer: " + consumer);
		this.consumer = consumer;
	}

	/**
	 * starts consuming and consumes—i.e. calls
	 * {@link DefaultConsumer#handleDelivery(String, Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])}.
	 * 
	 * @return the AMQP response.
	 * @throws IOException communication problem with the broker.
	 */
	public String startConsumption() throws IOException {
		// System.out.println("startConsumption in: " + queueName);
		return channel.basicConsume(queueName, true, consumer);
	}

	/**
	 * closes the channel and the connection with the broker.
	 * 
	 * @throws IOException      communication problem.
	 * @throws TimeoutException broker to long to communicate with.
	 */
	public void close() throws IOException, TimeoutException {
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	public Channel getChannel() {
		return channel;
	}
}
