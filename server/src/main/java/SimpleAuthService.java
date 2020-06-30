public class SimpleAuthService implements AuthService {
  ConnectDb connect;

  public SimpleAuthService() {
    connect = new ConnectDb();
  }

  @Override
  public String getNickNameAndId(String login, String password) {
    return (connect.logIn(login, password));
  }

  @Override
  public boolean registration(String login, String password, String nickname) {
    return (connect.registration(nickname, login, password));
  }

  @Override
  public void changeName(String login, String password, String name) {
    connect.changeName(login, password, name);
  }

  @Override
  public String readChat(String userId) {
    return (connect.readChat(userId));
  }

  @Override
  public void writeMsgToDB(String str, String sender, String recipient) {
    connect.writeMsgToDB(str, sender, recipient);
  }
}