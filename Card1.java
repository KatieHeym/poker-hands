/**
 * An implementing class for Card. Models a playing card as a duple of int and
 * char.
 *
 * @author Katie Heym
 *
 */
public final class Card1 implements Card {

    /**
     * The number attribute.
     */
    private final int number;
    /**
     * The suit attribute.
     */
    private final char suit;

    /**
     * Generates a card with the given number and suit.
     *
     * @param number
     *            the number of the card; jack through ace high are counted
     *            11-14.
     * @param suit
     *            a char in the set {C,D,H,S}
     */
    public Card1(int number, char suit) {
        this.number = number;
        this.suit = suit;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public char getSuit() {
        return this.suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card card = (Card) obj;
        return this.getNumber() == card.getNumber()
                && this.getSuit() == card.getSuit();
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
