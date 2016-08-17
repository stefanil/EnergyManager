package de.saxsys.energymanager.services;

import de.saxsys.energymanager.api.SolarPanel;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.Future;

import javax.inject.Singleton;

// todo implement web socket protocol as play does
// (see http://vlkan.com/blog/post/2014/08/07/testing-websockets-in-play/)
@Singleton
public class SolarPanelService {

  private static final Logger LOG = LoggerFactory.getLogger(SolarPanelService.class);

  private final WebSocketClient client;
  private Session session;

  public SolarPanelService() {
    client = new WebSocketClient();
    start();
  }

  public void start() {
    URI uri = URI.create("ws://localhost:8082/events/");

    try {
      client.start();
      final ClientSocket socket = new ClientSocket();
      final Future<Session> sessionFuture = client.connect(socket, uri);
      session = sessionFuture.get();
    } catch (final Throwable throwable) {
      LOG.error("Error while starting socket session.", throwable);
    }
  }

  public void send(final SolarPanel solarPanel) {
    try {
      final String solarPanelAsJson = new ObjectMapper().writeValueAsString(solarPanel);
      session.getRemote().sendString(solarPanelAsJson);
    } catch (final Throwable throwable) {
      LOG.error("Error while sending solarPanel on socket.", throwable);
    }
  }

  public void stop() {
    try {
      session.close();
      client.stop();
    } catch (final Throwable throwable) {
      LOG.error("Error while closing socket session.", throwable);
    }
  }
}
