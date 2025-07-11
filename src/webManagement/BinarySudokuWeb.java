package webManagement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import sudoku.BinarySudoku;
import sudoku.Sudoku.IrresolvableSudokuException;

public class BinarySudokuWeb extends SudokuWeb{

	@Override
	protected void getCells(){
		List<WebElement> elements=  driver.findElement(By.className("board-back")).findElements(By.tagName("div"));

		int rowCol = (int) Math.sqrt(elements.size());
		Integer[][] sudoku = new Integer[rowCol][rowCol];
		
		for(int i = 0; i < elements.size(); i++) {
			Integer currentValue = null;
			if(elements.get(i).getAttribute("outerHTML").contains("cell selectable cell-off")) {
				currentValue = null;
			} else if(elements.get(i).getAttribute("outerHTML").contains("task-cell cell-0")) {
				currentValue = 0;
			} else if(elements.get(i).getAttribute("outerHTML").contains("task-cell cell-1")) {
				currentValue = 1;
			}
			int[] coordinates = positionToCoordinates(rowCol, i);
			sudoku[coordinates[0]][coordinates[1]] = currentValue;
		}
		this.cellWebElements = elements;
		this.currentSudoku = new BinarySudoku(sudoku);
		this.cellsToFill = currentSudoku.emptyPositions();
	}
	
	@Override
	protected void solve(){
		try {
			this.currentSudoku.solve();
			Integer[][] solvedSudoku = this.currentSudoku.getSudoku();
			
			int rowCol = solvedSudoku.length;
			
			for(int[] coordinates:this.cellsToFill) {
				WebElement currentElement = this.cellWebElements.get(coordinatesToPosition(rowCol, coordinates));
				int value = solvedSudoku[coordinates[0]][coordinates[1]];
				
				if(value == 0) {
					currentElement.click();
					currentElement.click();
				} else {
					currentElement.click();
				}
			}
		} catch (IrresolvableSudokuException e) {
			System.out.println("A solution was not found");
		}
	}
}
