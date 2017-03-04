package madking.game.history;

import javafx.util.Pair;

/**
 *
 */
public class Move {
    private Pair<Cell, Cell> move;

    /**
     * Creates a new Move
     *
     * @param row The initial row
     * @param column The initial column
     * @param newRow The final row
     * @param newColumn The final column
     */
    public Move(int row, int column, int newRow, int newColumn) {
        move = new Pair<>(new Cell(row,column), new Cell(newRow,newColumn));
    }

    public Cell getInitialCell() {
        return move.getKey();
    }

    public Cell getFinalCell() {
        return move.getValue();
    }
}
