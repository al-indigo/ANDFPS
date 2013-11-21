package ru.genetika.pwm.utilities;

public class Word {

	//seq fwStrand
	private String word = null;
	private float scoreFw;
	private float scoreRev;
	
	public Word(String word, float scoreFw, float scoreRev)
	{
		this.word = word;
		this.scoreFw = scoreFw;
		this.scoreRev = scoreRev;
	}

	public String getWord() {
		return word;
	}

	public float getScoreFw() {
		return scoreFw;
	}

	public float getScoreRev() {
		return scoreRev;
	}
	
}
