
package hopur20.blownaway;


import java.util.Random;

/**
 * @author thorey
 *
 */
public class Level {
	/*
	 * Fastayrðing gagna:
	 * 
	 * bomb er sprengjan í þessu borði.
	 * time er tíminn sem gefst til að aftengja sprengju þessa borðs í sekúndum.
	 * locSummary er fjöldi víra sem þarf að klippa á hverri hlið sprengjunnar.
	 * colorSummary er fjöldi víra sem þarf að klippa af hverjum lit.
	 * 
	 */
	
	private Bomb bomb;
	private int time;
	private int[] locSummary;
	private int[] colorSummary;
	
	/*
	 * Notkun: level = new Level(i,j,t)
	 * Fyrir: i er fjöldi mögulegra lita í þessu borði.
	 * 		  j er fjöldi mögulegra hliða í þessu borði.
	 * 		  t er tíminn sem gefst til að aftengja sprengju þessa borðs í sekúndum.
	 */
	public Level(int numColors, int numLocations,int time){
		this.time = time;
		colorSummary = new int[numColors];
		locSummary = new int[numLocations];
		Random generator = new Random();
		
		// for each wire, flip a coin to see if it should be cut.
		for(int i = 0; i!=locSummary.length;i++){
			for(int j = 0; j!=colorSummary.length;j++){
				if(generator.nextDouble()>0.5){
					locSummary[i]++;
					colorSummary[j]++;
				}
			}
		}

		bomb = new Bomb(locSummary,colorSummary,time);
		
	}
	/*
	 * Notkun: t = level.getTime()
	 * eftir: t er tíminn sem gefst til að aftengja sprengju borðsins level.
	 */
	public int getTime()
	{
		return this.time;
	}
	/*
	 * Notkun: bomb = level.getBomb()
	 * Eftir: bomb er sprengja borðsins level.
	 */
	public Bomb getBomb()
	{
		return this.bomb;
	}
	/*
	 * Notkun: colorSummary = level.getColorSummary()
	 * Eftir: colorSummary er fjöldi víra sem þarf að klippa af hverjum lit í borðinu level. 
	 */
	public int[] getColorSummary(){
		return colorSummary;
	}
	/*
	 * Notkun: locSummary = level.getLocationSummary()
	 * Eftir: locSummary er fjöldi víra sem þarf að klippa á hverri hlið sprengjunnar í borðinu level.
	 */
	public int[] getLocationSummary(){
		return locSummary;
	}
	

}
