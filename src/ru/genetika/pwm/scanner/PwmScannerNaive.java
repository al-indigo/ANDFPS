package ru.genetika.pwm.scanner;

import ru.genetika.common.CharSequence;
import ru.genetika.pwm.Pwm;

public class PwmScannerNaive extends PwmScanner {

	char[] sequence = null;
	protected Pwm pwmRv = null;
	
	public PwmScannerNaive()
	{
	}
	
	@Override
	public void setPwm(Pwm pwm) {
		super.setPwm(pwm);
		pwmRv = pwmFw.getReverseComplementMatrix();
	}

	public void setSequence(CharSequence sequence) {
		this.sequence = sequence.getSequence();
	}
	
	public void scan() {
		for(int i = 0; i + pwmFw.size() <= sequence.length && sequence[i + pwmFw.size() - 1] != '\0' ; ++i)
		{
			float scoreFw = 0;
			float scoreRv = 0;
			for(int j = 0; j < pwmFw.size(); ++j)
			{
				scoreFw += pwmFw.get(j, sequence[j+i]);
				scoreRv += pwmRv.get(j, sequence[j+i]);
			}
			if(scoreFw >= minThreshold || scoreRv >= minThreshold)
			{
				resultListener.receiveScore(i, scoreFw, scoreRv);
			}
		}
	}

}
