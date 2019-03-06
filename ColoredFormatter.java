package robertHelperFunctions;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//This class is a formatter that changes the output text color depending on its level.
//I found this useful in testing and logging.
public class ColoredFormatter extends Formatter {
	private boolean keepLine = false;
	
	// ANSI escape code
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30;1m";
	public static final String ANSI_RED = "\u001B[31;1m";
	public static final String ANSI_GREEN = "\u001B[32;1m";
	public static final String ANSI_YELLOW = "\u001B[33;1m";
	public static final String ANSI_BLUE = "\u001B[34;1m";
	public static final String ANSI_PURPLE = "\u001B[35;1m";
	public static final String ANSI_CYAN = "\u001B[36;1m";
	public static final String ANSI_WHITE = "\u001B[37;1m";

	// Here you can configure the format of the output and 
	// its color by using the ANSI escape codes defined above.

	// format is called for every console log message
	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();
		
		if(record.getLevel() == Level.SEVERE) {
			builder.append(ANSI_RED);
		} else if(record.getLevel() == Level.WARNING) {
			builder.append(ANSI_YELLOW);
		} else if(record.getLevel() == Level.INFO) {
			builder.append(ANSI_GREEN);
		} else if(record.getLevel() == Level.CONFIG) {
			builder.append(ANSI_WHITE);
		} else if(record.getLevel() == Level.FINE) {
			builder.append(ANSI_CYAN);
		} else if(record.getLevel() == Level.FINER) {
			builder.append(ANSI_BLUE);
		} else if(record.getLevel() == Level.FINEST) {
			builder.append(ANSI_PURPLE);
		}
		
		builder.append(record.getMessage());

		builder.append(ANSI_RESET);
		
		if(!keepLine) {
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	//Sets a flag that determines whether to add a new line character to the output
	public void setKeepLine(boolean b) {
		keepLine = b;
	}
	
	@Override
	public synchronized String formatMessage(LogRecord record) {
		return "";
	}
}