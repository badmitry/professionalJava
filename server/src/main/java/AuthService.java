public interface AuthService {
  String getNickNameAndId(String login, String password);
  boolean registration(String login, String password, String nickname);
  void changeName(String s, String s1, String s2);
  String readChat(String userId);

  void writeMsgToDB(String str, String userId, String userId1);
}