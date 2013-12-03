package ru.genetika.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The class now represents one implementation of alphabet for the genome
 * analysis, based on bytes.
 * @author ilya
 *
 */
public class Alphabet implements Iterable<ISequenceElement> {
	private static List<ISequenceElement> alphabet;

	private static final int HALF_BYTE_RANGE = 128;

	/**
	 * By default the alphabet will consist of bytes corresponding to chars.
	 * Byte values range from -128 to 127 (inclusive).
	 */
	public Alphabet()	{
		alphabet = new ArrayList<ISequenceElement>(256);
		for (int i=0 ; i < 255 ; i++)	{
			alphabet.add(i, new ByteSequenceElement((byte)i));
		}
	}


	public static ISequenceElement getLetter(byte b)	{
		int i = HALF_BYTE_RANGE + b;
		return alphabet.get(i);
	}

	public static int length()	{
		return alphabet.size();
	}


	@Override
	public Iterator<ISequenceElement> iterator() {
		return alphabet.iterator();
	}
}
