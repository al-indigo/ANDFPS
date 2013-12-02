package ru.genetika.common;

public class ByteSequenceElement implements ISequenceElement {
	private byte b = '\0';

	public ByteSequenceElement(byte b)	{
		this.b = b;
	}


	@Override
	public boolean equals(char c)	{
		return b == (byte)c;
	}

	@Override
	public byte byteValue() {
		return b;
	}

	@Override
	public int intValue() {
		return b;
	}


	/**
	 * Overrides the default equals() method.
	 */
	@Override
	public boolean equals(Object obj)	{
		if (obj instanceof ByteSequenceElement)	{
			return b == ((ByteSequenceElement)obj).b;
		}

		return super.equals(obj);
	}

	/**
	 * Overrides the default hashCode() method, so that the element
	 * could be effectively used as a key in a map.
	 */
	@Override
	public int hashCode()	{
		return (int) b;
	}
}
