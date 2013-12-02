package org.arabidopsis.ahocorasick;

import java.util.List;

import ru.genetika.common.ISequence;


/**
   <p>Holds the result of the search so far.  Includes the outputs where
   the search finished as well as the last index of the matching.</p>

   <p>(Internally, it also holds enough state to continue a running
   search, though this is not exposed for public use.)</p>
 */
public class SearchResult {
	private State lastMatchedState;
	//byte[] bytes;
	private ISequence sequence;
	private int lastIndex;

	/*SearchResult(State s, byte[] bs, int i) {
		this.lastMatchedState = s;
		this.bytes = bs;
		this.lastIndex = i;
	}*/
	public SearchResult(State s, ISequence sequence, int i) {
		this.lastMatchedState = s;
		this.sequence = sequence;
		this.lastIndex = i;
	}


	public State getLastMatchedState()	{
		return lastMatchedState;
	}

	/**
   	  Returns a list of the outputs of this match.
	 */
	public List getOutputs() {
		return lastMatchedState.getOutputs();
	}

	public ISequence getSequence()	{
		return sequence;
	}

	/**
	  Returns the index where the search terminates.  Note that this
	  is one byte after the last matching character.
	 */
	public int getLastIndex() {
		return lastIndex;
	}
}
