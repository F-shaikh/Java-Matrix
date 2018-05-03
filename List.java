// Faisal Shaikh
public class List {
    // class List Data
    private int cursorIndex; // Index of current element that cursor is pointing at
    private int length; // Length of list
    private Node front; // Node pointing to front element in list
    private Node back; // Node pointing to back element in list
    private Node cursor; // Node pointing to current element

    // Constructor
    List() { // Creates a new empty list.
        cursorIndex = -1;
        length = 0;
        front = null;
        back = null;
        cursor = null;
    }

    private class Node {
        // Fields
        Object data;
        Node next;
        Node prev;

        // Constructor
        Node(Object data) {
            this.data = data;
            next = null;
            prev = null;
        }

        // toString():  overrides Object's toString() method
        public String toString() {
            return String.valueOf(data);
        }
    }

    // Access functions
    int length() // Returns the number of elements in this List.
    {
        return length;
    }

    int index() // If cursor is defined, returns the index of the cursor element,
    // otherwise returns -1.
    {
        return cursorIndex;
    }

    Object front() // Returns front element. Pre: length()>0
    {
        return front.data;
    }

    Object back() // Returns back element. Pre: length()>0
    {
        return back.data;
    }

    Object get() // Returns cursor element. Pre: length()>0, index()>=0
    {
        return cursor.data;
    }

    public boolean equals(Object x) // Returns true if and only if this List and L are the same
    // integer sequence. The states of the cursors in the two Lists
    // are not used in determining equality.
    {
        List temp3 = (List) x;
        if (this.length == temp3.length) {
            if (this.length == 0 && temp3.length == 0) {
                return true;
            }
            Node temp = front; // points to front
            Node temp2 = temp3.front; // points to front of other list
            while (temp != null) {
                if (temp.data != temp2.data) {
                    return false;
                }
                temp = temp.next;
                temp2 = temp2.next;
            }
            return true;
        } else
            return false;
    }

    // Manipulation procedures
    void clear() // Resets this List to its original empty state.
    {

        cursorIndex = -1;
        length = 0;
        front = null;
        back = null;
        cursor = null;
    }

    void moveFront() // If List is non-empty, places the cursor under the front element,
    // otherwise does nothing.
    {
        if (length > 0) {
            cursor = front;
            cursorIndex = 0;
        }
    }

    void moveBack() // If List is non-empty, places the cursor under the back element,
    // otherwise does nothing.
    {
        if (length > 0) {
            cursor = back;
            cursorIndex = length - 1;
        }
    }

    void movePrev() // If cursor is defined and not at front, moves cursor one step toward
    // front of this List, if cursor is defined and at front, cursor becomes
    // undefined, if cursor is undefined does nothing.
    {
        if (cursor != front) {
            cursor = cursor.prev;
            cursorIndex--;
        } else if (cursor == front) {
            cursor = null;
            cursorIndex = -1;
        }
    }

    void moveNext() // If cursor is defined and not at back, moves cursor one step toward
    // back of this List, if cursor is defined and at back, cursor becomes
    // undefined, if cursor is undefined does nothing.
    {
        if (cursor != back) {
            cursor = cursor.next;
            cursorIndex++;
        } else if (cursor.next == null) {
            cursor = null;
            cursorIndex = -1;
        }
    }

    void prepend(Object data) // Insert new element into this List. If List is non-empty,
    // insertion takes place before front element.
    {
        if (length > 0) {
            Node temp = new Node(data); // temp variable to adjust pointers
            front.prev = temp;
            temp.next = front;
            front = temp;
            length++;
            if (cursorIndex != -1) {
                cursorIndex++;
            }
        } else // if list is empty
        {
            front = new Node(data);
            back = front;
            length++;
        }
    }

    void append(Object data) // Insert new element into this List. If List is non-empty,
    // insertion takes place after back element.
    {
        if (length > 0) {
            Node temp = new Node(data); // temp variable to adjust pointers
            back.next = temp;
            temp.prev = back;
            back = temp;
            length++;
        } else // if list is empty
        {
            back = new Node(data);
            front = back;
            length++;
        }
    }

    void insertBefore(Object data) // Insert new element before cursor.
    // Pre: length()>0, index()>=0
    {
        if (cursor == front) {
            prepend(data);
        } else {
            Node temp = new Node(data); // temp variable to adjust pointers
            temp.next = cursor;
            temp.prev = cursor.prev;
            (cursor.prev).next = temp;
            cursor.prev = temp;
            length++;

            if (cursor != null) {
                cursorIndex++;
            }
        }
    }

    void insertAfter(Object data) // Inserts new element after cursor.
    // Pre: length()>0, index()>=0
    {
        if (cursor == back) {
            append(data);
        } else {
            Node temp = new Node(data); // temp variable to adjust pointers
            temp.prev = cursor;
            temp.next = cursor.next;
            (cursor.next).prev = temp;
            cursor.next = temp;
            length++;
        }
    }

    void deleteFront() // Deletes the front element. Pre: length()>0
    {
        if (cursor == front) {
            cursor = null;
            cursorIndex = -1;
        } else {
            cursorIndex--;
        }
        front = front.next;
        front.prev = null;
        length--;
    }

    void deleteBack() // Deletes the back element. Pre: length()>0
    {
        if (cursor == back) {
            cursor = null;
            cursorIndex = -1;
        }
        back = back.prev;
        if (back != null) {
            back.next = null;
        }
        length--;
    }

    void delete() // Deletes cursor element, making cursor undefined.
    // Pre: length()>0, index()>=0
    {
        if (this.length == 1)
        {
            front = back = null;
        }
        else if (cursor == front)
        {
            deleteFront();
        }
        else if (cursor == back)
        {
            deleteBack();
        }
        else
        {
            (cursor.prev).next = cursor.next;
            (cursor.next).prev = cursor.prev;
            cursor = null;
            cursorIndex = -1;
            length--;
        }
    }

    // Other methods
    public String toString() // Overrides Object's toString method. Returns a String
    // representation of this List consisting of a space
    // separated sequence of integers, with front on left.
    {
        Node temp = front;
        String output = "";
        while (temp != null)
        {
            output = output + temp.data;
            if (temp.next != null)
            {
                output += " ";
            }
            temp = temp.next;
        }
        return output;
    }
}
