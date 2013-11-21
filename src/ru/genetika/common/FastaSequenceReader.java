package ru.genetika.common;
import java.io.File;
import java.io.IOException;



public abstract class FastaSequenceReader {
	protected File file = null;
	
	public FastaSequenceReader(File file)
	{
		this.file = file;
	}
	
	public ISequence getSequence() throws IOException
	{
		return readFile();
	}
	
	protected abstract ISequence readFile() throws IOException;
}
