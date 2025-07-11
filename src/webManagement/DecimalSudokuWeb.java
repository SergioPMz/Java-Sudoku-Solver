package webManagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import sudoku.DecimalSudoku;
import sudoku.Sudoku.IrresolvableSudokuException;

public class DecimalSudokuWeb extends SudokuWeb{

	@Override
	protected void getCells(){
		this.cellWebElements = driver.findElements(By.className("cellnormal"));
		
		int rowCol = (int) Math.sqrt(this.cellWebElements.size());
		Integer[][] sudoku = new Integer[rowCol][rowCol];
		
		for(int i = 0; i < this.cellWebElements.size(); i++) {
			Integer currentValue = null;
			if(!this.cellWebElements.get(i).getAttribute("innerHTML").contains("fixedcell")) {
				currentValue = null;
			} else {
				String innerHtml = cellWebElements.get(i).getAttribute("innerHTML");
				currentValue = Integer.valueOf(String.valueOf(innerHtml.charAt(innerHtml.indexOf("</span>")-1)));
			}
			int[] coordinates = positionToCoordinates(rowCol, i);
			sudoku[coordinates[0]][coordinates[1]] = currentValue;
		}
		
		this.currentSudoku = new DecimalSudoku(sudoku);
		this.cellsToFill = this.currentSudoku.emptyPositions();
	}
	
	@Override
	protected void solve() {
		try {
			this.currentSudoku.solve();
			Integer[][] solvedSudoku = this.currentSudoku.getSudoku();
			int rowCol = this.currentSudoku.getSudoku().length;
			WebElement body = driver.findElement(By.tagName("body"));
			for(int[] coordinates:this.cellsToFill) {
				WebElement currentElement = this.cellWebElements.get(coordinatesToPosition(rowCol, coordinates));
				int value = solvedSudoku[coordinates[0]][coordinates[1]];
				currentElement.click();
				body.sendKeys(String.valueOf(value));
			}
		} catch (IrresolvableSudokuException e) {
			System.out.println("A solution was not found");
		}
	}
	
	/** Method that closes the driver
	 * 
	 */
	public void closeDriver() {
		this.driver.close();
	}
}
