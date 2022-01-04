package assingnment5;

import java.util.*;
import java.util.List;

/**
 * @author Ansari Mohammed Faisal - 000812671
 */
public class Game extends javafx.scene.canvas.Canvas {

    private Cell[] cells;
    boolean won = false;
    boolean lost = false;
    int score = 0;

    /**
     * @return return cells array
     */
    public Cell[] getCells() {
        return cells;
    }

    /**
     * Called to reset Game
     */
    public Game() {
        super(330, 390);
        setFocused(true);
        resetGame();
    }

    public Game(double width, double height) {
        super(width, height);
        setFocused(true);
        resetGame();
    }


    /**
     * Fuction called to reset whole game
     */
    void resetGame() {
        score = 0;
        won = false;
        lost = false;
        cells = new Cell[4 * 4];
        for (int cell = 0; cell < cells.length; cell++) {
            cells[cell] = new Cell();
        }
        cellAdd();
        cellAdd();
    }

    /**
     * Adds number in Remaining Space
     */
    private void cellAdd() {
        List<Cell> list = remainingSpace();
        if (!remainingSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Cell emptyCell = list.get(index);
            emptyCell.number = Math.random() < 0.9 ? 2 : 4;
        }

    }

    /**
     * @return returns a array list with remaining spaces
     */
    private List<Cell> remainingSpace() {
        List<Cell> space = new ArrayList<>(16);
        for (Cell c : cells)
            if (c.isEmpty())
                space.add(c);
        return space;
    }

    /**
     * @return return true or false based on remaining space
     */
    private boolean isFull() {
        return remainingSpace().size() == 0;
    }

    /**
     * @param x X pos of Cell
     * @param y Y pos of Cell
     * @return return Cells at specific pos
     */
    private Cell cellAt(int x, int y) {
        return cells[x + y * 4];
    }

    /**
     * @return This method checks if the the cell is movable to next position
     */
    protected boolean move() {
        if (!isFull()) return true;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Cell cell = cellAt(x, y);
                if ((x < 3 && cell.number == cellAt(x + 1, y).number) ||
                        (y < 3) && cell.number == cellAt(x, y + 1).number) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param column1 array of particular column
     * @param column2 array of particular column
     * @return compares and both column and return a tru or false based on that
     */
    private boolean compare(Cell[] column1, Cell[] column2) {
        if (column1 == column2) {
            return true;
        }
        if (column1.length != column2.length) {
            return false;
        }

        for (int i = 0; i < column1.length; i++) {
            if (column1[i].number != column2[i].number) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param angle angle at which the cells should be moved
     * @return returns new array which has updated cells
     */
    private Cell[] rotate(int angle) {
        Cell[] tile = new Cell[4 * 4];
        int offsetX = 3;
        int offsetY = 3;
        if (angle == 90) {
            offsetY = 0;
        } else if (angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                tile[(newX) + (newY) * 4] = cellAt(x, y);
            }
        }
        return tile;
    }

    /**
     * @param column array of old column
     * @return returns new line which has been updated
     */
    private Cell[] moveColumn(Cell[] column) {
        LinkedList<Cell> list = new LinkedList<Cell>();
        for (int i = 0; i < 4; i++) {
            if (!column[i].isEmpty()) {
                list.addLast(column[i]);
            }
        }

        if (list.size() == 0) {
            return column;
        } else {
            Cell[] newColumn = new Cell[4];
            while (list.size() != 4) {
                list.add(new Cell());
            }
            for (int j = 0; j < 4; j++) {
                newColumn[j] = list.removeFirst();
            }
            return newColumn;
        }
    }

    /**
     * @param column Array of old line
     * @return returns new array that has moved and added values
     */
    private Cell[] mergeLine(Cell[] column) {
        LinkedList<Cell> list = new LinkedList<Cell>();
        for (int i = 0; i < 4 && !column[i].isEmpty(); i++) {
            int num = column[i].number;
            if (i < 3 && column[i].number == column[i + 1].number) {
                num *= 2;
                score += num;
                if (num == 2048) {
                    won = true;
                }
                i++;
            }
            list.add(new Cell(num));
        }

        if (list.size() == 0) {
            return column;
        } else {
            while (list.size() != 4) {
                list.add(new Cell());
            }
            return list.toArray(new Cell[4]);
        }
    }

    /**
     * @param pos position of column
     * @return returns values of current column
     */
    private Cell[] getColumn(int pos) {
        Cell[] result = new Cell[4];
        for (int i = 0; i < 4; i++) {
            result[i] = cellAt(i, pos);
        }
        return result;
    }

    private void setColumn(int index, Cell[] re) {
        System.arraycopy(re, 0, cells, index * 4, 4);
    }

    /**
     * The method which calls other methods and moves all the cells
     */
    public void left() {
        boolean needAddCell = false;
        for (int i = 0; i < 4; i++) {
            Cell[] line = getColumn(i);
            Cell[] merged = mergeLine(moveColumn(line));
            setColumn(i, merged);
            if (!needAddCell && !compare(line, merged)) {
                needAddCell = true;
            }
        }
        if (needAddCell) {
            cellAdd();
        }
    }

    /**
     * function for press right key
     */
    public void right() {
        cells = rotate(180);
        left();
        cells = rotate(180);
    }

    /**
     * function for press of up key
     */
    public void up() {
        cells = rotate(270);
        left();
        cells = rotate(90);
    }

    /**
     * function for press of down key
     */
    public void down() {
        cells = rotate(90);
        left();
        cells = rotate(270);
    }
}
