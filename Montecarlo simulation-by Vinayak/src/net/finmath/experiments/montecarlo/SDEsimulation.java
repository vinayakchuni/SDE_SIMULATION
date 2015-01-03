/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christian-fries.de.
 *
 * Created on 10.02.2004
 */
package net.finmath.experiments.montecarlo;

import java.text.DecimalFormat;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationInterface;

/**
 * @author Christian Fries
 * 
 */
public class SDEsimulation {

	static final DecimalFormat fromatterReal2	= new DecimalFormat("0.00");
	static final DecimalFormat fromatterSci4	= new DecimalFormat(" 0.0000E00;-0.0000E00");
	
	public static void main(String[] args)
	{
		// The parameters
		int numberOfPaths	= 10000;
		int seed			= 53252;
		

		double lastTime = 4.0;
		double dt = 0.1;
		final double sigma=.3;

		// Create the time discretization
		TimeDiscretizationInterface timeDiscretization = new TimeDiscretization(0.0, (int)(lastTime/dt), dt);

		// Test the quality of the Brownian motion
		BrownianMotion brownian = new BrownianMotion(
				timeDiscretization,
				1,
				numberOfPaths,
				seed
		);
		
		
		
		
		System.out.println("Average, variance and other properties of a BrownianMotion.\nTime step size (dt): " + dt + "  Number of path: " + numberOfPaths + "\n");

		System.out.println("      " + "\t" + "  int dW " + "\t" + "         " + "\t" + "int dW dW" + "\t" + "        ");
		System.out.println("time  " + "\t" + "   mean  " + "\t" + "    var  " + "\t" + "   mean  " + "\t" + "    var  ");


		
		for(int timeIndex=0; timeIndex<timeDiscretization.getNumberOfTimeSteps(); timeIndex++) {
			RandomVariableInterface W	= new RandomVariable(0.0);
			RandomVariableInterface X	= new RandomVariable(0.0);
			RandomVariableInterface Y	= X.exp();
			RandomVariableInterface DeltaY	=new RandomVariable(0.0);
			for(int j=0;j<timeIndex;j++){
				
			
			
			RandomVariableInterface brownianIncrement = brownian.getBrownianIncrement(j,0);
			
			// Calculate W(t+dt) from dW
			W=W.add(brownianIncrement);
			X=W.mult(sigma);
			
			DeltaY=(brownianIncrement.mult(Y).mult(sigma)).add(Y.mult(.5*sigma*sigma*dt));
			
			Y=Y.add(DeltaY);
			

	//		double time		= timeDiscretization.getTime(timeIndex);


			

			


		}
		RandomVariableInterface Z=X.exp().sub(Y);
		
		double mean		= Z.getAverage();
		System.out.println(mean);
		
		}
		System.out.println("Test1");
		
	}	
}

