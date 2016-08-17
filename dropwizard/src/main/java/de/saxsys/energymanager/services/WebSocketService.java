package de.saxsys.energymanager.services;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;

public class WebSocketService {

  public static void main(String[] args) throws Exception {
    URI uri = URI.create("ws://localhost:8082/events/");

    WebSocketClient client = new WebSocketClient();
    try {
      try {
        client.start();
        // The socket that receives events
        EventSocket socket = new EventSocket();
        // Attempt Connect
        Future<Session> fut = client.connect(socket, uri);
        // Wait for Connect
        Session session = fut.get();
        // Send a message
        session.getRemote().sendString("Hello");
        // Close session
        session.close();
      }
      finally {
        client.stop();
      }
    } catch (Throwable t) {
      t.printStackTrace(System.err);
    }
  }
}
