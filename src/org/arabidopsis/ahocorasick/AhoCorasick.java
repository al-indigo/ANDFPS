package org.arabidopsis.ahocorasick;

import java.util.Iterator;
import java.util.Set;

import ru.genetika.common.Alphabet;
import ru.genetika.common.ByteSequence;
import ru.genetika.common.ISequence;
import ru.genetika.common.ISequenceElement;


/**
   <p>An implementation of the Aho-Corasick string searching
   automaton.  This implementation of the <a
   href="http://portal.acm.org/citation.cfm?id=360855&dl=ACM&coll=GUIDE"
   target="_blank">Aho-Corasick</a> algorithm is optimized to work
   with bytes.</p>

   <p>
   Example usage:
   <code><pre>
       AhoCorasick tree = new AhoCorasick();
       tree.add("hello".getBytes(), "hello");
       tree.add("world".getBytes(), "world");
       tree.prepare();

       Iterator searcher = tree.search("hello world".getBytes());
       while (searcher.hasNext()) {
           SearchResult result = searcher.next();
           System.out.println(result.getOutputs());
           System.out.println("Found at index: " + result.getLastIndex());
       }
   </pre></code>
   </p>
 */
public class AhoCorasick {
    private State root;
    private boolean prepared;

    public AhoCorasick() {
    	this.root = new State(0);
    	this.prepared = false;
    }


    /**
       Adds a new keyword with the given output.  During search, if
       the keyword is matched, output will be one of the yielded
       elements in SearchResults.getOutputs().
     */
    public void add(byte[] keyword, Object output) {
    	if (prepared)	{
    		throw new IllegalStateException("can't add keywords after prepare() is called");
    	}
    	//State lastState = this.root.extendAll(keyword);
    	State lastState = root.extendAll(new ByteSequence(keyword));
    	lastState.addOutput(output);
    }


    /**
       Prepares the automaton for searching.  This must be called
       before any searching().
     */
    public void prepare(Alphabet alphabet) {
    	this.prepareFailTransitions(alphabet);
    	this.prepared = true;
    }


    /**
       Starts a new search, and returns an Iterator of SearchResults.
     */
    public Iterator search(ISequence sequence)	{	//byte[] bytes) {
    	return new Searcher(this, this.startSearch(sequence));	//bytes));
    }



    /** DANGER DANGER: dense algorithm code ahead.  Very order
	dependent.  Initializes the fail transitions of all states
	except for the root.
    */
    /*private void prepareFailTransitions() {
    	Queue q = new Queue();
    	for(int i = 0; i < 256; i++)	{
    		if (root.get((byte) i) != null) {
    			root.get((byte) i).setFail(root);
    			q.add(root.get((byte) i));
    		}
    	}

    	this.prepareRoot();

    	while (! q.isEmpty()) {
    		State state = q.pop();
    		byte[] keys = state.keys();
    		for (int i = 0; i < keys.length; i++) {
    			State r = state;
    			byte a = keys[i];
    			State s = r.get(a);
    			q.add(s);
    			r = r.getFail();
    			while (r.get(a) == null)	{
    				r = r.getFail();
    			}
    			s.setFail(r.get(a));
    			s.getOutputs().addAll(r.get(a).getOutputs());
    		}
    	}
    }*/
    private void prepareFailTransitions(Alphabet alphabet) {
    	Queue q = new Queue();
    	for (ISequenceElement letter : alphabet)	{
    		if (root.hasEdge(letter)) {
    			root.get(letter).setFail(root);
    			q.add(root.get(letter));
    		}
    	}

    	this.prepareRoot(alphabet);

    	while (!q.isEmpty())	{
    		State state = q.pop();
    		Set<ISequenceElement> keys = state.keys();
    		for (ISequenceElement key : keys) {
    			State r = state;
    			State s = r.get(key);
    			q.add(s);
    			r = r.getFail();
    			while (!r.hasEdge(key))	{
    				r = r.getFail();
    			}

    			r = r.get(key);
    			s.setFail(r);
    			s.getOutputs().addAll(r.getOutputs());
    		}
    	}
    }


    /** Sets all the out transitions of the root to itself, if no
	transition yet exists at this point.
    */
    /*private void prepareRoot() {
    	for(int i = 0; i < 256; i++)
    		if (root.get((byte) i) == null)
    			root.put((byte) i, root);
    }*/
    private void prepareRoot(Alphabet alphabet) {
    	for (ISequenceElement letter : alphabet)	{
    		if (!root.hasEdge(letter))	{
    			root.put(letter, root);
    		}
    	}
    }



    /**
       Returns the root of the tree.  Package protected, since the
       user probably shouldn't touch this.
     */
    State getRoot() {
    	return root;
    }

    

    /**
       Begins a new search using the raw interface.  Package protected.
     */
    private SearchResult startSearch(ISequence sequence)	{	//byte[] bytes) {byte[] bytes) {
    	if (!prepared)	{
    		throw new IllegalStateException("can't start search until prepare()");
    	}

    	//return continueSearch(new SearchResult(this.root, bytes, 0));
    	return continueSearch(new SearchResult(root, sequence, 0));
    }



    /**
       Continues the search, given the initial state described by the
       lastResult.  Package protected.
       Modification: also stop search when character == >>\0<<
     */
    /*SearchResult continueSearch(SearchResult lastResult) {
    	byte[] bytes = lastResult.bytes;
    	State state = lastResult.getLastMatchedState();
    	for (int i = lastResult.getLastIndex() ; i < sequence.getLength() && bytes[i] != (byte)'\0' ; i++) {
    		byte b = bytes[i];
    		while (state.get(b) == null)	{
    			state = state.getFail();
    		}
    		state = state.get(b);

    		if (state.getOutputs().size() > 0)	{
    			return new SearchResult(state, sequence, i+1);
    		}
    	}
    	return null;
    }*/
    SearchResult continueSearch(SearchResult lastResult) {
    	ISequence sequence = lastResult.getSequence();
    	State state = lastResult.getLastMatchedState();
    	for (int i = lastResult.getLastIndex() ; i < sequence.getLength() && !sequence.get(i).equals('\0') ; i++) {
    		ISequenceElement element = sequence.get(i);
    		while (!state.hasEdge(element))	{
    			state = state.getFail();
    		}
    		state = state.get(element);

    		if (state.getOutputs().size() > 0)	{
    			return new SearchResult(state, sequence, i+1);
    		}
    	}
    	return null;
    }
}
