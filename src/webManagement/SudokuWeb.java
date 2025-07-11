package webManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import sudoku.Sudoku;

public abstract class SudokuWeb {
	
	protected WebDriver driver;
	protected Sudoku currentSudoku;
	protected List<WebElement> cellWebElements;
	protected ArrayList<int[]> cellsToFill;
	
	public SudokuWeb() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
		this.driver = new ChromeDriver(chromeOptions);
	}
	
	
	/** Method that gets the driver to the specified url
	 * 
	 * @param url The url to get to
	 */
	public void get(String url) {
		driver.get(url);
	}
	
	/** Method that extracts the cells data from the page
	 * 
	 */
	protected abstract void getCells();
	
	/** Method that solves the sudoku and puts the result in the web
	 * 
	 */
	protected abstract void solve();

	/** Method that gets the driver to the web where the binary sudoku is and solves it
	 * 
	 * @param url The url where the binary sudoku is
	 */
	public void solveWeb() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getCells();
		this.solve();
	}
	
	/** Method that closes the driver
	 * 
	 */
	public void closeDriver() {
		this.driver.close();
		this.driver.quit();
	}
	
	/** The web internally saves the position as a single number, this method is to translate that to coordinates with its row and column
	 * 
	 * @param rowAmount
	 * @param position
	 * @return An int[2] with the row in the first position and the column in the second
	 */
	public static int[] positionToCoordinates(int rowAmount, int position) {
		int row = 0;
		int column = 0;
		
		do {
			if(position > rowAmount - 1) {
				row++;
				position -= rowAmount;
			}
			if(position <= rowAmount - 1) 
				column = position;
			
		}while(position > rowAmount - 1);
		
		return  new int[] {row, column};
	}
	
	/** The web internally saves the position as a single number, this method is to get the number position that corresponds to the coordinates given
	 * 
	 * @param rowAmount
	 * @param coordinates
	 * @return The number equivalent to the coordinates given
	 */
	public static int coordinatesToPosition(int rowAmount, int[] coordinates) {
		int position;
		position = rowAmount*coordinates[0] + coordinates[1];
		return position;
	}
}
