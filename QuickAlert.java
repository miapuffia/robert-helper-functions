import java.util.LinkedHashMap;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

//This class has methods to display dialogs of various types that can return values
//Each dialog can be called in one line
public class QuickAlert {
	//Show an alert dialog
	public static void show(AlertType alertType, String title, String header) {
		show(alertType, title, header, null);
	}
	
	//Show an alert dialog with an icon
	public static void show(AlertType alertType, String title, String header, Image icon) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		
		if(icon != null) {
			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(icon);
		}
		
		alert.showAndWait();
	}
	
	//Show an alert dialog with custom buttons and an icon
	public static Optional<ButtonType> showCustomButtons(AlertType alertType, String title, String header, boolean removeDefaultButtons, ButtonType... buttons) {
		return showCustomButtons(alertType, title, header, removeDefaultButtons, null, buttons);
	}
	
	//Show an alert dialog with custom buttons
	public static Optional<ButtonType> showCustomButtons(AlertType alertType, String title, String header, boolean removeDefaultButtons, Image icon, ButtonType... buttons) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		
		if(icon != null) {
			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(icon);
		}
		
		if(removeDefaultButtons) {
			alert.getButtonTypes().clear();
		}
		
		for(ButtonType button : buttons) {
			alert.getButtonTypes().add(button);
		}
		
		return alert.showAndWait();
	}
	
	//Show a dialog that accepts numerical input without defaults
	public static Optional<Number[]> showNumericalInput(String title, String buttonText, String... labelTexts) {
		LinkedHashMap<String, Number> tempLinkedHashMap = new LinkedHashMap<String, Number>();
		
		for(int i = 0; i < labelTexts.length; i++) {
			tempLinkedHashMap.put(labelTexts[i], null);
		}
		
		return showNumericalInput(title, buttonText, tempLinkedHashMap, null);
	}
	
	//Show a dialog that accepts numerical input without defaults and an icon
	public static Optional<Number[]> showNumericalInput(String title, String buttonText, Image icon, String... labelTexts) {
		LinkedHashMap<String, Number> tempLinkedHashMap = new LinkedHashMap<String, Number>();
		
		for(int i = 0; i < labelTexts.length; i++) {
			tempLinkedHashMap.put(labelTexts[i], null);
		}
		
		return showNumericalInput(title, buttonText, tempLinkedHashMap, icon);
	}
	
	//Show a dialog that accepts numerical input with default value
	public static Optional<Number[]> showNumericalInput(String title, String buttonText, LinkedHashMap<String, Number> labelNumberData) {
		return showNumericalInput(title, buttonText, labelNumberData, null);
	}
	
	//Show a dialog that accepts numerical input with default value and an icon
	public static Optional<Number[]> showNumericalInput(String title, String buttonText, LinkedHashMap<String, Number> labelNumberData, Image icon) {
		Number[] returnNumbers = new Number[labelNumberData.size()];
		
		GridPane mainGridPane = new GridPane();
		mainGridPane.setHgap(10);
		mainGridPane.setVgap(10);
		
		int i = 0;
		for(String label : labelNumberData.keySet()) {
			final int index = i;
			
			Label nLabel = new Label(label);
			
			TextField nTextField = new TextField();
			nTextField.setMaxWidth(50);
			
			if(labelNumberData.get(label) != null) {
				nTextField.setText(labelNumberData.get(label).toString());
			}
			
			nTextField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(!newValue.matches("\\d*")) {
						nTextField.setText(oldValue);
					} else {
						try {
							returnNumbers[index] = Integer.parseInt(newValue);
						} catch(NumberFormatException nfe) {
							returnNumbers[index] = null;
						}
					}
				}
			});
			
			mainGridPane.add(nLabel, 0, mainGridPane.getRowCount());
			mainGridPane.add(nTextField, 1, mainGridPane.getRowCount() - 1);
			
			i++;
		}
		
		ButtonType createContainerButton = new ButtonType(buttonText, ButtonData.OK_DONE);
		
		Dialog<Number[]> createContainerDialog = new Dialog<>();
		createContainerDialog.setTitle(title);
		createContainerDialog.getDialogPane().getButtonTypes().addAll(createContainerButton);
		createContainerDialog.getDialogPane().setContent(mainGridPane);
		
		if(icon != null) {
			((Stage) createContainerDialog.getDialogPane().getScene().getWindow()).getIcons().add(icon);
		}
		
		createContainerDialog.setResultConverter(new Callback<ButtonType, Number[]>() {
			@Override
			public Number[] call(ButtonType b) {
				
				
				
				
				return returnNumbers;
			}
		});
		
		return createContainerDialog.showAndWait();
	}
	
	//Show a dialog that returns a selected radio button
	public static Optional<Integer> showRadioBox(AlertType alertType, String title, String header, String buttonText, RadioButton... radioButtons) {
		Label headerLabel = null;
		
		if(!header.equals("")) {
			headerLabel = new Label(header);
		}
		
		ToggleGroup radioGroup = new ToggleGroup();
		
		radioButtons[0].setSelected(true);
		
		for(RadioButton radioButton : radioButtons) {
			radioButton.setToggleGroup(radioGroup);
		}
		
		VBox radioButtonsVBox = new VBox(10, headerLabel);
		radioButtonsVBox.getChildren().addAll(radioButtons);
		
		ButtonType createContainerButton = new ButtonType(buttonText, ButtonData.OK_DONE);
		
		Dialog<Integer> createContainerDialog = new Dialog<>();
		createContainerDialog.setTitle(title);
		createContainerDialog.getDialogPane().getButtonTypes().addAll(createContainerButton);
		createContainerDialog.getDialogPane().setContent(radioButtonsVBox);
		createContainerDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		createContainerDialog.setResultConverter(new Callback<ButtonType, Integer>() {
			@Override
			public Integer call(ButtonType b) {
				for(int i = 0; i < radioButtons.length; i++) {
					if(radioButtons[i].isSelected()) {
						return i;
					}
				}
				
				return 0;
			}
		});
		
		return createContainerDialog.showAndWait();
	}
	
	//Show a dialog as a work service
	public static void showService(AlertType alertType, String title, String header) {
		AlertShowService alertService = new AlertShowService(alertType, title, header);
		alertService.start();
	}
}

class AlertShowService extends Service<Void> {
	AlertType alertType;
	String title;
	String header;
	
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Platform.runLater(() -> {
					Alert alert = new Alert(alertType);
					alert.setTitle(title);
					alert.setHeaderText(header);
					alert.showAndWait();
				});
				
				return null;
			}
		};
	}
	
	AlertShowService(AlertType alertType, String title, String header) {
		this.alertType = alertType;
		this.title = title;
		this.header = header;
		
		this.setOnScheduled(e -> {
			
		});
	}
}
