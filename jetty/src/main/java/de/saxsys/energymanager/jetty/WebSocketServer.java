package de.saxsys.energymanager.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WebSocketServer {

  public static void main(final String[] args) throws Exception {
    final Server server = new Server(8082);
    final WebSocketHandler webSocketHandler = new WebSocketHandler() {

      @Override
      public void configure(final WebSocketServletFactory factory) {
        factory.register(de.saxsys.energymanager.jetty.WebSocketHandler.class);
      }
    };
    server.setHandler(webSocketHandler);
    server.start();
    server.join();
  }
}
