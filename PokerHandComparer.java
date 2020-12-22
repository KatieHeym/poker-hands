import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for comparing two poker hands.
 *
 * @author Katie
 *
 */
public final class PokerHandComparer {

    /**
     * Private constructor for this utility class.
     */
    private PokerHandComparer() {
    }

    /**
     * Processes the input. Constructs the hands and compares them.
     *
     * @param s
     * @return the result of the hand comparison
     */
    private static String processInput(String s) {
        Set<Character> separators = new HashSet<>();
        separators.add(' ');
        separators.add(':');
        int pos = 0;
        String result = nextWordOrSeparator(s, pos, separators) + " ";
        pos += nextWordOrSeparator(s, pos, separators).length();
        pos += nextWordOrSeparator(s, pos, separators).length();
        PokerHand player1 = new PokerHand1(
                nextWordOrSeparator(s, pos, separators));
        pos += player1.getName().length();
        pos += nextWordOrSeparator(s, pos, separators).length();
        while (player1.size() < 5) {
            String newCard = nextWordOrSeparator(s, pos, separators);
            pos += nextWordOrSeparator(s, pos, separators).length();
            int number = -1;
            if (Character.isDigit(newCard.charAt(0))) {
                number = Character.getNumericValue(newCard.charAt(0));
            } else if (newCard.charAt(0) == 'T') {
                number = 10;
            } else if (newCard.charAt(0) == 'J') {
                number = 11;
            } else if (newCard.charAt(0) == 'Q') {
                number = 12;
            } else if (newCard.charAt(0) == 'K') {
                number = 13;
            } else if (newCard.charAt(0) == 'A') {
                number = 14;
            } else {
                assert false : "Input Error";
            }
            player1.add(number, newCard.charAt(1));
            pos += nextWordOrSeparator(s, pos, separators).length();
        }

        PokerHand player2 = new PokerHand1(
                nextWordOrSeparator(s, pos, separators));
        pos += player2.getName().length();
        pos += nextWordOrSeparator(s, pos, separators).length();
        while (player2.size() < 5) {
            String newCard = nextWordOrSeparator(s, pos, separators);
            pos += nextWordOrSeparator(s, pos, separators).length();
            int number = -1;
            if (Character.isDigit(newCard.charAt(0))) {
                number = Character.getNumericValue(newCard.charAt(0));
            } else if (newCard.charAt(0) == 'T') {
                number = 10;
            } else if (newCard.charAt(0) == 'J') {
                number = 11;
            } else if (newCard.charAt(0) == 'Q') {
                number = 12;
            } else if (newCard.charAt(0) == 'K') {
                number = 13;
            } else if (newCard.charAt(0) == 'A') {
                number = 14;
            } else {
                assert false : "Input Error";
            }
            player2.add(number, newCard.charAt(1));
            if (pos < s.length()) {
                pos += nextWordOrSeparator(s, pos, separators).length();
            }
        }
        //Determine winner
        PokerHand winningHand;
        if (player1.evaluate().compare(player2.evaluate()) > 0) {
            winningHand = player1;
        } else if (player1.evaluate().compare(player2.evaluate()) < 0) {
            winningHand = player2;
        } else {
            if (player1.evaluate().equals(PokerHand.Rank.ONE_PAIR)) {
                if (player1.findSet(2) > player2.findSet(2)) {
                    winningHand = player1;
                } else if (player1.findSet(2) < player2.findSet(2)) {
                    winningHand = player2;
                } else {
                    winningHand = highCardWinner(player1, player2);
                }
            } else if (player1.evaluate().equals(PokerHand.Rank.TWO_PAIR)) {
                if (player1.findSet(2) > player2.findSet(2)) {
                    winningHand = player1;
                } else if (player1.findSet(2) < player2.findSet(2)) {
                    winningHand = player2;
                } else {
                    PokerHand temp1 = player1.removeAll(player1.findSet(2));
                    PokerHand temp2 = player2.removeAll(player2.findSet(2));
                    if (player1.findSet(2) > player2.findSet(2)) {
                        winningHand = player1;
                    } else if (player1.findSet(2) < player2.findSet(2)) {
                        winningHand = player2;
                    } else {
                        PokerHand temp3 = player1.removeAll(player1.findSet(2));
                        PokerHand temp4 = player2.removeAll(player2.findSet(2));
                        winningHand = highCardWinner(player1, player2);
                        player1.combine(temp3);
                        player2.combine(temp4);
                    }
                    player1.combine(temp1);
                    player2.combine(temp2);
                }
            } else if (player1.evaluate().equals(PokerHand.Rank.TRIPS)
                    || player1.evaluate().equals(PokerHand.Rank.FULL_HOUSE)) {
                if (player1.findSet(3) > player2.findSet(3)) {
                    winningHand = player1;
                } else {
                    winningHand = player2;
                }
            } else if (player1.evaluate().equals(PokerHand.Rank.QUADS)) {
                if (player1.findSet(4) > player2.findSet(4)) {
                    winningHand = player1;
                } else {
                    winningHand = player2;
                }
            } else {
                winningHand = highCardWinner(player1, player2);
            }
        }
        if (winningHand == null) {
            result += "Tie.";
        } else {
            result += winningHand.getName() + " wins with ";
            switch (winningHand.evaluate()) {
                case HIGH:
                    result += translateNumbers(winningHand.findSet(1))
                            + " high.";
                    break;
                case ONE_PAIR:
                    result += "a pair of "
                            + translateNumbers(winningHand.findSet(2)) + "s.";
                    break;
                case TWO_PAIR:
                    String upper = translateNumbers(winningHand.findSet(2));
                    PokerHand temp = winningHand
                            .removeAll(winningHand.findSet(2));
                    String lower = translateNumbers(winningHand.findSet(2));
                    winningHand.combine(temp);
                    result += "two pair " + upper + "s over " + lower + "s.";
                    break;
                case TRIPS:
                    result += "trip " + translateNumbers(winningHand.findSet(3))
                            + "s.";
                    break;
                case STRAIGHT:
                    if (winningHand.findSet(1) == 14) {
                        PokerHand tryTemp = winningHand
                                .removeAll(winningHand.findSet(1));
                        if (winningHand.findSet(1) == 5) {
                            result += "the wheel.";
                        } else {
                            result += "an Ace-high straight.";
                        }
                        winningHand.combine(tryTemp);
                    } else {
                        result += "a "
                                + translateNumbers(winningHand.findSet(1))
                                + "-high straight.";
                    }
                    break;
                case FLUSH:
                    result += "a " + translateNumbers(winningHand.findSet(1))
                            + "-high flush.";
                    break;
                case FULL_HOUSE:
                    String uppers = translateNumbers(winningHand.findSet(3));
                    PokerHand temps = winningHand
                            .removeAll(winningHand.findSet(3));
                    String lowers = translateNumbers(winningHand.findSet(2));
                    winningHand.combine(temps);
                    result += uppers + "s full of " + lowers + "s.";
                    break;
                case QUADS:
                    result += "quad " + translateNumbers(winningHand.findSet(4))
                            + "s.";
                    break;
                case STRAIGHT_FLUSH:
                    if (winningHand.findSet(1) == 14) {
                        PokerHand tryTemp = winningHand
                                .removeAll(winningHand.findSet(1));
                        if (winningHand.findSet(1) == 5) {
                            result += "a 5-high straight flush.";
                        } else {
                            result += "a royal flush.";
                        }
                        winningHand.combine(tryTemp);
                    } else {
                        result += "a "
                                + translateNumbers(winningHand.findSet(1))
                                + "-high straight flush.";
                    }
                    break;

                default:
                    //Should never get here.
                    break;
            }
        }
        return result;
    }

    /**
     * Translates special card numbers.
     *
     * @requires 2 <= number <= 14
     * @param number
     * @return text corresponding to the card number given.
     */
    private static String translateNumbers(Integer number) {
        String translation;
        if (number <= 10) {
            translation = number.toString();
        } else if (number == 11) {
            translation = "Jack";
        } else if (number == 12) {
            translation = "Queen";
        } else if (number == 13) {
            translation = "King";
        } else {
            translation = "Ace";
        }
        return translation;
    }

    /**
     *
     * @param player1
     * @param player2
     * @return the reference to the higher-valued hand in high-card system. If
     *         the hands are tied, returns null.
     */
    private static PokerHand highCardWinner(PokerHand player1,
            PokerHand player2) {
        PokerHand winner;
        if (player1.size() == 0 || player2.size() == 0) {
            winner = null;
        } else if (player1.findSet(1) > player2.findSet(1)) {
            winner = player1;
        } else if (player1.findSet(1) < player2.findSet(1)) {
            winner = player2;
        } else {
            PokerHand temp1 = player1.removeAll(player1.findSet(1));
            PokerHand temp2 = player2.removeAll(player2.findSet(1));
            winner = highCardWinner(player1, player2);
            player1.combine(temp1);
            player2.combine(temp2);
        }
        return winner;
    }

    /**
     * Code borrowed from a previous project.
     *
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        boolean isSeparator = separators.contains(text.charAt(position));
        int counter = position + 1;
        while (counter < text.length()
                && isSeparator == separators.contains(text.charAt(counter))) {
            counter++;
        }
        return text.substring(position, counter);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("data/test.txt"));
        } catch (IOException e) {
            System.err.println("Error opening input file.");
            return;
        }
        try {
            String s = input.readLine();
            while (s != null) {
                System.out.println(processInput(s));
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading input file.");
        }
        try {
            input.close();
        } catch (IOException e) {
            System.err.println("Error closing input file.");
        }
    }

}
