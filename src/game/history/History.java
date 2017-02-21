package game.history;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 *
 */
public class History {
    private ArrayList<Move> moves;

    public History() {
        moves = new ArrayList<>();
    }

    public void addMove(int row, int column, int newRow, int newColumn) {
        moves.add(new Move(row, column, newRow, newColumn));
    }
}
