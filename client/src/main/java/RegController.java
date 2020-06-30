import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegController {
  @FXML
  public TextField loginField;
  @FXML
  public PasswordField passwordField;
  @FXML
  public TextField nickField;
  Controller controller;

  public void clickOk(ActionEvent actionEvent) {
    controller.tryRegistration(loginField.getText().trim(), passwordField.getText().trim(),
            nickField.getText().trim());
  }

    public void changeName(ActionEvent actionEvent) {
      controller.sendMsg("/changeName " + loginField.getText().trim() + " " + passwordField.getText().trim() + " " + nickField.getText().trim());
    }
}