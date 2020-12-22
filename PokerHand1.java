import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementing class for Poker Hand. Uses a {@code java.util.Set} as a model.
 *
 * @author Katie Heym
 *
 */
public final class PokerHand1 implements PokerHand {

    /**
     * Set of cards in the hand.
     */
    private Set<Card> hand;
    /**
     * Set of numbers in the hand.
     */
    private Set<Integer> numbers;
    /**
     * Name of the owner.
     */
    private String name;

    /**
     * Value of the ace.
     */
    private static final int ACE = 14;

    /**
     * Constructor.
     *
     * @param name
     *            the name to give this hand
     */
    public PokerHand1(String name) {
        this.hand = new HashSet<>();
        this.numbers = new HashSet<>();
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Iterator<Card> iterator() {
        return this.hand.iterator();
    }

    @Override
    public void add(Card card) {
        this.hand.add(card);
        this.numbers.add(card.getNumber());
    }

    @Override
    public void add(int number, char suit) {
        this.hand.add(new Card1(number, suit));
        this.numbers.add(number);
    }

    @Override
    public void combine(PokerHand source) {
        for (Card card : source) {
            this.add(card);
        }
        source.clear();
    }

    @Override
    public Card remove(Card card) {
        assert this.hand.remove(card) : "Error: Card not Found.";
        this.numbers.remove(card.getNumber());
        return card;
    }

    @Override
    public Card remove(int number, char suit) {
        assert this.hand
                .remove(new Card1(number, suit)) : "Error: Card not Found.";
        this.numbers.remove(number);
        return new Card1(number, suit);
    }

    @Override
    public PokerHand removeAll(int number) {
        this.numbers.remove(number);
        PokerHand removal = new PokerHand1(this.name);
        for (Card card : this) {
            if (card.getNumber() == number) {
                removal.add(card);
            }
        }
        for (Card card : removal) {
            this.hand.remove(card);
        }
        return removal;
    }

    @Override
    public int findSet(int setSize) {
        int found = -1;
        Set<Integer> numbersCopy = new HashSet<>(this.numbers);
        for (Integer number : numbersCopy) {
            PokerHand temp = this.removeAll(number);
            if (temp.size() >= setSize && number > found) {
                found = number;
            }
            this.combine(temp);
        }
        return found;
    }

    @Override
    public int size() {
        return this.hand.size();
    }

    @Override
    public void copyFrom(PokerHand source) {
        this.clear();
        for (Card card : source) {
            this.add(card);
        }
        this.name = source.getName();
    }

    @Override
    public PokerHand.Rank evaluate() {
        Rank ranking;
        assert this.size() == 5 : "Error : bad hand size.";
        boolean isFlush = true;
        char suit = 'z';
        for (Card card : this) {
            if (suit == 'z') {
                suit = card.getSuit();
            } else if (suit != card.getSuit()) {
                isFlush = false;
            }
        }
        boolean isStraight = this.numbers.size() == 5;
        if (isStraight) {
            int minNumber = ACE + 1;
            for (Integer number : this.numbers) {
                if (minNumber > number) {
                    minNumber = number;
                }
            }
            int counter = 0;
            while (isStraight && counter < this.size()) {
                isStraight = this.numbers.contains(minNumber + counter);
                counter++;
            }
            //Check for the wheel.
            if (!isStraight && this.numbers.contains(ACE)) {
                isStraight = true;
                counter = 0;
                while (isStraight && counter < this.size() - 1) {
                    isStraight = this.numbers.contains(2 + counter);
                    counter++;
                }
            }
        }
        boolean isFull = this.findSet(3) > 0;
        if (isFull) {
            PokerHand temp = this.removeAll(this.findSet(3));
            isFull = this.findSet(2) > 0;
            this.combine(temp);
        }
        boolean isTwoPair = this.findSet(2) > 0 && !isFull;
        if (isTwoPair) {
            PokerHand temp = this.removeAll(this.findSet(2));
            isTwoPair = this.findSet(2) > 0;
            this.combine(temp);
        }
        if (isFlush && isStraight) {
            ranking = Rank.STRAIGHT_FLUSH;
        } else if (this.findSet(4) > 0) {
            ranking = Rank.QUADS;
        } else if (isFull) {
            ranking = Rank.FULL_HOUSE;
        } else if (isFlush) {
            ranking = Rank.FLUSH;
        } else if (isStraight) {
            ranking = Rank.STRAIGHT;
        } else if (this.findSet(3) > 0) {
            ranking = Rank.TRIPS;
        } else if (isTwoPair) {
            ranking = Rank.TWO_PAIR;
        } else if (this.findSet(2) > 0) {
            ranking = Rank.ONE_PAIR;
        } else {
            ranking = Rank.HIGH;
        }
        return ranking;
    }

    @Override
    public void clear() {
        this.hand.clear();
        this.numbers.clear();
        this.name = "";
    }

}
