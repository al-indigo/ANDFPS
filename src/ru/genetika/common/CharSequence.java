package ru.genetika.common;

public class CharSequence implements ISequence {
	private String name = null;
	private char[] seq = null;


	public CharSequence(String name, char[] seq)	{
		this.name = name;
		this.seq = seq;
	}

	public CharSequence(String sequence) {
		this.seq = sequence.toCharArray();
	}


	public String getName() {
		return name;
	}

	public char[] getSequence() {
		return seq;
	}

	public int getLength()	{
		return seq.length;
	}

	@Override
	public ISequenceElement get(int i) {
		// TODO A stub, needs a normal implementation.
		throw new UnsupportedOperationException();
	}
}
