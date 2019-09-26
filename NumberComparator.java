import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//This class provides a safe way to compare two Number objects
@SuppressWarnings("unchecked")
class NumberComparator implements Comparator<Number> {

    // Special values that are treated as larger than any other.
    private final static List<?> special =
            Arrays.asList(Double.NaN, Float.NaN, null);

    private final static List<?> largest =
            Arrays.asList(Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

    private final static List<?> smallest =
            Arrays.asList(Double.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

	@SuppressWarnings("rawtypes")
	public int compare(Number n1, Number n2) {

        // Handle special cases (including null)
        if (special.contains(n1)) return  1;
        if (special.contains(n2)) return -1;

        if (largest.contains(n1) || smallest.contains(n2)) return  1;
        if (largest.contains(n2) || smallest.contains(n1)) return -1;

        // Promote known values (Byte, Integer, Long, Float, Double and
        // BigInteger) to BigDecimal, as this is the most generic known type.
        BigDecimal bd1 = asBigDecimal(n1);
        BigDecimal bd2 = asBigDecimal(n2);
        if (bd1 != null && bd2 != null)
            return bd1.compareTo(bd2);

        // Handle arbitrary Number-comparisons if o1 and o2 are of same class
        // and implements Comparable.
        if (n1 instanceof Comparable<?> && n2 instanceof Comparable<?>)
            try {
                return ((Comparable) n1).compareTo((Comparable) n2);
            } catch (ClassCastException cce) {
            }

        // If the longValue()s differ between the two numbers, trust these.
        int longCmp = ((Long) n1.longValue()).compareTo(n2.longValue());
        if (longCmp != 0)
            return longCmp;

        // Pray to god that the doubleValue()s differ between the two numbers.
        int doubleCmp = ((Double) n1.doubleValue()).compareTo(n2.doubleValue());
        if (doubleCmp != 0)
            return longCmp;

        // Die a painful death...
        throw new UnsupportedOperationException(
                "Cannot compare " + n1 + " with " + n2);
    }


    // Convert known Numbers to BigDecimal, and the argument n otherwise.
    private BigDecimal asBigDecimal(Number n) {
        if (n instanceof Byte)       return new BigDecimal((Byte) n);
        if (n instanceof Integer)    return new BigDecimal((Integer) n);
        if (n instanceof Short)      return new BigDecimal((Short) n);
        if (n instanceof Long)       return new BigDecimal((Long) n);
        if (n instanceof Float)      return new BigDecimal((Float) n);
        if (n instanceof Double)     return new BigDecimal((Double) n);
        if (n instanceof BigInteger) return new BigDecimal((BigInteger) n);
        if (n instanceof BigDecimal) return (BigDecimal) n;
        return null;
    }
}
