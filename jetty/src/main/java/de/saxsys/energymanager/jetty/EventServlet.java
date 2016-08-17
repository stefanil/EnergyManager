package de.saxsys.energymanager.jetty;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServlet extends WebSocketServlet {

  private static final long serialVersionUID = -4883969824885757706L;

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.register(EventSocket.class);
  }
}
