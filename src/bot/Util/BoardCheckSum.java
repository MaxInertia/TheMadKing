package bot.Util;

import game.history.Move;
import game.pieces.Piece;

public class BoardCheckSum implements CheckSum {

	
	/* Demonstration table for checksum algorithm (31C31R).
	 * 1	|	X	X	X	X	X	|
	 * 2	|	X	X	X	X	X	|
	 * 4	|	X	X	X	X	X	|
	 * 8	|	X	X	X	X	X	|
	 * 16	|	X	X	X	X	X	|
	 * 			1	2	4	8	16
	 * 
	 * To determine row or column value, add the powers of two.
	 * Place a C after column value, and r after row value.
	 * 
	 * To make the checksum more "aware", add a D and G value.
	 * 2C31R2D4G, means 2 Dragons, 4 Guards
	 * This isn't possible as only 5 pieces should be on the board.
	 * This can allow for "internal" checking of the checksum.
	 * 
	 * Hey bro, I heard you like checksums, so I put a checksum in your checksum,
	 * so you can checksum while you checksum!
	 */
	
	/*
	 * 2C31R -> 2C27R -> 4C27R
	 * 
	 * 				2C31R
	 * 1	|		X				|
	 * 2	|		X				|
	 * 4	|		X				|
	 * 8	|		X				|
	 * 16	|		X				|
	 * 			1	2	4	8	16
	 * 
	 * 				2C27R
	 * 1	|		X				|
	 * 2	|		X				|
	 * 4	|						|
	 * 8	|		X				|
	 * 16	|		X				|
	 * 			1	2	4	8	16
	 * 
	 * 				4C27R
	 * 1	|			X			|
	 * 2	|			X			|
	 * 4	|						|
	 * 8	|			X			|
	 * 16	|			X			|
	 * 			1	2	4	8	16
	 */
	

	@Override
	public String getCheckSum(Piece[][] board) {
		// In case we are given a board without a known checksum,
		// calculate it by going though each tile.
		
		int boardRows = 5;
		int boardColumns = 5;
		
		int columnValue = 0;
		int rowValue = 0;
		
		String column = "C";
		String row = "R";
		
		boolean[] rowCheck = new boolean[boardRows];
		boolean[] columnCheck = new boolean[boardColumns];
		for(int i=0; i<boardRows; i++) rowCheck[i] = false;
		for(int i=0; i<boardColumns; i++) columnCheck[i] = false;
		
		// Due to conventions setup, checksum will now be R then C 1:29AM 2017-03-01
		
		
		// Hard coded values
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardColumns; j++) {
				// Read row by row.
				System.out.println("[BoardCheckSum.java]\tReading row "+i);
				
				if (board[i][j] != null) {
					// Was something.
					// As we aren't in the extended version, ignore the type.
					System.out.println("[BoardCheckSum.java]\tDetected something in row "+i);
					
					if (!rowCheck[i]) {
						// Count the value
						rowCheck[i] = true;
						rowValue += (int) Math.pow(2, i);
						System.out.println("[BoardCheckSum.java]\tRowValue is now "+rowValue);
						break;
					}
				}
			}
		}
		System.out.println("[BoardCheckSum.java]\tFINAL RowValue is now "+rowValue);
		
		// Hard coded values
		for (int i = 0; i < boardColumns; i++) {
			for (int j = 0; j < boardRows; j++) {
				// Read column by column
				// Read row by row.
				System.out.println("[BoardCheckSum.java]\tReading column "+i);
				
				if (board[i][j] != null) {
					// Was something.
					// As we aren't in the extended version, ignore the type.
					System.out.println("[BoardCheckSum.java]\tDetected something in column "+i);
					
					if (!columnCheck[i]) {
						// Count the value
						columnCheck[i] = true;
						columnValue += (int) Math.pow(2, i);
						System.out.println("[BoardCheckSum.java]\tColumnValue is now "+columnValue);
						break;
					}
				}
			}
		}
		System.out.println("[BoardCheckSum.java]\tFINAL ColumnValue is now "+columnValue);		
        
		// Mash everything together as a string and return it.
		return rowValue+column+columnValue+row;
	}

	@Override
	public Move calculateMove(String checkSum, Piece[][] board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtCheckSum(Piece[][] board) {
		// In case we are given a board without a known checksum,
		// calculate it by going though each tile.
		
		int boardRows = 5;
		int boardColumns = 5;
		
		int columnValue = 0;
		int rowValue = 0;
		
		String column = "C";
		String row = "R";
		String guard = "G";
		String dragon = "D";
		
		// Due to conventions setup, checksum will now be R then C 1:29AM 2017-03-01

		boolean[] rowCheck = new boolean[boardRows];
		boolean[] columnCheck = new boolean[boardColumns];
		for(int i=0; i<boardRows; i++) rowCheck[i] = false;
		for(int i=0; i<boardColumns; i++) columnCheck[i] = false;
		
		// Hard coded values
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardColumns; j++) {
				// Read row by row.
				System.out.println("[BoardCheckSum.java]\tReading row "+i);
				
				if (board[i][j] == null) {
					// Go on to the next thing.
				} else {
					// Wasn't something.
					// As we aren't in the extended version, ignore it.
					System.out.println("[BoardCheckSum.java]\tDetected something in row "+i);
					rowValue += (int) Math.pow(2, i);
					System.out.println("[BoardCheckSum.java]\tRowValue is now "+rowValue);
					break;
				}
			}
		}
		System.out.println("[BoardCheckSum.java]\tFINAL RowValue is now "+rowValue);
		
		// Hard coded values
		for (int i = 0; i < boardColumns; i++) {
			for (int j = 0; j < boardRows; j++) {
				// Read column by column
				System.out.println("[BoardCheckSum.java]\tReading column "+i);
				
				if (board[i][j] == null) {
					// Go on to the next thing.
				} else {
					// Wasn't something.
					// As we aren't in the extended version, ignore it.
					System.out.println("[BoardCheckSum.java]\tDetected something in column "+i);
					columnValue += (int) Math.pow(2, i);
					System.out.println("[BoardCheckSum.java]\tColumnValue is now "+rowValue);
					break;
				}
			}
		}
		System.out.println("[BoardCheckSum.java]\tFINAL ColumnValue is now "+rowValue);		
        
		return rowValue+column+columnValue+row;
	}
}
