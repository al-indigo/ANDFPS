package ru.genetika.pwm.utilities;

import java.util.Arrays;
import java.util.Iterator;

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
//		calculateScoresRecursive(0, 0, new int[pwmFw.size()], 0);
        calculateScoresStraight();
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

    private void calculateScoresStraight ()
    {
        PwmWordPaths path = new PwmWordPaths(pwmFw);
        do {
            byte[] tempPath = path.next();
            float currentFwScore = 0;
            float currentRevScore = 0;
            boolean needBreak = false;
            for (int depth = 0; depth < pwmFw.size(); depth++) {
                 currentFwScore += pwmFw.get(depth, tempPath[depth]);
                 currentRevScore += pwmRev.get(depth, tempPath[depth]);
                /* here we check if we could ever get more scores on current path */
                 if(currentFwScore + lookAheadScoresFw[depth] < threshold &&
                    currentRevScore + lookAheadScoresRev[depth] < threshold)
                 {
                    needBreak = true;
                    /* if we can not; we increase path[depth] by one and null all after that */
                    path.makeHop(depth);
                    break;
                 }
            }
            if (needBreak) continue;
            if (currentFwScore >= threshold || currentRevScore >= threshold) {
                char[] wordFw = new char[pwmFw.size()];
                for(int i = 0; i < pwmFw.size(); i++) {
                    wordFw[i] = pwmFw.getLetters()[tempPath[i]];
                }
                /* TODO: get rid of String -- we don't need it at all */
                listener.receiveWord(new Word(new String(wordFw), currentFwScore, currentRevScore));
            }

        } while (path.hasNext());
    }

    private float[] calculateLookBehindScores(Pwm pwm) {
        float[] ret = new float[pwm.size()];
        Arrays.fill(ret, 0);
        for(int i = 0; i < pwm.size(); i++)
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
