package ru.genetika.pwm.scanner;

import org.arabidopsis.ahocorasick.AhoCorasick;
import org.arabidopsis.ahocorasick.SearchResult;
import ru.genetika.common.ByteSequence;
import ru.genetika.pwm.Pwm;
import ru.genetika.pwm.utilities.IPwmWordListener;
import ru.genetika.pwm.utilities.Word;
import ru.genetika.pwm.utilities.PwmWordGenerator;

import java.util.Iterator;

public class PwmScannerAhoC extends PwmScanner implements IPwmWordListener {

	byte[] sequence = null;
	boolean changed = false;
	AhoCorasick ahoC = null;
	
	@Override
	public void setMinThreshold(Float t) {
		super.setMinThreshold(t);
		changed = true;
	}
	
	@Override
	public void setPwm(Pwm pwm) {
		super.setPwm(pwm);
		changed = true;
	}
	
	public void setSequence(ByteSequence sequence) {
		this.sequence = sequence.getSequence();
	}

  public void prepare() {
    if(changed)
		{
			ahoC = new AhoCorasick();
			PwmWordGenerator wordGenerator = new PwmWordGenerator(pwmFw);
			wordGenerator.setThreshold(minThreshold);
			wordGenerator.setListener(this);
			wordGenerator.generateWords();
			ahoC.prepare();
			changed = false;
		}
  }

	@Override
	public void scan() {
		Iterator iter = ahoC.search(sequence);
		while(iter.hasNext())
		{
			SearchResult result = (SearchResult)iter.next();
			resultListener.receiveScore(result.getLastIndex() - pwmFw.size(), ((float[])result.getOutputs().get(0))[0], ((float[])result.getOutputs().get(0))[1]);
		}
	}

	public void receiveWord(Word word) {
		ahoC.add(word.getWord().getBytes(), new float[]{word.getScoreFw(), word.getScoreRev()});
	}	
}
