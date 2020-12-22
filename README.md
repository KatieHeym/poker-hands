# poker-hands
A response to the coding challenge at http://codingdojo.org/kata/PokerHands/.

The base of this response is rooted in two custom objects, the Card object (interface Card, implementing class Card1) and the PokerHand object (interface PokerHand, implementing class PokerHand1). The Card object is an immutable type that is created as a duple of an int from 2 to 14, representing the cards 2 through Ace, and a char representing the suit of the card (C,D,H,S). Because Card is an immutable type, there should be no problem with aliasing of Cards. The PokerHand object is a collection of Cards. It has many utility classes that allow adding and removing cards to and from the hand. The most critical methods for this application were .removeAll, .findSet, and .evaluate.

PokerHand.removeAll simply removes all Cards with the specified number. PokerHand.findSet asks for set size. It then returns the number of the highest set of cards in the hand of at least the speficied size. It makes identifying the highest single, pair, triple, etc. in the hand possible.

PokerHand.evaluate is the meat of the class. The interface PokerHand defines an enum with all the possible poker hands and their ranks. PokerHand.evaluate identifies and returns the highest rank whose requirements the hand satisfies. The hand must be 5 cards in size, like any standard poker hand candidate. One particularity in playing cards is the ability for Ace to play high or low. Because it is almost always advantageous in poker for Ace is be played high, it is given number 14, one above King. The only case where one would play Ace low is with a straight from Ace to 5, known as the wheel. Thus, there is a speical check for the wheel in PokerHand.evaluate.

The main utility class is PokerHandComparer. It handles the input from the data file and determines the winning hand. It outputs who won and with what sort of hand to the console. The input format starts with the number of the test case for easy comparison with results, and then proceeds in the format listed on the challenge site http://codingdojo.org/kata/PokerHands/.

The language used for the results is borrowed from poker slang. For example, 4s full of 10s indicates a Full House hand with three 4s and two 10s.

To run the program, download the .zip file, unzip to the desired directory and run the PokerHandComparer file. To add or remove test cases, edit the test.txt file in the data folder. A copy of each of the code files as well as the initial test.txt file is provided in the repository.
