import javafx.scene.paint.Color;

//This class provides static methods to convert or alter JavaFX colors
public class ColorConverter {
	public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
	
	public static Color brighter(Color color) {
		double[] hsl = RGBtoHSL(color);

		if(hsl[2] < 0.7) {
			hsl[2] += 0.3;
		} else {
			hsl[2] = 1.0;
		}

		return HSLtoRGB(hsl);
	}
	
	private static double[] RGBtoHSL(Color color) {
		// Get RGB values in the range 0 - 1

				double r = color.getRed();
				double g = color.getGreen();
				double b = color.getBlue();

				// Minimum and Maximum RGB values are used in the HSL calculations

				double min = Math.min(r, Math.min(g, b));
				double max = Math.max(r, Math.max(g, b));

				// Calculate the Hue

				double h = 0;

				if (max == min)
					h = 0;
				else if (max == r)
					h = ((60 * (g - b) / (max - min)) + 360) % 360;
				else if (max == g)
					h = (60 * (b - r) / (max - min)) + 120;
				else if (max == b)
					h = (60 * (r - g) / (max - min)) + 240;

				// Calculate the Luminance

				double l = (max + min) / 2;

				// Calculate the Saturation

				double s = 0;

				if (max == min)
					s = 0;
				else if (l <= .5f)
					s = (max - min) / (max + min);
				else
					s = (max - min) / (2 - max - min);

				return new double[] { h, s, l };
	}
	
	private static Color HSLtoRGB(double[] hsl) {
		final double h = hsl[0];
        final double s = hsl[1];
        final double l = hsl[2];
        final double c = (1.0 - Math.abs(2 * l - 1.0)) * s;
        final double m = l - 0.5 * c;
        final double x = c * (1.0 - Math.abs((h / 60.0 % 2.0) - 1.0));
        final int hueSegment = (int) h / 60;
        int r = 0, g = 0, b = 0;
        switch (hueSegment) {
            case 0:
                r = (int) Math.round(255 * (c + m));
                g = (int) Math.round(255 * (x + m));
                b = (int) Math.round(255 * m);
                break;
            case 1:
                r = (int) (int) Math.round(255 * (x + m));
                g = (int) (int) Math.round(255 * (c + m));
                b = (int) (int) Math.round(255 * m);
                break;
            case 2:
                r = (int) Math.round(255 * m);
                g = (int) Math.round(255 * (c + m));
                b = (int) Math.round(255 * (x + m));
                break;
            case 3:
                r = (int) Math.round(255 * m);
                g = (int) Math.round(255 * (x + m));
                b = (int) Math.round(255 * (c + m));
                break;
            case 4:
                r = (int) Math.round(255 * (x + m));
                g = (int) Math.round(255 * m);
                b = (int) Math.round(255 * (c + m));
                break;
            case 5:
            case 6:
                r = (int) Math.round(255 * (c + m));
                g = (int) Math.round(255 * m);
                b = (int) Math.round(255 * (x + m));
                break;
        }
        r = (int) constrain(r, 0, 255);
        g = (int) constrain(g, 0, 255);
        b = (int) constrain(b, 0, 255);
        return Color.rgb(r, g, b);
	}
	
	private static double constrain(double amount, double low, double high) {
		return amount < low ? low : (amount > high ? high : amount);
	}
}
