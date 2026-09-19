package hw3;

public class ListIterator
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

    // Sets the value of the current node
    void set(int value)
    {
        if (current == null)
            throw new IllegalStateException("Current node is null");
        this.current.datum = value;
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
