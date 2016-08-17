package de.saxsys.energymanager.jetty;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class ServerSocket extends WebSocketAdapter {

  @Override
  public void onWebSocketConnect(Session sess) {
    super.onWebSocketConnect(sess);
    System.out.println("Server Socket Connected: " + sess);
  }

  @Override
  public void onWebSocketText(String message) {
    super.onWebSocketText(message);
    System.out.println("Server Received TEXT message: " + message);
  }

  @Override
  public void onWebSocketClose(int statusCode, String reason) {
    super.onWebSocketClose(statusCode, reason);
    System.out.println("Server Socket Closed: [" + statusCode + "] " + reason);
  }

  @Override
  public void onWebSocketError(Throwable cause) {
    super.onWebSocketError(cause);
    cause.printStackTrace(System.err);
  }
}
