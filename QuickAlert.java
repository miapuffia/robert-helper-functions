package robertHelperFunctions;

import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

//This class has methods to display dialogs of various types that can return values
//Each dialog can be called in one line
public class QuickAlert {
	//Show an alert dialog
	public static void show(AlertType alertType, String title, String header) {
		Alert errorAlert = new Alert(alertType);
		errorAlert.setTitle(title);
		errorAlert.setHeaderText(header);
		errorAlert.showAndWait();
	}
	
	//Show a dialog that accepts numerical input
	public static Optional<String> showNumericalInput(String labelText, String buttonText, String defaultText) {
		Label nLabel = new Label(labelText);
		
		TextField nTextField = new TextField(defaultText);
		nTextField.setPrefWidth(50);
		
		nTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					nTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		HBox dialogHBox = new HBox(10, nLabel, nTextField);
		
		ButtonType createContainerButton = new ButtonType(buttonText, ButtonData.OK_DONE);
		
		Dialog<String> createContainerDialog = new Dialog<>();
		createContainerDialog.setTitle(buttonText);
		createContainerDialog.getDialogPane().getButtonTypes().addAll(createContainerButton);
		createContainerDialog.getDialogPane().setContent(dialogHBox);
		
		createContainerDialog.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				return nTextField.getText();
			}
		});
		
		Platform.runLater(() -> nTextField.requestFocus());
		
		return createContainerDialog.showAndWait();
	}
}