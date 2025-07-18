package main;
import java.util.Scanner;

import org.openqa.selenium.ElementClickInterceptedException;

import webManagement.BinarySudokuWeb;
import webManagement.DecimalSudokuWeb;
import webManagement.SudokuWeb;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		final String URLEXAMPLES = "https://es.puzzle-binairo.com/binairo-8x8-difficult" + "\n"
				+ "https://www.livesudoku.com/es/sudoku/evil/";
		
		final String SEPARATOR = "-----------------------------------------------------------------";
		
		SudokuWeb sudokuWeb = null;
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Type the url of the sudoku you want to solve, \"examples\" if you want to see url examples "
					+ "or \"exit\" if you want to close the program");
			
			String input = sc.nextLine().strip().toLowerCase();
			
			if(input.equals("examples")) {
				System.out.println(SEPARATOR + "\n" + URLEXAMPLES + "\n" + SEPARATOR);
				continue;
			};
			
			if(input.equals("exit")) break; //Exits the loop finalizing the program
			
			if(input.contains("livesudoku")) {
				sudokuWeb = new DecimalSudokuWeb();
			} else if(input.contains("puzzle-binairo")){
				sudokuWeb = new BinarySudokuWeb();
			}else {
				System.out.println("Url not valid");
				continue;
			}
			sudokuWeb.get(input);
			System.out.println("Accept the cookies and then press enter in the console");
			sc.nextLine();
			System.out.println("Solving.....");
			try {
				sudokuWeb.solveWeb();
				System.out.println("Sudoku solved! Press enter in the console to close the web");
			} catch (ElementClickInterceptedException e) {
				System.out.println("ERROR: The cookies were not accepted, press enter to continue");
			}
			sc.nextLine();
			sudokuWeb.closeDriver();
		}
		System.out.println("Program closed successfully");
		sc.close();
	}	
}
