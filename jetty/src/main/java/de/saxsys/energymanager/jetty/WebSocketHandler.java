package de.saxsys.energymanager.jetty;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

  @OnWebSocketClose
  public void onClose(final int statusCode, final String reason) {
    System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
  }

  @OnWebSocketError
  public void onError(Throwable t) {
    System.out.println("Error: " + t.getMessage());
  }

  @OnWebSocketConnect
  public void onConnect(final Session session) {
    System.out.println("Connect: " + session.getRemoteAddress().getAddress());
    try {
      session.getRemote().sendString("Hello Webbrowser");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @OnWebSocketMessage
  public void onMessage(final String message) {
    System.out.println("Message: " + message);
  }
}
