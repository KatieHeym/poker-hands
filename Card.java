/**
 * Interface for a playing card object.
 *
 * @author Katie Heym
 *
 */
public interface Card {
    /**
     *
     * @return An int from 2-14 inclusive. Ace is valued 14.
     */
    int getNumber();

    /**
     *
     * @return The first letter of its suit, capitalized. One of {C,D,H,S}
     */
    char getSuit();
}
