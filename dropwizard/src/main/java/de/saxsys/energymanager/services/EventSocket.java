package de.saxsys.energymanager.services;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class EventSocket extends WebSocketAdapter {

  @Override
  public void onWebSocketConnect(Session sess) {
    super.onWebSocketConnect(sess);
    System.out.println("Client Socket Connected: " + sess);
  }

  @Override
  public void onWebSocketText(String message) {
    super.onWebSocketText(message);
    System.out.println("Client Received TEXT message: " + message);
  }

  @Override
  public void onWebSocketClose(int statusCode, String reason) {
    super.onWebSocketClose(statusCode, reason);
    System.out.println("Client Socket Closed: [" + statusCode + "] " + reason);
  }

  @Override
  public void onWebSocketError(Throwable cause) {
    super.onWebSocketError(cause);
    cause.printStackTrace(System.err);
  }
}
