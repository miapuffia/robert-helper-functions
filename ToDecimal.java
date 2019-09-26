//Class that provides a static method to round a number to a number of decimals.
public class ToDecimal {
	public static double to(int decimals, double value) {
		return (double) ((int) Math.round(value * Math.pow(10, decimals))) / Math.pow(10, decimals);
	}
}