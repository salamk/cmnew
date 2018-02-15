/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart.ti;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import com.tictactec.ta.lib.*;

public class BollingerBand 
{

  /**
   * The total number of periods to generate data for.
   */
  public static final int TOTAL_PERIODS = 100;

  /**
    * The number of periods to average together.
    */
  public static final int PERIODS_AVERAGE = 20;

    public static void main(String[] args) 
    {
      double[] closePrice = new double[TOTAL_PERIODS];
      double[] out = new double[TOTAL_PERIODS];
      MInteger begin = new MInteger();
      MInteger length = new MInteger();

      double[] outRealUpperBand = new double[TOTAL_PERIODS];
      double[] outRealMiddleBand = new double[TOTAL_PERIODS];
      double[] outRealLowerBand = new double[TOTAL_PERIODS];




      for (int i = 0; i < closePrice.length; i++) {
        closePrice[i] = (double) i;
      }

      Core c = new Core();
//      RetCode retCode = c.sma(0, closePrice.length - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
//      RetCode re = c.sma(startIdx, endIdx, inReal, optInTimePeriod, outBegIdx, outNBElement, outReal)
      RetCode retCode = c.bbands(0, closePrice.length - 1, closePrice, PERIODS_AVERAGE,
              1.0, 3.0, MAType.Ema, begin, length, outRealUpperBand, outRealMiddleBand, outRealLowerBand);
//      RetCode re = c.bbands(startIdx, endIdx, inReal, optInTimePeriod, optInNbDevUp, optInNbDevDn, optInMAType, outBegIdx, outNBElement, outRealUpperBand, outRealMiddleBand, outRealLowerBand)


      if (retCode == RetCode.Success) {
        System.out.println("Output Begin:" + begin.value);
        System.out.println("Output Begin:" + length.value);

        for (int i = begin.value; i < closePrice.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(closePrice[i]);
            line.append(" mov avg=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
            }
    }
    else {
      System.out.println("Error");
    }
  }
}