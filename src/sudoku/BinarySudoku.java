package sudoku;

import java.util.ArrayList;

public class BinarySudoku extends Sudoku{
	

	public BinarySudoku(Integer[][] sudoku) {
		super(sudoku);
	}

	/** Method that checks there are not more than half zeros or ones in the row and column of the specified position
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean halfCondition(int row, int column) {
		int rows = this.sudoku.length;
		int columns = this.sudoku[0].length;
		
		int countZeros = 0;
		int countOnes = 0;
		//Gets the amount of zeros and ones that there are in the row
		Integer[] currentRow = this.sudoku[row];
		for (Integer integer : currentRow) {
			if (integer == null) { continue;
			}else if(integer == 0) countZeros++; else countOnes++;
		}
		
		//If the amount of zeros or ones in one row is more than half of it then it is not valid
		if(countZeros > columns/2 || countOnes > columns/2) {
			return false;
		}
		
		//Then we do exactly the same but for columns
		countZeros = 0;
		countOnes = 0;
		for (int i = 0; i<rows; i++) {
			Integer currentValue = this.sudoku[i][column];
			if (currentValue == null) { continue;
			}else if (currentValue == 0) countZeros++; else countOnes++;
		}
		
		if(countZeros > rows/2 || countOnes > rows/2) {
			return false;
		}
		
		return true;
	}
	
	/** Method that checks there are not three positions with the same value on a row
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean threeEqualPositionsCondition(int row, int column) {
		
		int repetitionCount = 1;
		Integer lastValue = null;
		Integer[] currentRow = this.sudoku[row];
		for (Integer currentValue : currentRow) {
			if(currentValue == null || currentValue!=lastValue) {
				repetitionCount = 1;	
			}else{
				repetitionCount++;
			}
			lastValue = currentValue;
			if(repetitionCount == 3) return false; //If a value that is not null is repeated three times then it returns false
		}
		
		//We do the same for in the column
		repetitionCount = 1;
		lastValue = null;
		for (int i = 0; i<sudoku.length; i++) {
			Integer currentValue = this.sudoku[i][column];
			if(currentValue == null || currentValue!=lastValue) {
				repetitionCount = 1;	
			}else{
				repetitionCount++;
			}
			lastValue = currentValue;
			if(repetitionCount == 3) return false;
		}
		
		return true;
	}
	

	/** Method that checks the sudoku does not have duplicated rows or columns
	 * 
	 * @return
	 */
	private boolean condicionRowsColsIguales() {
		ArrayList<Integer[]> completedRows = new ArrayList<Integer[]>();
		
		//It checks if two rows are the same
		rowLoop:
		for (int i = 0; i<this.sudoku.length; i++) {
			Integer[] row = new Integer[sudoku[0].length];
			for (int j = 0; j<this.sudoku[0].length; j++) {
				Integer currentValue = sudoku[i][j];
				if (currentValue == null) {
					continue rowLoop; //If a value is null then the row is not complete, so it cannot be checked and it continues to the next
				}
				row[j] = currentValue;
			}
			if (completedRows.contains(row)) {
				return false; //Two rows are equal
			}else {
				completedRows.add(row);
			}
		}
		
		//It checks if two columns are the same
		columnLoop:
		for (int j = 0; j<this.sudoku[0].length; j++) {
			Integer[] column = new Integer[sudoku.length];
			for (int i = 0; i<this.sudoku.length; i++) {
				Integer currentValue = sudoku[i][j];
				if (currentValue == null) {
					continue columnLoop; //If a value is null then the column is not complete, so it cannot be checked and it continues to the next
				}
				column[j] = currentValue;
			}
			if (completedRows.contains(column)) {
				return false; //Two columns are equal
			}else {
				completedRows.add(column);
			}
		}
		
		return true;
	}

	@Override
	protected boolean isValid(int row, int column) {
		return (halfCondition(row, column)&&threeEqualPositionsCondition(row, column)&&condicionRowsColsIguales());
	}

	@Override
	public Integer[] getPossibleValues() {
		return new Integer[] {0,1};
	}
}
