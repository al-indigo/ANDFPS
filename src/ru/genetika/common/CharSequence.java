package ru.genetika.common;

public class CharSequence implements ISequence {
	private String name = null;
	private char[] seq = null;
	
	public CharSequence(String name, char[] seq)
	{
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
}
