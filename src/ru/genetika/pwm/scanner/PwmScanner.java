package ru.genetika.pwm.scanner;

import ru.genetika.common.Alphabet;
import ru.genetika.pwm.Pwm;

public abstract class PwmScanner {

	protected IPwmScannerResultListener resultListener = null;
	protected float minThreshold = -999999;
	protected Pwm pwmFw = null;

	public PwmScanner() {
		super();
	}

	public void setResultListener(IPwmScannerResultListener lis) {
		resultListener = lis;
	}

	public void setPwm(Pwm pwm) {
		this.pwmFw = pwm;
	}

	public void setMinThreshold(Float t) {
		if (t == null)
		{
			minThreshold = -999999; //broken
		}
		else
		{
			minThreshold = t;
		}
	}

	public abstract void scan();

  public void prepare(Alphabet alphabet) { }
}