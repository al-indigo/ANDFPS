package org.arabidopsis.ahocorasick;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import ru.genetika.common.Alphabet;
import ru.genetika.common.ISequenceElement;


/**
   Represents an EdgeList by using a single array.  Very fast lookup
   (just an array access), but expensive in terms of memory.
   
   !!ATTENTION!! Keys can be non-unique!
 */

class DenseEdgeList implements ILabeledEdgesList {    
	private State[] array;
	private List<ISequenceElement> keys;

	public DenseEdgeList() {
		/*this.array = new State[256];
		for (int i = 0; i < array.length; i++)
			this.array[i] = null;*/
		array = new State[Alphabet.length()];
		keys = new LinkedList<ISequenceElement>();
	}
    

    /**
       Helps in converting to dense representation.
     */
    /*public static DenseEdgeList fromSparse(SparseEdgeList list) {
    	byte[] keys = list.keys();
    	DenseEdgeList newInstance = new DenseEdgeList();
    	for (int i = 0; i < keys.length; i++) {
    		newInstance.put(keys[i], list.get(keys[i]));
    	}
    	return newInstance;
    }*/


    /*public State get(byte b) {
    	return this.array[(int) b & 0xFF];
    }


    public void put(byte b, State s) {
    	this.array[(int) b & 0xFF] = s;
    }*/


	@Override
	public State get(ISequenceElement e) {
		return array[(int) e.byteValue() & 0xFF];
	}


	@Override
	public void put(ISequenceElement e, State s) {
		array[(int) e.byteValue() & 0xFF] = s;
		keys.add(e);
	}


	/**
	 * !!ATTENTION!! Keys can be non-unique!
	 */
	@Override
	public Collection<ISequenceElement> keys() {
		return keys;
	}


	/*public byte[] keySet() {
    	int length = 0;
    	for(int i = 0; i < array.length; i++) {
    		if (array[i] != null)
    			length++;
    	}
    	byte[] result = new byte[length];
    	int j = 0;
    	for(int i = 0; i < array.length; i++) {
    		if (array[i] != null) {
    			result[j] = (byte) i;
    			j++;
    		}
    	}
    	return result;
    }*/
}
