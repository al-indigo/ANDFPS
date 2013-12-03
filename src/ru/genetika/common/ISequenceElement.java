package ru.genetika.common;

public interface ISequenceElement {

	/**
	 * Checks whether a sequence element equals to a char <code>c</code>.
	 */
	public boolean equals(char c);

	/**
	 * Returns the value of the specified element as a byte.
	 */
	public byte byteValue();

	/**
	 * Returns the value of the specified element as an int (integer).
	 */
	public int intValue();
}
