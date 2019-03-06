package robertHelperFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//This class provides methods to get a list of available resources
public class Resources {
	//Get list of resources with the path non-recursively
	public List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();
		
		try(
			InputStream in = getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in))
		) {
			String resource;
			
			while((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}
		
		return filenames;
	}
	
	//Get list of resources with the path recursively
	public List<String> getResourceFilesRecursive(String path) throws IOException {
		List<String> returnList = new ArrayList<>();
		List<String> list = getResourceFiles(path);
		
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).indexOf(".") == -1) {
				returnList.addAll(getResourceFilesRecursive(path + "/" + list.get(i)));
			} else {
				returnList.add(path + "/" + list.get(i));
			}
		}
		
		return returnList;
	}
	
	private InputStream getResourceAsStream(String resource) {
		final InputStream in = getContextClassLoader().getResourceAsStream(resource);
		
		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}