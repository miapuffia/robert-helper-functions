package robertHelperFunctions;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.Parent;

//This class has a method to get all descendant nodes of a parent, recursively.
public class Nodes {
	//Get all descendants recursively
	public static ArrayList<Node> getAllNodes(Parent root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}
	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for(Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if(node instanceof Parent) {
				addAllDescendents((Parent)node, nodes);
			}
		}
	}
}