package org.arabidopsis.ahocorasick;

import java.util.Collection;

import ru.genetika.common.ISequenceElement;

/**
   Simple interface for mapping bytes to States.
 */
interface ILabeledEdgesList {
	/*State get(byte ch);
	void put(byte ch, State state);
	byte[] keys();*/

	State get(ISequenceElement e);
	void put(ISequenceElement e, State s);

	/**
	 * !!ATTENTION!! Keys can be non-unique!
	 */
	Collection<ISequenceElement> keys();
}
