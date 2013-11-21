package ru.genetika;// import java.io.BufferedWriter;

import ru.genetika.common.ByteSequence;
import ru.genetika.common.CharSequence;
import ru.genetika.pwm.Pwm;
import ru.genetika.pwm.PwmFormatException;
import ru.genetika.pwm.scanner.PwmScanner;
import ru.genetika.pwm.scanner.PwmScannerAhoC;
import ru.genetika.pwm.scanner.PwmScannerNaive;
import ru.genetika.pwm.scanner.ResultPrinter;

import java.io.*;
import java.util.ArrayList;

public class ANDFPS {

  public static void main(String[] args) throws IOException, PwmFormatException {
    if (args.length < 4) {
      System.out.println("Usage: <sequences.mfa> <mode=(n)aive|(a)hocorasick> <matrix> <threshold>");
      System.exit(1);
    }

    // init phase
    Pwm matrix = new Pwm(new FileReader(new File(args[2])));
    Float threshold = Float.parseFloat(args[3]);
    boolean naive = args[1].substring(0,1).equals("n");
    PwmScanner scanner = naive ? new PwmScannerNaive() : new PwmScannerAhoC();
    String[] sequences = loadSequences(args[0], matrix.size());

    scanner.setPwm(matrix);
    scanner.setMinThreshold(threshold);
    scanner.setResultListener(new ResultPrinter());
    scanner.prepare();
    System.out.println("preparation finished successfully; starting scan");

    for (int i = 0; i < sequences.length; i++) {
      if (naive)
        ((PwmScannerNaive)scanner).setSequence(new CharSequence(sequences[i].toUpperCase()));
      else {
        // ByteSequence bs1 = new FastaByteSequenceReader(new File("chr1.fasta")).getSequence();
        // ByteSequence bs2 = new ByteSequence(sequences[i]);
        // System.exit(2);
        ((PwmScannerAhoC)scanner).setSequence(new ByteSequence(sequences[i].toUpperCase()));
      }
      System.out.println("> " + i);
      scanner.scan();
    }

  }

  public static String[] loadSequences(String filename, int minLength) {
    ArrayList<String> resa = new ArrayList<String>();

    try {
      BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
      String s;
      StringBuilder sb = new StringBuilder();

      boolean firstLine = true;
      while ((s = bin.readLine()) != null) {
        if (s.length() > 0 && s.charAt(0) == '>') {
          // if (sb.length() >= minLength) {
          if (!firstLine) {
            resa.add(sb.toString());
          // }
          }
          sb.setLength(0);

        } else sb.append(s);
        firstLine = false;
      }
      // if (sb.length() >= minLength) 
      resa.add(sb.toString());

    } catch (IOException e) {
      throw new RuntimeException("unable to parse sequences file " + filename);
    }

    return resa.toArray(new String[resa.size()]);
  }

}
