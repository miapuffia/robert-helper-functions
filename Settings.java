package robertHelperFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.invoke.WrongMethodTypeException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.NoSuchElementException;

//This class provides methods to create a non-secure settings file and manage those settings
//It was designed to act like a portable version of the Preferences class
public class Settings {
	private HashMap<String, Object> settingsHashMap = new HashMap<String, Object>();
	private File saveFile;
	
	//Loads settings file and sets dynamic variables if it exists or creates it otherwise
	public Settings(String loadPath) {
		try {
			File loadFile = new File(loadPath);
			
			saveFile = new File(loadPath);
			
			if(loadFile.exists() && loadFile.isFile()) {
				BufferedReader reader = new BufferedReader(new FileReader(loadFile));
				
				String line;
				int count = 0;
				
				while((line = reader.readLine()) != null) {
					if(line.length() == 0 || line.charAt(0) != '"' || !line.contains('"' + ":(") || !line.contains("):")) {
						reader.close();
						throw new ProtocolException("Invalid settings at line " + count);
					}
					
					String settingName = line.substring(1, line.indexOf('"' + ":("));
					
					if(settingName.length() == 0) {
						reader.close();
						throw new ProtocolException("Empty setting name at line " + count);
					}
					
					if(settingsHashMap.containsKey(settingName)) {
						reader.close();
						throw new ProtocolException("Duplicate setting name at line " + count);
					}
					
					String settingType = line.substring(line.indexOf('"' + ":(") + 3, line.indexOf("):", line.indexOf('"' + ":(") + 3));
					
					if(settingType.length() == 0) {
						reader.close();
						throw new ProtocolException("Empty setting type at line " + count);
					}
					
					String settingData = line.substring(line.indexOf("):", line.indexOf('"' + ":(")) + 2);
					
					if(settingData.length() == 0 && !settingType.equals("String")) {
						reader.close();
						throw new ProtocolException("Empty setting data at line " + count);
					}
					
					switch(settingType) {
						case "Boolean":
							Boolean object1 = Boolean.valueOf(settingData);
							
							if(object1 == null) {
								throw new ProtocolException("Invalid setting data at line " + count);
							}
							
							settingsHashMap.put(settingName, object1);
							break;
						case "Integer":
							Integer object2 = Integer.valueOf(settingData);
							
							if(object2 == null) {
								throw new ProtocolException("Invalid setting data at line " + count);
							}
							
							settingsHashMap.put(settingName, object2);
							break;
						case "Double":
							Double object3 = Double.valueOf(settingData);
							
							if(object3 == null) {
								throw new ProtocolException("Invalid setting data at line " + count);
							}
							
							settingsHashMap.put(settingName, object3);
							break;
						case "String":
							settingsHashMap.put(settingName, settingData);
							break;
					}
				}
				
				reader.close();
			}
		} catch(Exception e) {
			DebugInfoFX.show(e);
		}
	}
	
	//Returns a boolean setting by name
	public boolean getBoolean(String settingName) {
		if(!settingsHashMap.containsKey(settingName)) {
			DebugInfoFX.show(new NoSuchElementException());
		}
		
		if(settingsHashMap.get(settingName) instanceof Boolean) {
			return (Boolean) settingsHashMap.get(settingName);
		} else {
			DebugInfoFX.show(new WrongMethodTypeException());
			return false;
		}
	}
	
	//Returns an int setting by name
	public int getInteger(String settingName) {
		if(!settingsHashMap.containsKey(settingName)) {
			DebugInfoFX.show(new NoSuchElementException());
		}
		
		if(settingsHashMap.get(settingName) instanceof Integer) {
			return (Integer) settingsHashMap.get(settingName);
		} else {
			DebugInfoFX.show(new WrongMethodTypeException());
			return 0;
		}
	}
	
	//Returns a double setting by name
	public double getDouble(String settingName) {
		if(!settingsHashMap.containsKey(settingName)) {
			DebugInfoFX.show(new NoSuchElementException());
		}
		
		if(settingsHashMap.get(settingName) instanceof Double) {
			return (Double) settingsHashMap.get(settingName);
		} else {
			DebugInfoFX.show(new WrongMethodTypeException());
			return 0;
		}
	}
	
	//Returns a String setting by name
	public String getString(String settingName) {
		if(!settingsHashMap.containsKey(settingName)) {
			DebugInfoFX.show(new NoSuchElementException());
		}
		
		if(settingsHashMap.get(settingName) instanceof String) {
			return (String) settingsHashMap.get(settingName);
		} else {
			DebugInfoFX.show(new WrongMethodTypeException());
			return "";
		}
	}
	
	//Adds a setting
	public void add(String settingName, Object settingData) {
		if(settingName == null) {
			throw new NullPointerException();
		}
		
		if(settingData instanceof Boolean || settingData instanceof Integer || settingData instanceof Double || settingData instanceof String) {
			settingsHashMap.put(settingName, settingData);
		} else {
			throw new IllegalArgumentException();
		}
		
		save();
	}
	
	//Sets a setting
	public void set(String settingName, Object settingData) {
		if(settingName == null) {
			DebugInfoFX.show(new NullPointerException());
		}
		
		if(!settingsHashMap.containsKey(settingName)) {
			DebugInfoFX.show(new NoSuchElementException());
		}
		
		if(settingsHashMap.get(settingName).getClass().isInstance(settingData)) {
			settingsHashMap.put(settingName, settingData);
		} else {
			DebugInfoFX.show(new IllegalArgumentException());
		}
		
		save();
	}
	
	//Saves the settings from its internal variables to the file it was instantiated with
	//While this can be called publicly, all changed are automatically saved
	public void save() {
		try {
			if(saveFile.exists()) {
				saveFile.delete();
			}
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile)));
			
			for(int i = 0; i < settingsHashMap.size(); i++) {
				bw.write('"' + ((String) settingsHashMap.keySet().toArray()[i]) + '"' + ":(" + settingsHashMap.get(settingsHashMap.keySet().toArray()[i]).getClass().getSimpleName() + "):" + settingsHashMap.get(settingsHashMap.keySet().toArray()[i]));
				
				if(i != settingsHashMap.size() - 1) {
					bw.newLine();
				}
			}
			
			bw.close();
		} catch(IOException ioe) {
			DebugInfoFX.show(ioe);
		}
	}
	
	//Returns whether a setting exists
	public boolean has(String settingName) {
		return settingsHashMap.containsKey(settingName);
	}
	
	//Deletes all settings
	public void clear() {
		settingsHashMap.clear();
	}
	
	//Prints all settings
	@Override
	public String toString() {
		return settingsHashMap.toString();
	}
}