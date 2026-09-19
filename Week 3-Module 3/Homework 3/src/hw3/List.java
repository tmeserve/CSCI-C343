package hw3;

public class List
{
    protected ListNode head;
    protected ListNode tail;
    protected int size = 0;

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
            this.clear(); // last node removed - clear() resets both head and tail together
        else
        {
            this.head = this.head.next;
            this.size--;
        }

        return true;
    }

    /*
    Action: Appends all the nodes of another list to the end of this list and
            leaves the other list empty.
    Params: other - the list whose nodes are moved onto the end of this list
    Returns: nothing
    Precondition: none (a null or empty list is ignored)
    */
    public void concatenate(List other)
    {
        if (other == null || other == this || other.head == null)
            return;

        if (head == null)
            head = other.head;
        else
        {
            ListNode lastNode = head;
            while (lastNode.next != null)
            {
                lastNode = lastNode.next;
            }
            lastNode.next = other.head;
        }

        size += other.size;
        other.head = null;
        other.size = 0;
    }

    /*
    Action: (tells what it's supposed to do)
    Params: (name of params and what they represent to the function)
    Returns: (what item is being returned if any void if nothing)
    Precondition: (tells user what the params should be to guarantee correct output)
    */
    public void display()
    {
        if (size > 0)
        {
            ListIterator iter = new ListIterator(head);
            while (!iter.isNull())
            {
                System.out.printf("%2d ", iter.current.datum);
                iter.next();
            }
        }
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
            
            previousNode = currentNode;
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
    Action: Creates an interator positioned at the last node of the list
    Params: none
    Returns: a ListIterator referencing the list's tail node
    Precondition: none
    */
    public ListIterator end()
    { return new ListIterator(this.tail); }

    /*
    Action: Increments the value of every node in the list by 1
    Params: none
    Returns: none
    Precondition: None
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
    Action: Decreases the value of every node in the list by 1
    Params: none
    Returns: none
    Precondition: None
    */
    public void decreaseAll()
    {
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            int value = currentNode.get();
            currentNode.set(--value);
            currentNode = currentNode.next();
        }
    }

    /*
    Action: Searches the list for a node containing the given value
    Params: value - the integer value to search for
    Returns: The first ListNode found holding value or null if no such node exists
    Precondition: None
    */
    public ListNode find(int value)
    {
        if (this.size == 0)
            return null;
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
    Action: Counts how many nodes in the list hold an even value
    Params: none
    Returns: the number of even-valued nodes in the list
    Precondition: none
    */
    public int countEven()
    {
        int count = 0;
        ListIterator currentNode = this.begin();

        while (!currentNode.isNull())
        {
            if ((currentNode.get() % 2) == 0)
                count++;

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
            if (!currentNode.isNull())
                toReturn += " -> ";
        }
        
        return toReturn;
    }
}