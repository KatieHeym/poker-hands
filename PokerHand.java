/**
 * Interface for a poker hand object.
 *
 * @author Katie Heym
 *
 */
public interface PokerHand extends Iterable<Card> {

    /**
     * Poker hand ranks.
     *
     * @author Katie Heym
     *
     */
    enum Rank {
        /**
         * Poker hand ranks.
         */
        HIGH(0), ONE_PAIR(1), TWO_PAIR(2), TRIPS(3), STRAIGHT(4), FLUSH(
                5), FULL_HOUSE(6), QUADS(7), STRAIGHT_FLUSH(8);

        /**
         * Internal ranking for comparison.
         */
        private final int ranking;

        /**
         * Constructor.
         *
         * @param ranking
         */
        Rank(int ranking) {
            this.ranking = ranking;
        }

        /**
         *
         * @return the rank of this
         */
        public int getRank() {
            return this.ranking;
        }

        /**
         * Returns a the difference between the rank of this and the rank of
         * rank.
         *
         * @param rank
         *            the Rank to compare against.
         * @return positive if this had higher rank, 0 if the ranks are equal,
         *         negative otherwise
         */
        public int compare(Rank rank) {
            return (this.getRank() - rank.getRank());
        }
    }

    /**
     * Adds card to this.
     *
     * @requires card is not already in this.
     * @param card
     */
    void add(Card card);

    /**
     * Adds a card with the given number and suit to this.
     *
     * @requires this has no corresponding card.
     * @param number
     * @param suit
     */
    void add(int number, char suit);

    /**
     * Adds all cards in source to this; clears source.
     *
     * @param source
     */
    void combine(PokerHand source);

    /**
     * Removes card from this.
     *
     * @requires card is in this.
     * @param card
     * @return the card removed
     */
    Card remove(Card card);

    /**
     * Removes the card in this with the given number and suit.
     *
     * @requires such a card is in this
     * @param number
     * @param suit
     * @return the card removed
     */
    Card remove(int number, char suit);

    /**
     * Removes all cards with the given number from this.
     *
     * @param number
     * @return a hand containing all cards removed.
     */
    PokerHand removeAll(int number);

    /**
     * Finds a set of same-number cards of the given size.
     *
     * @param setSize
     * @return the number of the highest set of cards of the given size in this;
     *         if no such set exists, returns -1
     */
    int findSet(int setSize);

    /**
     *
     * @return the number of cards in this
     */
    int size();

    /**
     * Copies the source into this.
     *
     * @param source
     */
    void copyFrom(PokerHand source);

    /**
     * @requires this.size()=5
     * @return the poker rank of the hand
     */
    Rank evaluate();

    /**
     * Clears this.
     */
    void clear();

    /**
     *
     * @return the name of the owner of the hand
     */
    String getName();
}
