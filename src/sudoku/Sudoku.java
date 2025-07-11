package sudoku;

import java.util.ArrayList;

public abstract class Sudoku {
	protected Integer[][] sudoku;
	
	public Sudoku(Integer[][] sudoku) {
		super();
		this.sudoku = sudoku;
	}

	/** Method that checks that position specified in the parameters does break any rule of a valid sudoku
	 * 
	 * @param row The row of the position to check
	 * @param column The column of the position to check
	 * @return true if it is valid and false if not
	 */
	protected abstract boolean isValid(int row, int column);
	
	/** Method that checks that position specified in the parameters does break any rule of a valid sudoku
	 * 
	 * @param position an int[2] with the row in the fist positions and the column in the second of the cell to check
	 * @return true if it is valid and false if not
	 */
	private boolean isValid(int[] position) {
		return isValid(position[0], position[1]);
	};
	
	/** Method that returns the possible values a position this sudoku can have in a given cell
	 * 
	 * @return An Integer[] with the values
	 */
	public abstract Integer[] getPossibleValues();
	
	/** Method that checks if a position has a next value available or not
	 * 
	 * @param row The row the position is in
	 * @param column The column the position is in
	 * @return true if it has a next value in possibleValues and false if not
	 */
	private boolean hasSiblings(int row, int column) {
		Integer[] possibleValues = getPossibleValues();
		return this.sudoku[row][column] != possibleValues[possibleValues.length-1]; //If the position does not have the last value possible then it has siblings
	}
	
	/** Method that checks if a position has a next value available or not
	 * 
	 * @param position an int[2] with the row in the fist positions and the column in the second of the cell to check
	 * @return true if it has a next value in possibleValues and false if not
	 */
	private boolean hasSiblings(int[] position) {
		return hasSiblings(position[0], position[1]);
	}
	
	/** It changes the value of the cell to the next one of the accepted values
	 * 
	 * @param row The row the position is in
	 * @param column The column the position is in
	 */
	private void generator(int row, int column) {
		Integer[] possibleValues = getPossibleValues();
		Integer value = this.sudoku[row][column];
		if (value == null) {
			this.sudoku[row][column] = possibleValues[0]; //If the cell is empty it puts the first value
		}else {
			for (int i = 0; i<possibleValues.length-1; i++) {
				if (value == possibleValues[i]) {
					sudoku[row][column] = possibleValues[i+1]; //It changes the value of the cell to the next one
					break;
				}
			}
		}
	}
	
	/** It changes the value of the cell to the next one of the accepted values
	 * 
	 * @param position an int[2] with the row in the fist positions and the column in the second of the cell to check
	 */
	private void generator(int[] position) {
		generator(position[0], position[1]);
	}
	
	/** Method that gets the empty positions of the sudoku
	 * 
	 * @return An ArrayList<int[]> with all the empty positions
	 */
	public ArrayList<int[]> emptyPositions() {
		ArrayList<int[]> emptyPositions = new ArrayList<int[]>();
		int row = this.sudoku.length;
		int column = this.sudoku[0].length;
		//If the cell is null then saves an int[2] in emptyPositions, first value being the row and second the column of the empty position
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				if(this.sudoku[i][j] == null) {
					emptyPositions.add(new int[]{i,j});
				}
			}
		}
		return emptyPositions;
	}
	
	/** Method that solves the sudoku with a backtracking algorithm
	 * @throws IrresolvableSudokuException 
	 * 
	 */
	public void solve() throws IrresolvableSudokuException {
		boolean sudokuSolved = false;
		ArrayList<int[]> emptyPossitions = emptyPositions();
		int level = 0;
		while(level > -1) {
			int[] currentPosicion = emptyPossitions.get(level);
			if(hasSiblings(currentPosicion)) {
				generator(currentPosicion);
				if(isValid(currentPosicion)) {
					if(level < emptyPossitions.size()-1) {
						level++;
					}else {
						sudokuSolved = true;
						break;
					}
				}
			}else {
				sudoku[currentPosicion[0]][currentPosicion[1]] = null;
				level--;
			}
		}
		if (!sudokuSolved) throw new IrresolvableSudokuException();
	};
	
	public Integer[][] getSudoku() {
		return sudoku;
	}
	
	public void setSudoku(Integer[][] sudoku) {
		this.sudoku = sudoku;
	}
	
	/** Exception to be thrown when a solution could not be found for a sudoku
	 * 
	 */
	public class IrresolvableSudokuException extends Exception{
		private static final long serialVersionUID = 1L;
		
		public IrresolvableSudokuException() {
			super("The sudoku does not have a solution");
		}
		
		public IrresolvableSudokuException(String message) {
			super(message);
		}
	}

	@Override
	public String toString() {
		int row = this.sudoku.length;
		int column = this.sudoku[0].length;
		String sudokuString = "";
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				if(sudoku[i][j] != null) sudokuString += sudoku[i][j] + " ";
				else sudokuString += "x ";
			}
			sudokuString = sudokuString.strip() + "\n";
		}
		return sudokuString.strip();
	}
}
