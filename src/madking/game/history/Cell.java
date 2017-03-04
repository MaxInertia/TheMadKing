package madking.game.history;

import javafx.util.Pair;

/**
 *
 */
public class Cell {
    private Pair<Integer, Integer> cell;

    /**
     * Creates a new Cell
     *
     * @param row The key for this pair
     * @param column The value to use for this pair
     */
    public Cell(int row, int column) {
        cell = new Pair<>(row, column);
    }

    public int getRow() {
        return cell.getKey();
    }

    public int getColumn() {
        return cell.getValue();
    }
}
