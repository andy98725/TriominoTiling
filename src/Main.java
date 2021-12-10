import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);

		// Get board size
		int n = -1;
		System.out.println("Enter a 2^n size from 1-10:");
		do {
			try {
				n = reader.nextInt();
				if (n <= 0)
					System.out.println("Please enter a positive value:");
				if (n > 10)
					System.out.println("Please enter a value below 11:");
			} catch (Exception e) {
				System.out.println("Please enter a number:");
			}
			// Clear buffer
			reader.nextLine();
		} while (n <= 0 || n > 10);
		final int boardSize = (int) Math.pow(2, n);

		// Get coordinates
		int x = -1, y = -1;
		System.out.println("Enter x coord for the missing tile from 0-" + Integer.toString(boardSize - 1) + ":");
		do {
			try {
				x = reader.nextInt();
				if (x < 0)
					System.out.println("Please enter a positive value:");
				if (x >= boardSize)
					System.out.println("Please enter a value below " + Integer.toString(boardSize) + ":");
			} catch (Exception e) {
				System.out.println("Please enter a valid number:");
			}
			reader.nextLine();
		} while (x < 0 || x >= boardSize);

		System.out.println("Enter y coord for the missing tile from 0-" + Integer.toString(boardSize - 1) + ":");
		do {
			try {
				y = reader.nextInt();
				if (y < 0)
					System.out.println("Please enter a positive value:");
				if (y >= boardSize)
					System.out.println("Please enter a value below " + Integer.toString(boardSize) + ":");

			} catch (Exception e) {
				System.out.println("Please enter a valid number:");
			}
			reader.nextLine();
		} while (y < 0 || y >= boardSize);

		reader.close();
		new Application(boardSize, x, y);
	}
}
