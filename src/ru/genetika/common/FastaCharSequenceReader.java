package ru.genetika.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FastaCharSequenceReader extends FastaSequenceReader {

	public FastaCharSequenceReader(File file) {
		super(file);
	}
	
	@Override
	public CharSequence getSequence() throws IOException {
		return (CharSequence) super.getSequence();
	}

	@Override
	protected CharSequence readFile() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String name = null;
		char[] seq = new char[(int) file.length()];
		Arrays.fill(seq, '\0');
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
				seq[pos] = Character.toUpperCase(c);
				++pos;
			}
		}
		reader.close();
		return new CharSequence(name, seq);
	}
}
