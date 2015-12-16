package gardens;

import java.awt.Color;
import thom.swing.MoreColor;

public class ColorWheel{
    
    public static Color 
            Red = Color.RED,
            Navy = MoreColor.Navy,
            DarkGreen = MoreColor.DarkGreen,
            Yellow = Color.YELLOW,
            Brown = MoreColor.Brown,
            Pink = Color.PINK,
            Orange = Color.ORANGE,
            LightBlue = MoreColor.LightBlue,
            LightGreen = MoreColor.LightGreen,
            Cyan = Color.CYAN,
            Violet = MoreColor.Violet,
            Magenta = Color.MAGENTA,
            Grey = Color.GRAY
            ;
    
    private static int index = -1;
    
    public static Color newColor(){
        index++;
        switch (index) {
            case 0: return Red;
            case 1: return Navy;
            case 2: return DarkGreen;
            case 3: return Yellow;
            case 4: return Brown;
            case 5: return Pink;
            case 6: return Orange;
            case 7: return LightBlue;
            case 8: return LightGreen;
            case 9: return Cyan;
            case 10: return Violet;
            case 11: return Magenta;
            case 12: return Grey;
            default:
                throw new AssertionError();
        }

    }
    
    public static void resetColorWheel(){
        index = -1;
    }

    static Color getComplementColor(Color color) {
        if (color==Color.BLACK ||
                color==Red || color==Navy ||
                color==DarkGreen || color==Brown ||
                color==Violet || color==Magenta
                ) return Color.WHITE;
        else return Color.BLACK;
    }

    static String getString(Color color) {
        if (color.equals(Red)) {
            return "Red";
        }else if (color.equals(Navy)) {
            return "Navy";
        }else if (color.equals(DarkGreen)) {
            return "Dark Green";
        }else if (color.equals(Yellow)) {
            return "Yellow";
        }else if (color.equals(Brown)) {
            return "Brown";
        }else if (color.equals(Pink)) {
            return "Pink";
        }else if (color.equals(Orange)) {
            return "Orange";
        }else if (color.equals(LightBlue)) {
            return "Light Blue";
        }else if (color.equals(LightGreen)) {
            return "Light Green";
        }else if (color.equals(Cyan)) {
            return "Cyan";
        }else if (color.equals(Violet)) {
            return "Violet";
        }else if (color.equals(Magenta)) {
            return "Magenta";
        }else if (color.equals(Grey)) {
            return "Grey";
        }else throw new java.lang.IllegalArgumentException("Color not supported");
    }
    
}