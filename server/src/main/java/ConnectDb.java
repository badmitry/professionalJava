import java.sql.*;

public class ConnectDb {
    private Connection connection;
    private PreparedStatement psRegistration;
    private PreparedStatement psLogIn;
    private PreparedStatement psChangeName;
    private Statement stmt;
    private PreparedStatement psWriteChatToDb;

    public ConnectDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:server/src/main/resources/dbChat.db");
            stmt = connection.createStatement();
            psWriteChatToDb = connection.prepareStatement("INSERT INTO chat (message, senderID, recipientID) " +
                    "VALUES (?, ?, ?)");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String logIn(String login, String password) {
        String name;
        String id;
        try {
            psLogIn = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            psLogIn.setString(1, login);
            psLogIn.setString(2, password);
            if (psLogIn.executeQuery().isClosed()) {
                return null;
            } else {
                name = psLogIn.executeQuery().getString("name");
                id = psLogIn.executeQuery().getString("id");
                psLogIn.close();
                return name + " " + id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                psLogIn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public boolean registration(String name, String login, String password) {
        try {
            psRegistration = connection.prepareStatement("INSERT INTO users (name, login, password) VALUES (?, ?, ?)");
            psRegistration.setString(1, name);
            psRegistration.setString(2, login);
            psRegistration.setString(3, password);
            psRegistration.executeUpdate();
            psRegistration.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                psRegistration.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public void changeName(String login, String password, String name) {
        try {
            psChangeName = connection.prepareStatement("UPDATE users SET name = ? WHERE login = ? AND password = ?");
            psChangeName.setString(1, name);
            psChangeName.setString(2, login);
            psChangeName.setString(3, password);
            psChangeName.executeUpdate();
            psChangeName.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                psChangeName.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String readChat(String userId) {
        try {
            StringBuilder sb = new StringBuilder();
            ResultSet rs = stmt.executeQuery("SELECT message FROM chat WHERE " +
                    "(senderID = 'null' AND recipientID = 'null') OR " + userId + " = senderID OR " + userId + " = recipientID");
            while (rs.next()) {
                sb.append(rs.getString(1) + "\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeMsgToDB(String str, String sender, String recipient) {
        try {
            psWriteChatToDb.setString(1, str);
            psWriteChatToDb.setString(2, sender);
            psWriteChatToDb.setString(3, recipient);
            stmt.executeUpdate("INSERT INTO chat (message, senderID, recipientID) " +
                    "VALUES ('" + str + "', '" + sender + "', '" + recipient + "')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

