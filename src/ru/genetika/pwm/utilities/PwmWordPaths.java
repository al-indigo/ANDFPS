package ru.genetika.pwm.utilities;

import ru.genetika.pwm.Pwm;

import java.util.Arrays;

/* decided not to make "implements Iterable" -- it's redundant. But this class works exactly as
 * iterators do. */

public class PwmWordPaths{
    private Pwm matrix = null;
    private byte[] path = null;
    private byte numCols = 0;
    private int numRows = 0;

    public PwmWordPaths (Pwm pwm) {
        matrix = pwm;
        path = new byte[pwm.size()];
        Arrays.fill(path, (byte)'\0');
        path[pwm.size()-1] = -1;
        /* TODO: fix numCols in original -- numCols never can be more than 27 */
        numCols = (byte)pwm.getNumCols();
        numRows = pwm.size();
    }

    public byte[] next() {
        for (int i = numRows-1; i >= 0; i--) {
            if (path[i] < numCols-1) {
                path[i]++;
                return path.clone();
            } else {
                path[i] = 0;
                continue;
            }
        }
        return path.clone();
    }

    public void makeHop(int hopPoint) {
//        byte[] pathCopy = path.clone();

        if (hopPoint == numRows -1) return;

        for (int i = hopPoint + 1; i < numRows - 1; i++) {
            path[i] = 0;
        }

        path[numRows-1] = -1;

        for (int i = hopPoint; i >= 0; i--) {
            if (path[i] < numCols-1) {

                path[i]++;

/*              For debug: our paths should only increase
                float sum = 0;
                float oldsum = 0;
                for (int m = 0; m < numRows; m++) {
                    sum += Math.pow(10, m) * path[numRows-m-1];
                }
                for (int m = 0; m < numRows; m++) {
                    oldsum += Math.pow(10, m) * pathCopy[numRows-m-1];
                }
                if (sum < oldsum) {
                    System.out.println("Error!");
                }
*/

                return;
            } else {
                /* we have finished */
                if (i == 0 && path[i] == numCols-1) {

                    Arrays.fill(path, (byte)(numCols-1));
                    return;
                }
                path[i] = 0;
                continue;
            }
        }
/*      For debug: our paths should only increase
        float sum = 0;
        float oldsum = 0;
        for (int m = 0; m < numRows; m++) {
            sum += Math.pow(10, m) * path[numRows-m-1];
        }
        for (int m = 0; m < numRows; m++) {
            oldsum += Math.pow(10, m) * pathCopy[numRows-m-1];
        }
        if (sum < oldsum) {
            System.out.println("Error!");
        }
*/
    }

    public boolean hasNext() {
        for (int i = 0; i < numRows; i++) {
            if (path[i] < numCols-1) {
                return true;
            }
        }
        return false;
    }
}
