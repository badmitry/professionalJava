import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server {
  private List<ClientHandler> clients;
  private AuthService authService;
  public ExecutorService executorService;
  private Logger logger;
  private Handler fileHandler;

  public Server() {
    logger = Logger.getLogger("");
    try {
      fileHandler = new FileHandler("server/src/main/resources/serverLog%g.log", 1*1024,5, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    fileHandler.setFormatter(new SimpleFormatter());
    fileHandler.setLevel(Level.ALL);
    logger.addHandler(fileHandler);
    clients = new Vector<>();
    authService = new SimpleAuthService();
    ServerSocket serverSocket = null;
    Socket socket;
    final int PORT = 10000;

    try {
      serverSocket = new ServerSocket(PORT);
      logger.log(Level.INFO, "Сервер запущен");
      executorService = Executors.newCachedThreadPool();
      while (true) {
        socket = serverSocket.accept();
        logger.log(Level.INFO,"Клиент подключился");
        new ClientHandler(this, socket);

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
      try {
        serverSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public void broadcastMsg(String msg) {
    for (ClientHandler c : clients) {
      c.sendMsg(msg);
      if (!msg.startsWith("/")){
          authService.writeMsgToDB(msg,null, null);
      }

    }
  }

  private void sendListUsers() {
    StringBuilder sb = new StringBuilder("/clientList ");
    for (ClientHandler c : clients) {
      sb.append(c.getNick()).append(" ");
    }
    String msg = sb.toString();
    broadcastMsg(msg);
  }

  public void personalMsg (String recipient, ClientHandler client, String msg, String userId) {
    String str = String.format("[%s] %s", client.getNick(), msg);
    if (recipient.equals(client.getNick())) {
      client.sendMsg(str);
      authService.writeMsgToDB(str, userId, userId);
      return;
    } else {
      for (ClientHandler c : clients) {
        if (c.checkNick(recipient)) {
          c.sendMsg(str);
          client.sendMsg(str);
          authService.writeMsgToDB(str, userId, c.getUserId());
          return;
        }
      }
    }
    client.sendMsg("пользователь " + recipient + " не найден.");
  }

  public void subscribe (ClientHandler clientHandler) {
    clients.add(clientHandler);
    logger.log(Level.INFO, clientHandler.getNick()+" подключился");
    sendListUsers();
  }

  public void unsubscribe (ClientHandler clientHandler) {
    clients.remove(clientHandler);
    logger.log(Level.INFO, clientHandler.getNick()+" отключился");
    sendListUsers();
  }

  public AuthService getAuthService() {
    return authService;
  }

  public boolean checkNick (String nick) {
    for (ClientHandler c: clients) {
      if (c.checkNick(nick)) {
        return true;
      }
    }
    return false;
  }
}