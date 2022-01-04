package assingnment5;

import javafx.scene.paint.Color;

/**
 * @author ANsari Mohammed Faisal - 000812671
 */
public class Cell {
    int number;

    /**
     * Initializes value
     */
    public Cell() {
        this.number = 0;
    }

    /**
     * @param number number to set value of cell
     */
    public Cell(int number) {
        this.number = number;
    }

    /**
     * @return returns true if number is 0
     */
    public boolean isEmpty() {
        return number == 0;
    }

    /**
     * @return returns color based on number
     */
    public Color getBackground() {
        switch (number) {
            case 2:
                return Color.rgb(238, 228, 218, 1.0);
            case 4:
                return Color.rgb(237, 224, 200, 1.0);
            case 8:
                return Color.rgb(242, 177, 121, 1.0);
            case 16:
                return Color.rgb(245, 149, 99, 1.0);
            case 32:
                return Color.rgb(246, 124, 95, 1.0);
            case 64:
                return Color.rgb(246, 94, 59, 1.0);
            case 128:
                return Color.rgb(237, 207, 114, 1.0);
            case 256:
                return Color.rgb(237, 204, 97, 1.0);
            case 512:
                return Color.rgb(237, 200, 80, 1.0);
            case 1024:
                return Color.rgb(237, 197, 63, 1.0);
            case 2048:
                return Color.rgb(237, 194, 46, 1.0);
        }
        return Color.rgb(205, 193, 180, 1.0); //0xcdc1b4
    }

    /**
     * @return returns foreground color
     */
    public Color getForeground() {
        Color foreground;
        if (number < 16) {
            foreground = Color.rgb(119, 110, 101, 1.0); //0x776e65
        } else {
            foreground = Color.rgb(249, 246, 242, 1.0);    //0xf9f6f2
        }
        return foreground;
    }
}
