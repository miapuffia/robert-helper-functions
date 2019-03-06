package robertHelperFunctions;

//This class provides methods to perform logical XOR operations on multiple operands.
//Java has the ^ operator to perform binary XOR, but to operations exist for more operands.
public class MultiXOR {
	//Binary XOR
	public static boolean twoWay(boolean a, boolean b) {
		return a ^ b;
	}
	
	//Ternary XOR
	public static boolean threeWay(boolean a, boolean b, boolean c) {
		return ((a ^ b) ^ c) && !(a && b && c);
	}
	
	//Quaternary XOR
	public static boolean fourWay(boolean a, boolean b, boolean c, boolean d) {
		return (threeWay(a, b, c) || d) && (d ^ (a || b || c));
	}
}