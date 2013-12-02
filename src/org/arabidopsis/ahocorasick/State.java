package org.arabidopsis.ahocorasick;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.genetika.common.ISequence;
import ru.genetika.common.ISequenceElement;


/**
   A state represents an element in the Aho-Corasick tree.
 */
class State {

	// Arbitrarily chosen constant.  If this state ends up getting
	// deeper than THRESHOLD_TO_USE_SPARSE, then we switch over to a
	// sparse edge representation.  I did a few tests, and there's a
	// local minima here.  We may want to choose a more sophisticated
	// strategy.
	private static final int THRESHOLD_TO_USE_SPARSE = 3;

	private int depth;
	//private EdgeList edgeList;
	private State fail;
	private List outputs;
	private Map<ISequenceElement, State> edges;

	public State(int depth) {
		this.depth = depth;
		/*if (depth > THRESHOLD_TO_USE_SPARSE)	{
			this.edgeList = new SparseEdgeList();
		}
		else	{
			this.edgeList = new DenseEdgeList();
		}*/
		edges = new HashMap<ISequenceElement, State>();
		//edges = new TreeMap<ISequenceElement, State>();

		this.fail = null;
		this.outputs = new ArrayList();
	}


	/*public State extend(byte b) {
		if (this.edgeList.get(b) != null)	{
			return this.edgeList.get(b);
		}

		State nextState = new State(this.depth + 1);
		this.edgeList.put(b, nextState);

		return nextState;
	}*/
	public State extend(ISequenceElement e) {
		if (edges.containsKey(e))	{
			return edges.get(e);
		}

		State nextState = new State(depth + 1);
		edges.put(e, nextState);

		return nextState;
	}


	/*public State extendAll(byte[] bytes) {
		State state = this;
		for (int i = 0 ; i < bytes.length ; i++) {
			if (state.edgeList.get(bytes[i]) != null)
				state = state.edgeList.get(bytes[i]);
			else
				state = state.extend(bytes[i]);
		}
		return state;
	}*/
	public State extendAll(ISequence sequence) {
		State state = this;
		for (int i = 0 ; i < sequence.getLength() ; i++) {
			ISequenceElement element = sequence.get(i);
			if (state.edges.containsKey(element))	{
				state = state.edges.get(element);
			}
			else	{
				state = state.extend(element);
			}
		}

		return state;
	}


	/**
	   Returns the size of the tree rooted at this State.  Note: do
	   not call this if there are loops in the edgelist graph, such as
	   those introduced by AhoCorasick.prepare().
	 */
	/*public int size() {
		byte[] keys = edgeList.keys();
		int result = 1;
		for (int i = 0; i < keys.length; i++)	{
			result += edgeList.get(keys[i]).size();
		}
		return result;
	}*/
	public int size() {
		Set<ISequenceElement> keys = edges.keySet();
		int result = 1;
		for (ISequenceElement key : keys)	{
			result += edges.get(key).size();
		}
		return result;
	}


	/**
	 * Tells whether there're edges from this state to other states of automaton labeled with <code>e</code>.
	 */
	public boolean hasEdge(ISequenceElement e)	{
		return edges.containsKey(e);
	}

	/*public State get(byte b) {
		return this.edgeList.get(b);
	}*/
	public State get(ISequenceElement e) {
		return edges.get(e);
	}

	/*public void put(byte b, State s) {
		this.edgeList.put(b, s);
	}*/
	public void put(ISequenceElement e, State s) {
		edges.put(e, s);
	}

	/*public byte[] keys() {
		return this.edgeList.keys();
	}*/
	public Set<ISequenceElement> keys() {
		return edges.keySet();
	}


	public State getFail() {
		return this.fail;
	}

	public void setFail(State f) {
		this.fail = f;
	}


	public void addOutput(Object o) {
		outputs.add(o);
	}


	public List getOutputs() {
		return outputs;
	}
}
