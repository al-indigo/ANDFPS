package ru.genetika.pwm.utilities;

import java.util.Arrays;

import ru.genetika.pwm.Pwm;

public class PwmWordGenerator {

	private Pwm pwmFw = null;
	private Pwm pwmRev = null;
	private float[] lookAheadScoresFw = null;
	private float[] lookAheadScoresRev = null;
	private float threshold = 0;
	private IPwmWordListener listener = null;
	
	
	public PwmWordGenerator(Pwm pwm)
	{
		this.pwmFw = pwm;
		this.pwmRev = this.pwmFw.getReverseComplementMatrix();
		lookAheadScoresFw = calculateLookAheadScores(pwmFw);
		lookAheadScoresRev = calculateLookAheadScores(pwmRev);
	}
	
	public void setListener(IPwmWordListener listener) {
		this.listener = listener;
	}
	
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public void generateWords()
	{
		calculateScoresRecursive(0, 0, new int[pwmFw.size()], 0);
	}
	
	private void calculateScoresRecursive(float scoreFw, float scoreRev, int[] seq, int depth)
	{
		if(depth == pwmFw.size())
		{
			if(scoreFw >= threshold || scoreRev >= threshold)
			{
				char[] wordFw = new char[seq.length];
				for(int i = 0; i < seq.length; ++i)
				{
					wordFw[i] = pwmFw.getLetters()[seq[i]];
				}
				listener.receiveWord(new Word(new String(wordFw), scoreFw, scoreRev));
			}
		}
		else
		{
			for(int i = 0; i < pwmFw.getNumCols(); ++i)
			{
				if(scoreFw + pwmFw.get(depth, i) + lookAheadScoresFw[depth] >= threshold ||
				   scoreRev + pwmRev.get(depth, i) + lookAheadScoresRev[depth] >= threshold)
				{
					int[] seqCopy = seq.clone();
					seqCopy[depth] = i;
					calculateScoresRecursive(scoreFw + pwmFw.get(depth, i), scoreRev + pwmRev.get(depth, i), seqCopy, depth + 1);
				}
			}
		}
	}
	
	private float[] calculateLookAheadScores(Pwm pwm)
	{
		float[] ret = new float[pwm.size()];
		Arrays.fill(ret, 0);
		for(int i = pwm.size() - 1; i > 0; --i)
		{
			float max = Float.MIN_VALUE;
			for(int j = 0; j < pwm.getNumCols(); ++j)
			{
				if(max < pwm.get(i, j))
					max = pwm.get(i, j);
			}
			ret[i - 1] = ret[i] + max;
		}
		return ret;
	}
}
