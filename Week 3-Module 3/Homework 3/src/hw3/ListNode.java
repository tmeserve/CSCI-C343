package hw3;

public class ListNode
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
