/*********************************************************************
  Author:        Dana Vrajitoru, IUSB, CS
  Modified By:   Tyler Meserve
  Class:         C343 Data Structures
  File name:     Canfield.java
  Last updated:  November 2025
  Description:   Implementation of the card game of Canfield using 
                 stacks and queues.
 **********************************************************************/
package hw3;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Canfield {
	static int NUM_CARDS = 52, NUM_SUITS = 4, NUM_RANKS = 13;
	Queue deck;
	Stack reserve;
	Stack foundations[],	tableau[];
	int   waste;
	boolean quit;
	char  from, previousFrom, to, previousTo;
	int   fromId, previousFromId, toId, previousToId;
	boolean repeatMove;
	int score;

	static int TEST_SCENARIO = 0;
	
	Scanner scan;

	public static void main(String[] args) {
		Canfield game = new Canfield();
	    game.play();
	}

	// Prints the choices of actions for the player.
	static void printMenu()
	{
		System.out.println("[q] quit [N] new game [m] move [d] deal a card");
		System.out.print("Enter your choice: ");
	} // printMenu()

	// Clears the terminal screen.
	static void clearScreen()
	{
		for (int i = 0; i < 24; i++)
			System.out.println("");
	} // clearScreen()

	// Input a number between 0 and the limit
	int getNumber(int limit)
	{
		int n = scan.nextInt();
		while (n < 0 || n >= limit)
		{
			System.out.println("Please enter a number between 0 and " + limit);
			n = scan.nextInt();
		}
		return n;
	}

	// Default constructor
	Canfield()
	{
		deck = new Queue();
		reserve = new Stack();
		foundations = new Stack[NUM_SUITS];
		tableau = new Stack[NUM_SUITS];
		for (int i = 0; i < NUM_SUITS; i++)
		{
			foundations[i] = new Stack();
			tableau[i] = new Stack();
		}
		waste  = -1;        // non existing card, empty marker
		quit   = false;
		from   = '\0'; 
		to     = '\0';
		fromId = 0; 
		toId   = 0;
		scan = new Scanner(System.in);
	} // Canfield() 

	// The main loop of the game.
	void play()
	{
		try {
			char answer;
			newGame();
			printGame();
			while (!quit) 
			{
				printMenu();
				answer = (char)System.in.read();
				performAction(answer);
			}
		}
		catch (IOException e) {
			System.out.println("Input error in move");
		}
	} // play()

	// Empty all the data structures
	void emptyAll()
	{
		int i;
		deck.makeEmpty();
		reserve.makeEmpty();
		for (i = 0; i < NUM_SUITS; i++) 
		{
			foundations[i].makeEmpty();
			tableau[i].makeEmpty();
		}
	} // emptyAll()

	////////////////////////New game ///////////////////////////////////////////

	//Starting a new game. It must reset all the data structures,
	//generate a new set of 52 random cards and place them in the
	//appropriate places in the game.
	void newGame()
	{
		int i, card;
		int cardsLeft = NUM_CARDS;
		int newDeck[] = new int[NUM_CARDS];
		score = 0;

		// Empty all the data structures
		emptyAll();

		// Create a new deck and shuffle it
		newShuffledPack(newDeck);

		// Now place the cards in the data structures
		for (i = 0; i < NUM_RANKS; i++)  // fill in the reserve with 13 cards
		{
			card = newDeck[--cardsLeft]; // use a card and decrease the total count
			reserve.push(card);
		}
		for (i = 0; i < NUM_SUITS; i++)  // draw a card for each tableau
		{
			card = newDeck[--cardsLeft]; // use a card and decrease the total count
			tableau[i].push(card);
		}
		for (i = 0; i < NUM_SUITS; i++)  // Initialize the foundations with 0
			foundations[i].push(0);
		waste = newDeck[--cardsLeft];    // the card to play
		while (cardsLeft > 0)            // the remaining cards go into the deck
		{
			card = newDeck[--cardsLeft];
			deck.enqueue(card);
		}

		if (TEST_SCENARIO > 0)
		{
			testSetup(TEST_SCENARIO);
		}
	} // newGame()

	void testSetup(int scenario)
	{
		emptyAll();
		score = 0;
		for (int i=0; i < NUM_SUITS; i++)
			foundations[i].push(0);

		if (scenario == 1)
		{
			reserve.push(8);
			reserve.push(11);
			reserve.push(8);
            tableau[0].push(1);
            tableau[1].push(4);
            tableau[1].push(3);
            tableau[1].push(2);
            tableau[2].push(7);
            tableau[2].push(5);
            tableau[3].push(7);
            tableau[3].push(6);
            tableau[3].push(5);
            foundations[2].push(4);
            waste = 12;
            deck.enqueue(10);
            deck.enqueue(4);
        }
        else if (scenario == 2)
        {
            foundations[0].push(13);
            foundations[1].push(13);
            foundations[2].push(13);
            foundations[3].push(10);
            tableau[3].push(13);
            tableau[3].push(12);
            tableau[3].push(11);
            waste = -1;
        }
	}

	// Create a new pack of cards and shuffles them
	static void newShuffledPack(int newDeck[])
	{
		int i, j;
		Random rand = new Random();

		for (i = 0; i < NUM_CARDS; i++)
			newDeck[i] = 1 + i % NUM_RANKS; // add the cards in order

		for (i = 0; i < NUM_CARDS-1; i++)   // then shuffle them
		{
			j = i + rand.nextInt(NUM_CARDS-i); // random other card
			swap(newDeck, i, j);   // swap them
		}
	} // newShuffledPack()

	// Swap two elements of an array
	static void swap(int a[], int i, int j)
	{
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	} // swap()

	// Performs an action based on the user's choice.
	void performAction(char answer)
	{
		switch (answer) 
		{
		case 'q':
		case 'Q':
			quit = true;
			break;
			//case 'n': this causes problems because it's next to m
		case 'N':
			newGame();
			break;
		case 'm':
		case 'M':
			move();
			break;
		case 'd':
		case 'D':
			deal();
			break;
		case 'r':
		case 'R':
			repeatMove = true;
			move();
			repeatMove = false;
			break;
		}
		if (!quit)
			printGame();

		//
		if (won())
		{
			System.out.println("Congratulations, you won! Final Score: " + score);
			quit = true;
		}
	} // performAction()

	// Displays the entire content of the game.
	void printGame()
	{
		clearScreen();
		System.out.print("Deck:    ");
		if (waste != -1)
			System.out.print(waste);
		System.out.println("");
		System.out.println("");
		System.out.print("Reserve: ");
		if (!reserve.isEmpty())
			System.out.print(reserve.top());
		System.out.println("");
		System.out.println("");

		for (int i = 0; i < NUM_SUITS; i++) 
		{
			System.out.print("F" + i + "      ");
			if (foundations[i].top() > 0)
				System.out.print(foundations[i].top() + "   ");
			else
				System.out.print("     ");
			System.out.print("T" + i + ' ');
			tableau[i].print();
			System.out.println("");
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
		System.out.println("Score: " + score);
		System.out.println("");
		System.out.println("");
	} // printGame()

	// Deals one card from the deck.
	void deal()
	{
		if (!deck.isEmpty()) 
		{
			deck.enqueue(waste);
			waste = deck.dequeue();
		}
	} // deal()

	// Performs one move.
	void move()
	{
		try {
			int card;
			
			if (repeatMove)
			{
				from = previousFrom;
				to = previousTo;
				fromId = previousFromId;
				toId = previousToId;
			}
			else
			{
				System.out.println("From:");
				System.out.print("[t] top of the tableau [T] whole tableau [d] deck [r] reserve : ");
				from = scan.next().charAt(0);
				if (from == 't' || from == 'T') 
				{
					System.out.print("number : ");
					fromId = getNumber(NUM_SUITS);
				}
				System.out.print("To: [t] tableau [f] foundation : ");
				to = scan.next().charAt(0);
				System.out.print("number : ");
				toId = getNumber(NUM_SUITS);
			}
			
			card = getCard();
			System.out.println("card to move: " + card);
			if (card != -1 && checkMove(card)) 
			{
				removeCard();
				addCard(card);
				refillTableau();
				previousFrom = from;
				previousFromId = fromId;
				previousTo = to;
				previousToId = toId;
			}
			else 
			{
				System.out.print("Movement not permitted. Strike any key to continue."); 
				System.in.read();
				//while (cin.get() != '\n') ; // Clear the line
			}
		}
		catch (IOException e) {
			System.out.println("Input error in move");
		}
	} // move()

	/*
    Action: Moves every card of the tableau fromId onto the foundation toId, top
            card first, adding one to the score for each card moved.
    Params: none (uses the fields tableau, foundations, fromId, toId and score)
    Returns: nothing
    Precondition: tableauFitsFoundation() has returned true for these two stacks
    */
	void moveTableauToFoundation()
	{
		while (!tableau[fromId].isEmpty())
		{
			foundations[toId].push(tableau[fromId].pop());
			score++;
		}
	}

	boolean won()
	{
		for (Stack foundation: foundations)
		{
			if (foundation.top() != NUM_RANKS)
				return false;
		}
		return true;
	}

	/*
    Action: Moves one card from the reserve onto the tableau the last move
	        came from, if that tableau is now empty.
    Params: none (uses the fields from, fromId, tableau and reserve)
    Returns: nothing
    Precondition: a move from a tableau has just been completed
    */
	void refillTableau()
	{
		if ((tableau[fromId].isEmpty() && !reserve.isEmpty()) && (from == 't' || from == 'T'))
		{
			int tempCard = reserve.pop();
			tableau[fromId].push(tempCard);
		}
	}

	/*
    Action: Checks whether the whole tableau fromId can be placed on the foundation
            toId, i.e. its cards from top to bottom are exactly the next numbers
            the foundation needs. The tableau is left unchanged.
    Params: none (uses the fields tableau, foundations, fromId and toId)
    Returns: true if every card fits in sequence, false otherwise
    Precondition: the tableau fromId is not empty
    */
    boolean tableauFitsFoundation()
	{
		Stack temp = new Stack();
		int expected = foundations[toId].top() + 1;
		boolean fits = true;

		while (!tableau[fromId].isEmpty())
		{
			int card = tableau[fromId].pop();
			temp.push(card);
			
			if (card != expected)
				fits = false;
			expected++;
		}

		while (!temp.isEmpty())
			tableau[fromId].push(temp.pop());

		return fits;
	}

	//////////////////////////////////////////////////////////////////////
	//Functions that perform the actions for a move, in this order:
	//get the card to move, check if it can be moved, 
	//remove it from the old structure, add it to the new one
	//////////////////////////////////////////////////////////////////////

	//Determines what card is to be moved. Accessor method.
	int getCard()
	{
		switch (from) 
		{
		case 'd':
		case 'D':
			return waste;
		case 'r':
		case 'R':
			if (!reserve.isEmpty())
				return reserve.top();
			else
				return -1;
		case 't':
			if (!tableau[fromId].isEmpty())
				return tableau[fromId].top();
			else
				return -1;
		case 'T':
			if (!tableau[fromId].isEmpty())
				return tableau[fromId].bottom();
			else
				return -1;
		default:
			return -1;
		}  
	} // getCard()

	//Checks if the requested movement is possible. Accessor method.
	boolean checkMove(int card)
	{
		if (to == 't' || to == 'T') 
			if (tableau[toId].isEmpty()) // one can always move anything to an
				// empty tableau
				return true;
			else
				return (tableau[toId].top() == card+1 || 
				tableau[toId].top() == 1 && card == NUM_RANKS) ;
		// One can place a card on the top of the tableau if it's one
		// card smaller than the top of that tableau. One can also
		// place a 13 over a 1.
		else if (to == 'f' || to == 'F') 
		{
			if (from == 'T')
				return tableauFitsFoundation();
			return (foundations[toId].top() == card-1);
		}
		return false; // Anything else is not permitted.
	} // checkMove()

	//Removes the card to be moved. Mutator method.
	void removeCard()
	{
		int error=0;
		switch (from) 
		{
		case 'd':
		case 'D':
			if (!deck.isEmpty())
				waste=deck.dequeue();
			else
				waste=-1;
			break;
		case 'r':
		case 'R':
			if (!reserve.isEmpty())
				reserve.pop();
			else 
				error = 1;
			break;
		case 't':
			if (!tableau[fromId].isEmpty()) 
				tableau[fromId].pop();
			else 
				error = 2;
			break;
		case 'T':
			if (!tableau[fromId].isEmpty())
				;
			else 
				error = 3;
			break;
		}
		if (error > 0) 
		{
			System.out.println("Error in the code: " + error);
			System.exit(1);
		}
	} // removeCard()

	//Adds the card or the entire tableau to its new destination. 
	//Mutator method.
	void addCard(int card)
	{
		if (from == 'T' && (to == 't' || to == 'T')) 
		{
			// add the stack onto which we move at the end of the one we move.
			tableau[fromId].concatenate(tableau[toId]);
			// move that stack back into the destination stack.
			tableau[toId].concatenate(tableau[fromId]);
		}
		else if (from == 'T' && (to == 'f' || to == 'F'))
			moveTableauToFoundation();
		else 
			if (to == 't' || to == 'T') 
				tableau[toId].push(card);
			else
			{
				foundations[toId].push(card);
				score++;
			}
				
	} // addCard()
}
