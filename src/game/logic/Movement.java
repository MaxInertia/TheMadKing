package game.logic;

import game.pieces.Piece;

/**
 *
 */
public class Movement {

    private Piece[][] board;

    private Movement(Piece[][] board) {
        this.board = board;
    }

    /**
     * Check if the move is valid
     *
     * @precondition There must be a piece at (row,column) ... board[row][column] != null
     *
     * @param row initial row
     * @param column initial column
     * @param newRow row being moved to
     * @param newColumn column being moved to
     * @return Tue if the move is valid, otherwise false
     */
    public static boolean checkIfValid(Piece[][] board, int row, int  column, int newRow, int newColumn) {
        Movement m = new Movement(board);

        boolean[] movement = new boolean[4];
        for(int i=0; i<4; i++) movement[i] = false;

        if(board[row][column].getType().equals(Piece.Type.DRAGON)) {
            movement[1] = true;
        } else if(board[row][column].getType().equals(Piece.Type.GUARD)) {
            movement[0] = true;
            movement[2] = true;
        } else {
            movement[0] = true;
            movement[2] = true;
            movement[3] = true;
        }

        boolean valid;
        if(movement[0]) {
            valid = m.adjacent_motion(row, column, newRow, newColumn);
            if(valid) return true;
        }
        if(movement[1]) {
            valid = m.all_directions_motion(row, column, newRow, newColumn);
            if(valid) return true;
        }
        if(movement[2]) {
            valid = m.capture_dragon_motion(row, column, newRow, newColumn);
            if(valid) return true;
        }
        if(movement[3]) {
            valid = m.jump_guard_motion(row, column, newRow, newColumn);
            if(valid) return true;
        }
        return false;
    }

    /** 0
     *
     * @param row
     * @param column
     * @param newRow
     * @param newColumn
     * @return
     */
    public boolean adjacent_motion(int row, int  column, int newRow, int newColumn) {
        if( (Math.abs(row-newRow) + Math.abs(column-newColumn)) == 1 && board[newRow][newColumn] == null) {
            return true;
        }
        return false;
    }

    /** 1
     *
     * @param row
     * @param column
     * @param newRow
     * @param newColumn
     * @return
     */
    public boolean all_directions_motion(int row, int  column, int newRow, int newColumn) {
        if( board[newRow][newColumn] == null && (Math.abs(column-newColumn) <= 1) && (Math.abs(row-newRow) <= 1) ) {
            return true;
        }
        return false;
    }

    /** 3
     *
     * @param row
     * @param column
     * @param newRow
     * @param newColumn
     * @return
     */
    public boolean capture_dragon_motion(int row, int  column, int newRow, int newColumn) {
        // Check if Dragon can be moved into
        if(board[newRow][newColumn] != null){
            if(board[newRow][newColumn].getType()== Piece.Type.DRAGON) {

                int numberSurrounding = 0;

                if(newRow-1 >= 0) {
                    Piece piece =board[newRow-1][newColumn];
                    if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(newRow+1 <= 4) {
                    Piece piece =board[newRow+1][newColumn];
                    if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(newColumn-1 >= 0) {
                    Piece piece =board[newRow][newColumn-1];
                    if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(newColumn+1 <= 4) {
                    Piece piece =board[newRow][newColumn+1];
                    if(piece!=null && (piece.getType() == Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }

                if(numberSurrounding >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 4
     *
     * @param row
     * @param column
     * @param newRow
     * @param newColumn
     * @return
     */
    public boolean jump_guard_motion(int row, int  column, int newRow, int newColumn) {
        if(board[newRow][newColumn]!=null) return false;

        if( (row == newRow) && (Math.abs(column-newColumn) == 2) ) {
            int intermediateCol = (column+newColumn)/2;
            Piece piece = board[newRow][intermediateCol];
            if(piece != null && piece.getType()== Piece.Type.GUARD) {
                return true;
            }

        } else if( (column == newColumn) && (Math.abs(row-newRow) == 2) ) {
            int intermediateRow = (row+newRow)/2;
            Piece piece = board[intermediateRow][newColumn];
            if(piece != null && piece.getType()== Piece.Type.GUARD) {
                return true;
            }
        }
        return false;
    }
}
