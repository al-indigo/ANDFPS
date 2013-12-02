package ru.genetika.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Alphabet implements Iterable<ISequenceElement> {
	private List<ISequenceElement> alphabet;

	/**
	 * By default the alphabet will consist of bytes from 0 to 255 (correspond to chars).
	 */
	public Alphabet()	{
		alphabet = new LinkedList<ISequenceElement>();
		for (int i=0 ; i < 255 ; i++)	{
			alphabet.add(new ByteSequenceElement((byte)i));
		}
	}

	@Override
	public Iterator<ISequenceElement> iterator() {
		return alphabet.iterator();
	}
}
