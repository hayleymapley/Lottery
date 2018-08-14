package nz.ac.vuw.mapleyhayl.Lottery;

import java.util.*;

//This tool lets a user pick their numbers for the lottery.
//Each ticket has 4-8 numbers, between 1-99.
//The user will be able to:
// - get a random selection
// - replace a number
// - remove a number
// - insert a number
// - print out current numbers
// - check if a ticket is a winner (current winning criteria: 1 number in any position on ticket matches the draw)

public class Main {

	Ticket[] savedTickets = new Ticket[3]; //reminder: do not allow user to save ticket if it has too few/many numbers
	int [] currentNumbers = new int[8];
	Scanner in = new Scanner(System.in);

	public Main() {
		this.menu();
	}

	public void menu() {
		System.out.println("------ MENU -------");
		System.out.println("[1] Create new ticket");
		System.out.println("[2] Show current selection");
		System.out.println("[3] Replace number");
		System.out.println("[4] Insert number");
		System.out.println("[5] Remove number");
		System.out.println("[6] Save ticket");
		System.out.println("[7] View saved tickets");
		System.out.println("[8] Play ticket");
		System.out.println("\nPlease enter your selection: ");
		int ans = in.nextInt();
		switch (ans) {
		case 1 : 
			this.createTicketTemplate();
			break;
		case 2 :
			this.showCurrentNumbers();
			break;
		case 3 :
			this.replace();
			break;
		case 4 :
			this.insertAfter();
			break;
		case 5 :
			this.remove();
			break;
		case 6 :
			this.saveTicket();
			break;
		case 7 :
			this.showSavedTickets();
			break;
		case 8 :
			this.playTicket();
			break;
		}
		this.menu();
	}

	public void createTicketTemplate() {
		System.out.println("How many numbers do you wish to have? (Between 4-8 numbers allowed)");
		int ans = in.nextInt();
		while (!(ans >= 4 && ans <= 8)) {
			System.out.println("Please enter a valid amount of numbers: ");
			ans = in.nextInt();
		}
		for (int i=0; i<ans; i++) {
			int ticketNum = (int) Math.ceil(Math.random()*99);
			currentNumbers[i] = ticketNum;
		}
		this.showCurrentNumbers();
	}

	public void showCurrentNumbers() {
		for (int i=0; i<currentNumbers.length; i++) {
			if (currentNumbers[i] != 0) {
				System.out.print(currentNumbers[i]);
				System.out.print(" ");
			}
		}
		System.out.println("");
	}

	public void remove() {
		System.out.println("Please enter the index of the number you wish to remove: ");
		int ans = in.nextInt();
		int index = ans - 1;
		for (int i=index; i<currentNumbers.length-1; i++) {
			int next = currentNumbers[i+1];
			currentNumbers[i] = next;
		}
		if (currentNumbers[7] != 0) {
			currentNumbers[7] = 0;
		}
		this.showCurrentNumbers();
	}

	public void insertAfter() {
		System.out.println("Please enter the index of the number you wish to insert after: ");
		int ans = in.nextInt();
		int index = ans - 1;
		if (currentNumbers[7] != 0) {
			currentNumbers[7] = 0;
		}
		for (int i=currentNumbers.length-1; i>index; i--) {
			currentNumbers[i] = currentNumbers[i-1];
		}
		System.out.println("Please enter the number you wish to insert: ");
		ans = in.nextInt();
		while (ans < 1 || ans > 99) {
			System.out.println("Please enter the number you wish to add: (Must be between 1-99)");
			ans = in.nextInt();
		}
		currentNumbers[index+1] = ans;
		this.showCurrentNumbers();
	}

	public void replace() {
		System.out.println("Please enter the index of the number you wish to replace: ");
		int ans = in.nextInt();
		int index = ans - 1;
		System.out.println("Please enter the number you wish to add: ");
		ans = in.nextInt();
		while (ans < 1 || ans > 99) {
			System.out.println("Please enter the number you wish to add: (Must be between 1-99)");
			ans = in.nextInt();
		}
		currentNumbers[index] = ans;
		this.showCurrentNumbers();
	}

	public void showSavedTickets() {
		int i = 1;
		for (Ticket t : savedTickets) {
			if (t != null) {
				System.out.print("["+i+"] ");
				for (int j=0; j<t.getNumbers().length; j++) {
					if (t.getNumbers()[j] != 0) {
						System.out.print(t.getNumbers()[j]);
						System.out.print(" ");
					}
				}
				System.out.println("");
			} else {
				System.out.println("["+i+"] " + "Empty");
			}
			i++;
		}
		System.out.println("");
	}

	public void saveTicket() {
		this.showSavedTickets();
		int[] copyNumbers = new int[8];
		for (int i=0; i<copyNumbers.length; i++) {
			copyNumbers[i] = currentNumbers[i];
		}
		Ticket save = new Ticket(copyNumbers);
		System.out.println("Which ticket would you like to override?");
		int ans = in.nextInt();
		int index = ans - 1;
		savedTickets[index] = save;
	}

	public void playTicket() {
		//runs 50 draws
		int winCount = 0;
		System.out.println("Which ticket would you like to play?");
		int ans = in.nextInt();
		int index = ans - 1;
		while (ans < 1 || ans > 3) {
			System.out.println("Which ticket would you like to play? (Must be a saved ticket)");
			ans = in.nextInt();
		}
		for (int k=0; k<50; k++) {
			boolean win = false;
			Ticket t = new Ticket(currentNumbers);
			if (savedTickets[index] != null) {
				t = savedTickets[index];
			}
			int[] draw = new int[8];
			for (int i=0; i<8; i++) {
				int num = (int) Math.ceil(Math.random()*99);
				draw[i] = num;
			}
			for (int i=0; i<t.getNumbers().length; i++) {
				int num = t.getNumbers()[i];
				for (int j=0; j<draw.length; j++) {
					int compareNum = draw[index];
					if (compareNum == num) {
						win = true;
					}
				}
			}
			if (win == true) {
				winCount++;
			}
		}
		System.out.println("Wins: " + winCount);
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Lotto!\n");
		new Main();
	}

}
