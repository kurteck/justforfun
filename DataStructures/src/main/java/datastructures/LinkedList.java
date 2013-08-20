package datastructures;
public class LinkedList<E> {

	int size = 0;
	ListNode<E> header = new ListNode<E>(null, null, null);
	
	private ListNode<E> getHead() {
		return header.next;
	}
	
	private ListNode<E> getTail() {
		return header.prev;
	}
	
	private void setHead(ListNode<E> newHead) {
		header.next = newHead;
	}
	
	private void setTail(ListNode<E> newTail) {
		header.prev = newTail;
	}
	
	/**
	 * Inserts the given element at the head of the list.  
	 * Note that null values are allowed.
	 * 
	 * Runtime is O(1).
	 * 
	 * @param element
	 */
	public void addToHead(E element) {
		
		ListNode<E> newHead = new ListNode<E>(element, null, null);
		if (getHead() != null) {
			newHead.next   = getHead();  // set newHead to current head of list
			getHead().prev = newHead;    // point current head of list back to newHead
		}
		setHead(newHead);			// finally set head to newHead
		
		if (getTail() == null) {
			setTail(newHead);
		}
		
		size++;
	}
	
	
	/**
	 * Inserts the given element at the end of the list.  
	 * Note that null values are allowed.
	 * 
	 * Runtime is O(1).
	 * 
	 * @param element
	 */
	public void addToTail(E element) {
		
		ListNode<E> newTail = new ListNode<E>(element, null, null);
		if (getTail() != null) {
			getTail().next = newTail;
			newTail.prev   = getTail();
		}
		setTail(newTail);
		
		
		if (getHead() == null) {
			setHead(newTail);
		}
		
		size++;
	}

	
	/**
	 * Searches the list sequentailly for the given element.  
	 * Returns true if found and removed; false otherwise.
	 * 
	 * Runtime is O(N).
	 * 
	 * @param element
	 * @return
	 */
	public boolean remove(E element) {
		
		ListNode<E> runner = getHead();
		while (runner != null) {
			
			if (runner.element.equals(element)) {
				remove(runner);
				return true;
			}
			else {
				runner = runner.next;
			}
		}
		
		return false;
	}

	
	/**
	 * Removes this node from the list and updates related references
	 * 
	 * @param listNode
	 */
	private E remove(ListNode<E> listNode) {
		
		if (listNode == null) {
			return null;
		}
		
		if (listNode.next != null) {
			listNode.next.prev = listNode.prev;
		}
		
		if (listNode.prev != null) {
			listNode.prev.next = listNode.next;
		}

		if (listNode == getHead()) {
			setHead(listNode.next);
		}
		
		if (listNode == getTail()) {
			setTail(listNode.prev);
		}
		
		size--;
		
		return listNode.element;
	}
	
	
	
	/**
	 * Returns the object at the front of the list.
	 * 
	 * @return
	 */
	public E removeHead() {
		return remove(getHead());
	}

	
	/**
	 * Returns the object at the end of the list.
	 * 
	 * @return
	 */
	public E removeTail() {
		return remove(getTail());
	}
	
	
	/**
	 * Searches the list sequentailly for the given element.  
	 * Returns true if found; false otherwise.
	 * 
	 * Runtime is O(N).
	 * 
	 * @param element
	 * @return
	 */
	public boolean contains(E element) {
		
		ListNode<E> runner = getHead();
		while (runner != null) {
			
			if (runner.element.equals(element)) {
				return true;
			}
			else {
				runner = runner.next;
			}
		}
		
		return false;
	}

	
	/**
	 * Returns the element at the specified index
	 * 
	 * Runtime is O(N).
	 * 
	 * @param element
	 * @return
	 */
	public E get(int index) {
		
		if (index < 0 || (index >= size)) {
			return null;
		}
		
		ListNode<E> runner;
		int direction =  index < (size / 2) ? 1 : -1;
		if (direction > 0) {
			runner = getHead();
			for (int i=0; i < index; i++) {
				runner = runner.next;
			}
		}
		else {
			runner = getTail();
			for (int i=0; i < (size-1-index); i++) {
				runner = runner.prev;
				
			}
		}
		
		return runner.element;
	}
	
	
	/**
	 * True if this list has zero elements.
	 * @return
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		ListNode<E> runner = getHead();
		while (runner != null) {
			sb.append(runner.element).append(",");
			runner = runner.next;
		}

		
		return sb.toString();
	}
	

	// Or delcare as "private class ListNode" (Why is <E> not needed ?!)
	private static class ListNode<E> { 
		E element;
		ListNode<E> prev;
		ListNode<E> next;
		
		public ListNode(E element, ListNode<E> prev, ListNode<E> next) { 
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
	}
}