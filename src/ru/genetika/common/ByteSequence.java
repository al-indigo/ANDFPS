package ru.genetika.common;


public class ByteSequence implements ISequence {
	private String name = null;
	// private byte[] seq = null;
	private ByteSequenceElement[] sequence = null;

	public ByteSequence(String name, byte[] bytesequence) {
		this(bytesequence);
		this.name = name;
	}

	public ByteSequence(byte[] bytesequence) {
		// this.seq = seq;
		sequence = new ByteSequenceElement[bytesequence.length];
		for (int i = 0; i < bytesequence.length; i++) {
			sequence[i] = new ByteSequenceElement(bytesequence[i]);
		}
	}

	public ByteSequence(String string) {
		/*
		 * byte[] seq = new byte[string.length()];
		 * Arrays.fill(seq, (byte)'\0');
		 * // from Andreas, strange thing
		 * for (int i = 0; i < string.length();
		 * i++) { seq[i] = (byte)string.charAt(i); } this.seq = seq;
		 */

		sequence = new ByteSequenceElement[string.length()];
		for (int i = 0; i < string.length(); i++) {
			sequence[i] = new ByteSequenceElement((byte) string.charAt(i));
		}
	}


	public String getName() {
		return name;
	}

	/*
	 * public byte[] getSequence() { return seq; }
	 */

	public int getLength() {
		return sequence.length;
	}

	@Override
	public ISequenceElement get(int i) {
		if (i < sequence.length) {
			return sequence[i];
		}

		return null;
	}
}
