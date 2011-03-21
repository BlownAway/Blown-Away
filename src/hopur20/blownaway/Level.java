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
	private int time,numColors,numLocations;
	private int[] locSummary;
	private int[] colorSummary;
	
	/*
	 * Notkun: level = new Level(n)
	 * Fyrir: @param n <= 15 er númer borðs.
	 */
	public Level(int levelNumber){
		int difficulty = levelNumber%5;
		if(levelNumber < 5){
			numColors = 3;
			numLocations = 2;
			this.time = 40-difficulty*10;
		}
		else{
			if(levelNumber <10){
				numColors = 3;
				numLocations =4;
				this.time = 30-difficulty*2;
			}
			else{
				numColors = 4;
				numLocations = 4;
				this.time = 45-difficulty*10;
			}
		}
		colorSummary = new int[numColors];
		locSummary = new int[numLocations];
		Random generator = new Random();
		
		// for each wire, flip a coin to see if it should be cut.
		for(int i = 0; i!=locSummary.length;i++){
			for(int j = 0; j!=colorSummary.length;j++){
				double sideSaturation = locSummary[i]/((double) numColors);
				double colorSaturation = colorSummary[j]/((double) numLocations);
				
				double p = 1;
				if(sideSaturation>0&difficulty<2){
					p= Math.pow(2*(sideSaturation-0.5), (5-difficulty)*2.0)/2
					  +Math.pow(2*(colorSaturation-0.5),(5-difficulty)*2.0)/2;
				}
				else{
					p = 1-Math.pow(2*(sideSaturation-0.5), (5-difficulty)*2.0)/2
					     -Math.pow(2*(colorSaturation-0.5),(5-difficulty)*2.0)/2;
				}
				if(generator.nextDouble()>p){
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
	
	public int getNumberToCut(){
		int num = 0;
		for(int i = 0; i!=locSummary.length;i++){
			num += locSummary[i];
		}
		return num;
	}
	

}
