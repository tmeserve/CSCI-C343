package hw2;

import java.util.InputMismatchException;
import java.util.Scanner;

class Main
{
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        List list = new List();
        boolean quit = false;
        int input = -1;
        String inputError = "Please input 0-6.";

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
                        list.removeFront();
                        System.out.println("The front has been removed");
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
                    default:
                        System.out.println(inputError);
                        break;
                }
            }
            catch (InputMismatchException e)
            { System.out.println(inputError); }
        }
    }

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
}

class List
{
    private ListNode head;
    private ListNode tail;
    private int size = 0;

    public void clear()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void insertFront(int value)
    {
        ListNode temp = new ListNode(value);

        temp.next = this.head;
        this.head = temp;

        if (tail == null) // first node
            this.tail = temp;
        
        this.size++;
    }

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

    public void removeFront()
    {
        if (size == 0)
            return;
        else if (size == 1)
            this.clear();
        else {
            this.head = this.head.next;
            this.size--;
        }
    }

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

    public ListIterator being()
    { return new ListIterator(this.head); }

    public String toString()
    {
        String toReturn = "";
        ListNode currentNode = this.head;

        while (currentNode != null)
        {
            toReturn += currentNode.datum;
            currentNode = currentNode.next;
            if (currentNode != null)
                toReturn += " -> ";
        }
        
        return toReturn;
    }
}

class ListIterator
{
    ListNode current;

    // Constructor to initialize the iterator
    public ListIterator(ListNode head) {
        this.current = head;
    }

    // Moves to the next node and returns this iterator
    ListIterator next() {
        if (current != null) {
            current = current.next;
        }
        return this;
    }

    // Returns the value of the current node
    int get() {
        if (current == null) {
            throw new IllegalStateException("Current node is null");
        }
        return current.datum;
    }

    // Checks if there is a next node available
    boolean hasNext() {
        return current != null && current.next != null;
    }

    // Checks if the current pointer is null
    boolean isNull() {
        return current == null;
    }
}