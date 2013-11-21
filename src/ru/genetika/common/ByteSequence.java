package ru.genetika.common;

import java.util.Arrays;

public class ByteSequence implements ISequence {
	private String name = null;
	private byte[] seq = null;
	
	public ByteSequence(String name, byte[] seq)
	{
		this.name = name;
		this.seq = seq;
	}

  public ByteSequence(String sequence) {
    byte[] seq = new byte[sequence.length()];
		Arrays.fill(seq, (byte)'\0'); // from Andreas, strange thing
		for (int i = 0; i < sequence.length(); i++) {
      seq[i] = (byte)sequence.charAt(i);
    }
    this.seq = seq;
  }

  public String getName() {
		return name;
	}

	public byte[] getSequence() {
		return seq;
	}
}
