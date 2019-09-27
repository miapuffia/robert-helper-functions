# Robert Helper Functions
A set of functions that can be used in other Java code to help simplify certain actions.

## ColorConverter
This class provides static methods to convert or alter JavaFX colors

## ColoredFormatter
This class is a formatter that changes the output text color depending on its level.

## DebugInfoFX
This class provides a JavaFX window to show when an exception occurs.
It lists all information about the exception including its stack trace.
Because of the nature of dialogs in JavaFX, the show method will pause the application thread allowing time to view the information without continuation of the thread.
Once the OK button is pressed the FX thread is exited.

## MultiXOR
This class provides methods to perform logical XOR operations on multiple operands.
Java has the ^ operator to perform binary XOR, but to operations exist for more operands.

## Nodes
This class has a method to get all descendant nodes of a parent, recursively.

## QuickAlert
This class has methods to display dialogs of various types that can return values
Each dialog can be called in one line

## Resources
This class provides methods to get a list of available resources

## Settings
This class provides methods to create a non-secure settings file and manage those settings
It was designed to act like a portable version of the Preferences class

## ToDecimal
This class provides a static method to round a number to a number of decimals.
