package hw2;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
Name: Tyler Meserve
Date: 9/1/2026
Homework #: Homework 2
Source file: hw2.java
Class: C343
Action: Demonstrates building & manipulating a singly linked list and custom iterators
External Assistance Provided By: 
*/

class Main
{
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        List list = new List();
        boolean quit = false;
        int input = -1;
        String inputError = "Please input 0-12.";

        while (!quit)
        {
            try
            {
                System.out.println("\n0 - Quit the program");
                System.out.println("1 - Insert new node at the front of the list");
                System.out.println("2 - Insert new node at the back of the list");
                System.out.println("3 - Remove the front node of the list");
                System.out.println("4 - Print list");
                System.out.println("5 - Check if the list is ordered");
                System.out.println("6 - Print using toString()");
                System.out.println("7 - Calculate the sum of the linked list");
                System.out.println("8 - Increment all values by 1");
                System.out.println("9 - Find a node by value");
                System.out.println("10 - Count all even numbers");
                System.out.println("11 - Print the last element");
                System.out.println("12 - Decrease all values by 1");
                System.out.print("Selection: ");
                input = scan.nextInt();
                scan.nextLine();
                switch (input)
                {
                    case 0:
                        quit = true;
                        break;
                    case 1:
                        insertFront(list);
                        break;
                    case 2:
                        insertBack(list);
                        break;
                    case 3:
                        if (list.removeFront())
                            System.out.println("The front has been removed.");
                        else
                            System.out.println("The list is empty; nothing to remove");
                        break;
                    case 4:
                        list.print();
                        break;
                    case 5:
                        String toPrint = "The list is ";

                        if (list.isOrdered())
                            toPrint += "in order.";
                        else
                            toPrint += "not in order.";

                        System.out.println(toPrint);
                        break;
                    case 6:
                        System.out.println("Singly Linked List: " + list.toString());
                        break;
                    case 7:
                        sum(list.begin());
                        break;
                    case 8:
                        list.incrementAll();
                        System.out.println("All values have been incremented by 1");
                        break;
                    case 9:
                        find(list);
                        break;
                    case 10:
                        int evenCount = list.countEven();
                        if (evenCount == 0)
                            System.out.println("There are no even numbers in the list.");
                        else
                            System.out.println("There are " + evenCount + " even numbers in the list.");
                        break;
                    case 11:
                        printTailNode(list);
                        break;
                    case 12:
                        list.decreaseAll();
                        System.out.println("All values have been decreased by 1");
                        break;
                    default:
                        System.out.println(inputError);
                        break;
                }
            }
            catch (InputMismatchException e)
            { System.out.println(inputError); }
        }
    }

    /*
    Action: Prompts the user for an integer and inserts it at the front of the given list.
    Params: list - the list to insert into.
    Returns: none.
    Precondition: list is not null.
    */
    public static void insertFront(List list)
    {
        try
        {
            System.out.print("Please input an integer to insert at the front: ");
            int value = scan.nextInt();
            scan.nextLine();

            list.insertFront(value);
        }
        catch (InputMismatchException e)
        { System.out.println("Please enter a valid integer."); }
    }

    /*
    Action: Prompts the user for an integer and inserts it at the back of the given list.
    Params: list - the list to insert into.
    Returns: none.
    Precondition: list is not null.
    */
    public static void insertBack(List list)
    {
        try
        {
            System.out.print("Please input an integer to insert at the back: ");
            int value = scan.nextInt();
            scan.nextLine();

            list.insertBack(value);
        }
        catch (InputMismatchException e)
        { System.out.println("Please enter a valid integer."); }
    }

    /*
    Action: Traverses a list using a ListIterator and prints the sum of its elements.
    Params: list - an iterator positioned at the first node of the list to sum.
    Returns: none.
    Precondition: list is not null.
    */
    public static void sum(ListIterator list)
    {
        int sum = 0;

        while (!list.isNull())
        {
            sum += list.get();
            list = list.next();
        }

        System.out.println("The sum of the linked list is " + sum);
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public static void find(List list)
    {
        try
        {
            System.out.print("Please input an integer to insert at the back: ");
            int value = scan.nextInt();
            scan.nextLine();

            ListNode node = list.find(value);

            if (node == null)
                System.out.println("Integer, " + value + ", was not found in the list.");
            else
                System.out.println("Integer, " + value + ", was found in the list.");
        }
        catch (InputMismatchException e)
        { System.out.println("Please enter a valid integer."); }
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public static void printTailNode(List list)
    {
        ListIterator tailNode = list.end();
        System.out.println("Last Nodes Value: " + tailNode.get());
    }
}

class ListNode
{
    int datum;
    ListNode next;

    public ListNode()
    {}

    public ListNode(int datum)
    { 
        this.datum = datum;
        this.next = null;
    }

    public ListNode(ListNode node)
    {
        this.datum = node.datum;
        this.next = null;
    }
}

class List
{
    private ListNode head;
    private ListNode tail;
    private int size = 0;

    /*
    Action: Removes all nodes from the list, resetting it to empty.
    Params: none.
    Returns: none.
    Precondition: none.
    */
    public void clear()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /*
    Action: Inserts a new node holding the given value at the front of the list.
    Params: value - the integer value to insert.
    Returns: none.
    Precondition: none.
    */
    public void insertFront(int value)
    {
        ListNode temp = new ListNode(value);

        temp.next = this.head;
        this.head = temp;

        if (tail == null) // first node
            this.tail = temp;
        
        this.size++;
    }

    /*
    Action: Inserts a new node holding the given value at the back of the list.
    Params: value - the integer value to insert.
    Returns: none.
    Precondition: none.
    */
    public void insertBack(int value)
    {
        ListNode temp = new ListNode(value);
        if (head == null) 
            this.head = this.tail = temp;
        else 
        {
            this.tail.next = temp;
            this.tail = temp;
        }
        this.size++;
    }

    /*
    Action: Removes the first node in the list, if one exists.
    Params: none.
    Returns: true if a node was removed, false if the list was already empty.
    Precondition: none.
    */
    public boolean removeFront()
    {
        if (size == 0)
            return false;
        else if (size == 1)
            this.clear();
        else
        {
            this.head = this.head.next;
            this.size--;
        }

        return true;
    }

    /*
    Action: Prints every node in the list, in order, separated by arrows.
    Params: none.
    Returns: none.
    Precondition: none.
    */
    public void print()
    {
        ListNode currentNode = this.head;
        System.out.print("Singly Linked List: ");
        
        while (currentNode != null)
        {
            System.out.print(currentNode.datum);
            currentNode = currentNode.next;
            if (currentNode != null)
                System.out.print(" -> ");
        }
        System.out.println();
    }

    /*
    Action: Checks whether the list is in ascending order.
    Params: none.
    Returns: true if the list is empty or every element is <= the one after it, false otherwise.
    Precondition: none.
    */
    public boolean isOrdered()
    {
        ListNode currentNode = this.head;
        if (currentNode == null)
            return true;

        ListNode previousNode = currentNode;

        while (currentNode.next != null)
        {
            if (previousNode.datum > currentNode.datum)
                return false;
            
            currentNode = currentNode.next;
        }

        return true;
    }

    /*
    Action: Creates an iterator positioned at the first node of the list.
    Params: none.
    Returns: a ListIterator referencing the list's head node.
    Precondition: none.
    */
    public ListIterator begin()
    { return new ListIterator(this.head); }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public ListIterator end()
    { return new ListIterator(this.tail); }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public void incrementAll()
    {
        ListNode currentNode = this.head;

        while (currentNode != null)
        {
            currentNode.datum++;
            currentNode = currentNode.next;
        }
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public void decreaseAll()
    {
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            int value = currentNode.get();
            currentNode.set(value--);
            currentNode = currentNode.next();
        }
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public ListNode find(int value)
    {
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            if (value == currentNode.get())
                return currentNode.current;

            currentNode = currentNode.next();
        }

        return null;
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public int countEven()
    {
        int count = 0;
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            if (currentNode.get() % 2 == 0)
                count += 0;

            currentNode = currentNode.next();
        }

        return count;
    }

    /*
    Action: Builds a string representation of the list's contents, traversed with a ListIterator.
    Params: none.
    Returns: a string containing each node's datum in order, separated by arrows.
    Precondition: none.
    */
    @Override
    public String toString()
    {
        String toReturn = "";
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            toReturn += currentNode.get();
            currentNode = currentNode.next();
            if (currentNode != null)
                toReturn += " -> ";
        }
        
        return toReturn;
    }
}

class ListIterator
{
    ListNode current;

    // Constructors
    public ListIterator() {}

    public ListIterator(ListIterator list)
    { this.current = list.current; }
    
    public ListIterator(ListNode head)
    { this.current = head; }

    // Moves to the next node and returns this iterator
    ListIterator next()
    {
        if (current != null)
            current = current.next;
        return this;
    }

    // Returns the value of the current node
    int get()
    {
        if (current == null)
            throw new IllegalStateException("Current node is null");
        return current.datum;
    }

    void set(int value)
    {
        if (current == null)
            throw new IllegalStateException("Current node is null");
        current.datum = value;
    }

    // Checks if there is a next node available
    boolean hasNext()
    { return current != null && current.next != null; }

    // Checks if the current pointer is null
    boolean isNull()
    { return current == null; }

    /*
    Action: Checks whether this iterator and another iterator reference nodes with the same datum.
    Params: other - the iterator to compare against.
    Returns: true if both iterators' current nodes hold the same datum, false otherwise.
    Precondition: other is not null; both iterators' current nodes are not null.
    */
    boolean sameAs(ListIterator other)
    { return this.get() == other.get(); }
}