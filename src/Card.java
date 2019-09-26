
public class Card {

	public final int orderVal;
	public final char value;
	public final char suit;

	public Card(char v, char s) {

		// associate an int value with each cardval to make comparing orders easier
		switch (v) {

		case 'A':
			orderVal = 1;
			break;

		case '2':
			orderVal = 2;
			break;

		case '3':
			orderVal = 3;
			break;

		case '4':
			orderVal = 4;
			break;

		case '5':
			orderVal = 5;
			break;

		case '6':
			orderVal = 6;
			break;

		case '7':
			orderVal = 7;
			break;

		case '8':
			orderVal = 8;
			break;

		case '9':
			orderVal = 9;
			break;

		case 'T':
			orderVal = 10;
			break;

		case 'J':
			orderVal = 11;
			break;

		case 'Q':
			orderVal = 12;
			break;

		case 'K':
			orderVal = 13;
			break;

		default:
			orderVal = 0;
		}

		value = v;
		suit = s;
	}

	public Card() {
		orderVal = 0;
		value = '0';
		suit = 'X';

	}

	public String toString() {
		return (value + " " + suit);
	}
}
