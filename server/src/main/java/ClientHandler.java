import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private String nick;
    private String login;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            server.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //authentification/registration/
                        while (true) {
                            String str = inputStream.readUTF();

                            if (str.startsWith("/auth ")) {
                                server.createLogMsg(Level.INFO,"Клиент отправил запрос на аутонтификацию");
                                socket.setSoTimeout(120000);
                                String[] token = str.split(" ");

                                if (token.length < 3) {
                                    sendMsg("Вы не заполнили логин/пароль");
                                    continue;
                                }
                                String userData = server.getAuthService().getNickNameAndId(token[1], token[2]);
                                if (userData == null) {
                                    sendMsg("Неверный логин / пароль");
                                } else {
                                    String[] userDataArr = userData.split(" ");
                                    String newNick = userDataArr[0];
                                    userId = userDataArr[1];
                                    if (server.checkNick(newNick)) {
                                        sendMsg("Пользователь " + newNick + " уже зарегестрирован в системе.");
                                    } else if (newNick != null) {
                                        sendMsg("/authok " + newNick);
                                        sendMsg(server.getAuthService().readChat(userId));
                                        socket.setSoTimeout(0);
                                        nick = newNick;
                                        login = token[1];
                                        server.subscribe(ClientHandler.this);
                                        server.broadcastMsg("Клиент: " + nick + " подключился");
                                        break;
                                    } else {
                                        sendMsg("Неверный логин / пароль");
                                    }
                                }
                            } else if (str.startsWith("/reg ")) {
                                server.createLogMsg(Level.INFO,"Клиент отправил запрос на регистрацию");
                                String[] token = str.split(" ");

                                if (token.length < 4) {
                                    sendMsg("Вы не заполнили все поля");
                                    continue;
                                }
                                boolean succeed = server.getAuthService().registration(token[1], token[2], token[3]);
                                if (succeed) {
                                    sendMsg("Регистрация успешно прошла.");
                                    server.createLogMsg(Level.INFO,"Создан новый пользователь: " + token[3]);
                                } else {
                                    sendMsg("Login или nickName заняты. \nПопробуйте снова!");
                                }

                            } else if (str.startsWith("/changeName ")) {
                                String[] token = str.split(" ");

                                if (token.length < 4) {
                                    sendMsg("Вы не заполнили все поля");
                                    continue;
                                }
                                String newNick = server.getAuthService().getNickNameAndId(token[1], token[2]);
                                if (newNick == null) {
                                    sendMsg("Вы ввели неправильные учетные данные");
                                } else {
                                    String[] nickToken = newNick.split(" ");
                                    if (server.checkNick(nickToken[0])) {
                                        sendMsg("Пользователь " + nickToken[0] + " зарегестрирован в системе.");
                                    } else {
                                        server.getAuthService().changeName(token[1], token[2], token[3]);
                                        sendMsg("Имя успешно изменено.");
                                    }
                                }
                            }

                        }


                        //chat
                        while (true) {
                            String str = inputStream.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    sendMsg("/end");
                                    break;
                                }
                                if (str.startsWith("/w ")) {
                                    String[] arr = str.split(" ", 3);
                                    if (server.checkNick(arr[1])) {
                                        server.personalMsg(arr[1], ClientHandler.this, arr[2], userId);
                                    } else {
                                        sendMsg("Клиент " + arr[1] + " не подключен");
                                    }
                                }
                            } else {
                                server.broadcastMsg(nick + ": " + str);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Клиент отключился");
                        server.broadcastMsg(nick + " отключился");
                        server.unsubscribe(ClientHandler.this);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
//            Thread t1 = new Thread(() -> {
//
//            });
//            t1.setDaemon(true);
//            t1.start();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNick(String nick) {
        if (this.nick.equals(nick)) {
            return true;
        } else {
            return false;
        }
    }

    public String getNick() {
        return nick;
    }

}