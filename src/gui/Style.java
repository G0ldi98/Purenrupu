package gui;

import javafx.scene.paint.Color;

public class Style {
    public static String getCellColorAsString(int value) {
        if (value == 1) {
            return "DARKGRAY";
        }
        return "white";
    }

    public static Color getCellColor(int value) {
        if (value == 1) {
            return Color.DARKGRAY;
        }
        return Color.WHITE;
    }
}
