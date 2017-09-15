import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogInWindow {


	public static void display(){
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Login");


		HBox usernameHBox = new HBox(10);
		Label username = new Label("Username: ");
		TextField usernameTextfield = new TextField();
		usernameHBox.getChildren().addAll(username, usernameTextfield);
		usernameHBox.setPadding(new Insets(5,5,5,5));

		HBox passwordHBox = new HBox(10);
		Label password = new Label("Password: ");
		TextField passwordTextField = new TextField();
		passwordHBox.getChildren().addAll(password, passwordTextField);
		passwordHBox.setPadding(new Insets(5,5,5,5));

		Button login = new Button("Login");
		login.setOnAction(e-> window.close());

		HBox loginHBox = new HBox();
		loginHBox.getChildren().add(login);
		loginHBox.setPadding(new Insets(5,5,5,5));
		loginHBox.setAlignment(Pos.CENTER_RIGHT);

		VBox layout = new VBox(5);
		layout.getChildren().addAll(usernameHBox, passwordHBox, loginHBox);

		Scene scene = new Scene(layout,250,170);
		window.setScene(scene);
		window.showAndWait();

	}


}
