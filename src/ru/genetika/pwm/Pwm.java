package ru.genetika.pwm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Vector;
import java.util.Iterator;

/**
 * !!!!DNA only!!!!
 */

public class Pwm {
	private static int numCols = 4;
	private static char[] letters = new char[] {'A', 'C', 'G', 'T'};
	private Vector<float []> matrix = null;
	private String name = null;
	
	private Pwm(){}
	
	public Pwm(Reader reader) throws PwmFormatException, IOException
	{
		safeReadMatrix(reader);
	}	
	
	public int getNumCols() {
		return Pwm.numCols;
	}

	public String getName() {
		return name;
	}
	
	public float get (int i, int j)
	{
		return matrix.get(i)[j];
	}
	
	public float get(int i, char j)
	{
		int iLetter = Arrays.binarySearch(letters, j);
		if(iLetter < 0)
		{
			return (float) -999999999999.0; //broken in JAVA5 Float.NEGATIVE_INFINITY;
		}
		return get(i, iLetter);
	}
	
	public Pwm getReverseComplementMatrix()
	{
		Pwm ret = new Pwm();
		ret.matrix = new Vector<float[]>();
		for(int i = matrix.size() - 1; i >= 0; --i)
		{
			float[] line = new float[numCols];
			line[0] = matrix.get(i)[3];
			line[1] = matrix.get(i)[2];
			line[2] = matrix.get(i)[1];
			line[3] = matrix.get(i)[0];
			ret.matrix.add(line);
		}
		return ret;
	}
	
	public int size()
	{
		int size = 0;
		if(matrix != null)
		{
			size = matrix.size();
		}
		return size;
	}

	private void safeReadMatrix(Reader reader) throws PwmFormatException, IOException
	{
		try
		{
			readMatrix(reader);
		}
		catch(PwmFormatException e)
		{
			matrix = null;
			throw (e);
		}
		catch(IOException e)
		{
			name = null;
			throw (e);
		}
	}
	
	private void readMatrix (Reader reader) throws PwmFormatException, IOException
	{
		matrix = new Vector<float[]>();
		BufferedReader bReader = new BufferedReader(reader);
		try {
			while(bReader.ready())
			{
				String line = bReader.readLine();
				if(line.length() > 0)
				{
					if(line.matches(".*[A-Za-z]+.*"))
					{
						name = line;
					}
					else
					{	
						String[] tmp = line.split("\\s");
						if(tmp.length != numCols)
						{
							throw(new PwmFormatException("Wrong alphabet size."));
						}
						matrix.add(new float[Pwm.numCols]);
						for (int i = 0; i < tmp.length; ++i) {
							matrix.lastElement()[i] = Float.parseFloat(tmp[i]);
						}
					}
				}
			}
		} catch (IOException e) {
			throw (e);
		} catch (NumberFormatException e) {
			throw(new PwmFormatException("Not a number."));
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(IOException e)
			{
				//doNothing;
			}
		}
	}

	public char[] getLetters() {
		return letters;
	}
}
