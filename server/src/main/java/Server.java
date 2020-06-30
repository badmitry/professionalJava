import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private List<ClientHandler> clients;
  private AuthService authService;
  public ExecutorService executorService;

  public Server() {
    clients = new Vector<>();
    authService = new SimpleAuthService();
    ServerSocket serverSocket = null;
    Socket socket;
    final int PORT = 10000;

    try {
      serverSocket = new ServerSocket(PORT);
      System.out.println("Сервер запущен");
      executorService = Executors.newCachedThreadPool();
      while (true) {
        socket = serverSocket.accept();
        System.out.println("Клиент подключился");
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
    sendListUsers();
  }

  public void unsubscribe (ClientHandler clientHandler) {
    clients.remove(clientHandler);
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