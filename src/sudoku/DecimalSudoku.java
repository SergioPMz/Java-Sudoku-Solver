package sudoku;


public class DecimalSudoku  extends Sudoku{
	
	public DecimalSudoku(Integer[][] sudoku) {
		super(sudoku);
	}
	
	@Override
	public boolean isValid(int row, int column) {
		int rowCol = 9; // All decimal sudokus have rows and columns of length 9
		int value = sudoku[row][column];
		
		// Checks the number is not repeated in the 3x3
		int[] primeraPosicionCuadrante = new int[] {(row - row%3), (column - column%3)};
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				int [] currentPosition = new int[] {primeraPosicionCuadrante[0] + i, primeraPosicionCuadrante[1] + j};
				if(!(currentPosition[0] == row && currentPosition[1] == column)) {
					if(sudoku[currentPosition[0]][currentPosition[1]] != null && sudoku[currentPosition[0]][currentPosition[1]] == value) {
						return false;
					}
				}
			}
		}
		
		// Checks the number is not repeated in the same row or column
		for(int i = 0; i < rowCol; i++) {
			if(i != column) {
				if(sudoku[row][i] !=null && sudoku[row][i] == value) {
					return false;
				}
			}
			if((i != row)) {
				if(sudoku[i][column]!=null && sudoku[i][column] == value) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Integer[] getPossibleValues() {
		return new Integer[] {1,2,3,4,5,6,7,8,9};
	}
}
