package nz.ac.vuw.mapleyhayl.Lottery;

public class Ticket {
	
	private int[] numbers;
	
	public Ticket(int[] numbers) {
		this.numbers = numbers;
	}
	
	public int[] getNumbers() {
		return numbers;
	}
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}
}
