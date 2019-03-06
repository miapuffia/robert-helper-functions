package robertHelperFunctions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//This class provides a JavaFX window to show when an exception occurs.
//It lists all information about the exception including its stack trace.
//Because of the nature of dialogs in JavaFX, the show method will pause the application thread
//allowing time to view the information without continuation of the thread.
//Once the OK button is pressed the FX thread is exited.
public class DebugInfoFX {
	//Display the dialog (freezes the thread)
	public static void show(Exception exception) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		String debugText = "";
		
		if(exception == null) {
			debugText = "Class: " + stackTraceElements[2].getClassName() + "\nMethod: " + stackTraceElements[2].getMethodName() + "\nLine number: " + stackTraceElements[2].getLineNumber() + "\n\nNo stack trace";
		} else {
			debugText = "Class: " + stackTraceElements[2].getClassName() + "\nMethod: " + stackTraceElements[2].getMethodName() + "\nLine number: " + stackTraceElements[2].getLineNumber() + "\n\n";
			
			stackTraceElements = exception.getStackTrace();
			
			for(StackTraceElement stackTract : stackTraceElements){
				debugText += stackTract.toString() + "\n";
			}
		}
		
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("A critical program error has occurred");
		errorAlert.setHeaderText("Below is the location of the error. The program will be terminated shortly after this window is closed.");
		errorAlert.setContentText(debugText);
		errorAlert.setResizable(true);
		
		errorAlert.showAndWait();
		
		Platform.exit();
	}
}