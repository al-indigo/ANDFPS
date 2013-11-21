package ru.genetika.pwm.scanner;

public class ResultPrinter implements IPwmScannerResultListener {
	
	public ResultPrinter() { }
	
	public void receiveScore(int pos, float scoreFw, float scoreRv) {
		System.out.println(pos + "\t" + scoreFw + "\t" + scoreRv);
	}
}
