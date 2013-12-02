package org.arabidopsis.ahocorasick;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import ru.genetika.common.ISequenceElement;


/**
   Linked list implementation of the EdgeList should be less memory-intensive.
   
   !!ATTENTION!! Keys can be non-unique!
 */


class SparseEdgeList implements ILabeledEdgesList {
	static private class ListNode {
		//byte b;
		ISequenceElement e;
		State state;
		ListNode next;

		public ListNode(ISequenceElement e, State s, ListNode nextListNode) {
			//this.b = b;
			this.e = e;
			state = s;
			next = nextListNode;
		}
	}


	private ListNode head = null;


	/*public State get(byte b) {
		Cons c = head;
		while (c != null) {
			if (c.b == b)
				return c.s;
			c = c.next;
		}
		return null;
	}

	public void put(byte b, State s) {
		this.head = new Cons(b, s, head);
	}*/

	@Override
	public State get(ISequenceElement e) {
		ListNode listNode = head;
		while (listNode != null) {
			if (e.equals(listNode.e))	{
				return listNode.state;
			}
			listNode = listNode.next;
		}

		return null;
	}


	@Override
	public void put(ISequenceElement e, State s) {
		head = new ListNode(e, s, head);
	}


	/**
	 * !!ATTENTION!! Keys can be non-unique!
	 */
	@Override
	public Collection<ISequenceElement> keys() {
		List<ISequenceElement> keys = new ArrayList<ISequenceElement>(getLength());

		ListNode listNode = head;
		while (listNode != null) {
			keys.add(listNode.e);
			listNode = listNode.next;
		}

		return keys;
	}

	private int getLength()	{
		int length = 0;
		ListNode listNode = head;
		while (listNode != null) {
			length++;
			listNode = listNode.next;
		}
		return length;
	}


	/*public byte[] keySet() {
		int length = 0;
		ListNode c = head;
		while (c != null) {
			length++;
			c = c.next;
		}
		byte[] result = new byte[length];
		c = head;
		int j = 0;
		while (c != null) {
			result[j] = c.b;
			j++;
			c = c.next;
		}
		return result;
	}*/
}
