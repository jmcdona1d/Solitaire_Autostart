
public class CardDeck {

	// Two arrays that hold all possible card value and suit values - 52 possible
	// combinations
	public final char[] cardVals = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K' };
	public final char[] cardSuits = { 'H', 'S', 'C', 'D' };

	private int num;
	private Card[] cards;

	CardDeck(int n) {

		num = n;
		cards = new Card[52 * num];

		boolean[] check = new boolean[52 * num];
		for (int i = 0; i < num; i++) {
			for (int v = 0; v < 13; v++) {
				for (int s = 0; s < 4; s++) {

					int index = (int) Math.floor(Math.random() * (52 * num));

					if (!check[index]) {
						cards[index] = new Card(cardVals[v], cardSuits[s]);
						check[index] = true;
					}

					else {
						s--;
					}
				}
			}
		}
	}

	public Card getCard(int i) {
		return cards[i];
	}
}