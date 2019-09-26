import java.util.Hashtable;
import java.util.Arrays;

public class Solitaire {

	private int k; // number of decks
	private int n; // number of starting stacks
	private int m; // number of cards/stack
	private int p; // number of final piles

	private CardDeck cards;
	private Card[][] sStacks;// Card arrays holding all the starting stacks
	private Card[] fStack;// Empty array will hold top card in finish piles
	private int[] sStackTop;// array that will hold indexes of top for starting stacks

	private Hashtable<String, Boolean> check = new Hashtable<String, Boolean>();;// Hash map used to reduce redundant
																					// calculations

	Solitaire(int K, int N, int M, int P) {
		k = K; // decks
		n = N; // starting stacks
		m = M; // number of cards per stack
		p = P; // number of finish piles

		cards = new CardDeck(k);// Returns k decks shuffled together
		sStacks = new Card[n][m];
		fStack = new Card[p];
		sStackTop = new int[n];// initially all are zero (top of deck)

		int i = 0;
		for (int l = 0; l < m; l++) {
			for (int j = 0; j < n; j++) {
				sStacks[j][l] = cards.getCard(i);
				i++;
			}
		}

		for (int l = 0; l < p; l++)// sets initial fStack cards to be "blank"
			fStack[l] = new Card();
	}

	public int autoStart() { // setup for autoStart Function

		int moves = 0;
		moves = autoStart(moves, this.sStacks, this.fStack, this.sStackTop);
		return moves;
	}

	private int autoStart(int moves, Card[][] start, Card[] end, int[] startTop) {

		if (check.containsKey(Arrays.toString(startTop)))// don't repeat calculations
			return 0;

		Card[] tempEnd;
		int[] tempTops;

		int[] stackMoves = new int[n];

		for (int i = 0; i < n; i++) {

			if (startTop[i] != m) {// check that start pile is not at its end

				for (int j = 0; j < p; j++) {

					if (((end[j].orderVal == start[i][startTop[i]].orderVal - 1)
							&& (end[j].suit == start[i][startTop[i]].suit))
							| ((end[j].orderVal == 0) && (start[i][startTop[i]].orderVal == 1))) {
						tempEnd = Arrays.copyOf(end, end.length); // Creates deep copies
						tempTops = Arrays.copyOf(startTop, startTop.length);
						tempEnd[j] = start[i][startTop[i]];
						tempTops[i]++;
						stackMoves[i] = autoStart(moves + 1, start, tempEnd, tempTops);
						break;
					}

					else if (j == p - 1 || end[j].orderVal == 0) {// if all filled end stacks have been gone through
						stackMoves[i] = moves;// can't make anymore moves
						break;
					}
				}
			}
		}

		for (int k = 0; k < n; k++) {// Find max value

			if (stackMoves[k] > moves)
				moves = stackMoves[k];
		}

		check.put(Arrays.toString(startTop), true);// indicates that specific top cards have been looked at already
		return moves;
	}

	public static void autoStartToVal(int val, int k, int m, int n, int p) {// runs until 'val' moves are made
		int result = 0;
		int count = 0;
		int[] frequency = new int[val + 1];

		while (result < val) {
			Solitaire test = new Solitaire(k, m, n, p);
			result = test.autoStart();
			count++;
			if (result <= val)
				frequency[result]++;
		}
		System.out.println("\nTook " + count + " Tries to reach " + val + "\n");
		for (int i = 0; i < val + 1; i++)
			System.out.println(frequency[i] + " \t occurences of " + i);

		System.out.println("\nHistogram: (each star represents %0.1)");
		histogram(frequency, count);
	}

	private static void histogram(int[] values, int total) {// creates histogram with asterisks each
		// representing %0.1 of the total results
		int counter;
		double decimal;

		for (int i = 0; i < values.length; i++) {
			decimal = (double) (values[i] * 1000) / total;
			counter = (int) Math.floor(decimal);

			System.out.print(i + ": ");
			while (counter > 0) {
				System.out.print("*");
				counter--;
			}
			System.out.println();
		}
	}

	public static void testRuns(double runs, int k, int m, int n, int p) {// Executes autuoStart 'runs' number of times
		int result;
		double[] frequency = new double[(n * m) + 1];// in case all cards get moved

		for (double i = 0; i < runs; i++) {
			Solitaire test = new Solitaire(k, m, n, p);
			result = test.autoStart();
			frequency[result]++;
		}

		System.out.println(runs + " random autoStarts completed \n");
		for (int i = 0; i < frequency.length; i++)
			System.out.println(frequency[i] + " \t occurences of " + i);
	}

	public static void timeTest() {

		// To test without Hash table checking comment out lines: 16,53,54 and 93 before
		// running

		Solitaire test = new Solitaire(2, 10, 4, 8);

		// Test case that can do 13 moves
		test.sStacks[0][0] = new Card('7', 'H');
		test.sStacks[0][1] = new Card('2', 'S');
		test.sStacks[0][2] = new Card('9', 'S');
		test.sStacks[0][3] = new Card('T', 'C');

		test.sStacks[1][0] = new Card('A', 'H');
		test.sStacks[1][1] = new Card('3', 'C');
		test.sStacks[1][2] = new Card('4', 'D');
		test.sStacks[1][3] = new Card('K', 'S');

		test.sStacks[2][0] = new Card('2', 'C');
		test.sStacks[2][1] = new Card('Q', 'S');
		test.sStacks[2][2] = new Card('6', 'D');
		test.sStacks[2][3] = new Card('6', 'C');

		test.sStacks[3][0] = new Card('7', 'C');
		test.sStacks[3][1] = new Card('Q', 'C');
		test.sStacks[3][2] = new Card('8', 'H');
		test.sStacks[3][3] = new Card('T', 'S');

		test.sStacks[4][0] = new Card('A', 'D');
		test.sStacks[4][1] = new Card('9', 'C');
		test.sStacks[4][2] = new Card('5', 'D');
		test.sStacks[4][3] = new Card('7', 'D');

		test.sStacks[5][0] = new Card('4', 'C');
		test.sStacks[5][1] = new Card('3', 'S');
		test.sStacks[5][2] = new Card('5', 'S');
		test.sStacks[5][3] = new Card('8', 'H');

		test.sStacks[6][0] = new Card('2', 'H');
		test.sStacks[6][1] = new Card('2', 'C');
		test.sStacks[6][2] = new Card('7', 'C');
		test.sStacks[6][3] = new Card('4', 'D');

		test.sStacks[7][0] = new Card('2', 'D');
		test.sStacks[7][1] = new Card('A', 'C');
		test.sStacks[7][2] = new Card('A', 'D');
		test.sStacks[7][3] = new Card('K', 'C');

		test.sStacks[8][0] = new Card('2', 'D');
		test.sStacks[8][1] = new Card('A', 'H');
		test.sStacks[8][2] = new Card('3', 'D');
		test.sStacks[8][3] = new Card('T', 'H');

		test.sStacks[9][0] = new Card('6', 'S');
		test.sStacks[9][1] = new Card('5', 'D');
		test.sStacks[9][2] = new Card('6', 'H');
		test.sStacks[9][3] = new Card('7', 'D');

		long startTime = System.nanoTime();
		int moves = test.autoStart();
		long endTime = System.nanoTime();
		long convert = 1000;
		long totalTime = ((endTime - startTime) / convert);// convert total time to microseconds

		System.out.println("Time to " + moves + " moves: " + totalTime + " microseconds");// should be 13 moves
	}

	public static void main(String[] args) {

		autoStartToVal(15, 2, 10, 4, 8);

		// Enter in following order:
		// max value to reach
		// number of decks
		// number of starting stacks
		// number of cards in each starting stack
		// number of finishing piles

		//testRuns(1E7, 2, 10, 4, 8); // same inputs as above, except first is a double
		// for how many trials to run

		//timeTest();
	}
}
