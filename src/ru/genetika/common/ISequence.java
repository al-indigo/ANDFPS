package ru.genetika.common;

public interface ISequence {

	public String getName();

	//public Object getSequence();

	public int getLength();

	/**
	 * Get the i'th element of the sequence (zero-based).
	 */
	public ISequenceElement get(int i);
}