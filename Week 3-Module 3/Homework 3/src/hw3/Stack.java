/*********************************************************************
  Author:        Dana Vrajitoru, IUSB, CS
  Class:         C343 Data Structures
  File name:     Stack.java
  Last updated:  November 2025
  Description:   Definition of a class stack derived from a list.
 **********************************************************************/

package hw3;

public class Stack extends List {
	// Default constructor: create an empty stack.
	Stack() 
	{
		super();
	} //Stack()

	// Insert an object into the stack.
	void push(int data)
	{
		insertFront(data);
	} // push()

	// Remove the top object and return its value.
	int pop()
	{
		int result = top();
		removeFront();
		return result;
	} // pop() 

	// Inspect the value of the top object.
	int top()
	{ 
		if (!isEmpty()) 
			return begin().get();
		else 
		{
			System.out.println("Attempt to access the top of an empty stack");
			System.exit(1);
		}  
		return 0;
	} // top()

	// Inspect the bottom object of the stack. Unorthodox function for a
	// stack but necessary in the game.
	int bottom()
	{
		if (!isEmpty())
			return end().get();
		else 
		{
			System.out.println("attempt to find the bottom of an empty stack");
			System.exit(1);
		}
		return 0;
	} // bottom()

	// Delete all the objects in the stack.
	void makeEmpty()
	{ 
		this.clear(); 
	}// makeEmpty()

	// Test if the stack is empty.
	boolean isEmpty()
	{ 
		// return !(List)(*this); 
		return super.size == 0;
	} // isEmpty()

	// Concatenate two stacks.
	void concatenate(Stack other)
	{
		super.concatenate(other);
	} // concatenate()

	// Very simple display of the stack for the purpose of the game.
	void print()
	{
		this.display();
	} // print()

	// The number of objects in the stack.
	int stored()
	{
		return this.size;
	} // stored()
}
