package ru.genetika.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FastaByteSequenceReader extends FastaSequenceReader {

	public FastaByteSequenceReader(File file) {
		super(file);
	}
	
	@Override
	public ByteSequence getSequence() throws IOException {
		return (ByteSequence) super.getSequence();
	}

	@Override
	protected ByteSequence readFile() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String name = null;
		byte[] seq = new byte[(int) file.length()];
		Arrays.fill(seq, (byte)'\0');
		int pos = 0;
		while(reader.ready())
		{
			int ci = reader.read();
			char c = (char) ci;
			if(c == '>')
			{
				name = reader.readLine();
			}
			else if(c != '\n' && c != '\r')
			{
				seq[pos] = (byte)Character.toUpperCase(c);
				++pos;
			}
		}
		reader.close();
		return new ByteSequence(name, seq);
	}
}
