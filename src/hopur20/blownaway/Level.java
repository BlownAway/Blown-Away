/**
 * 
 */
package hopur20.blownaway;


import java.util.Random;

/**
 * @author thorey
 *
 */
public class Level {
	
	private Bomb bomb;
	private int time;
	private int[] locSummary;
	private int[] colorSummary;
	
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

		bomb = new Bomb(locSummary,colorSummary);
		
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public Bomb getBomb()
	{
		return this.bomb;
	}
	public int[] getColorSummary(){
		return colorSummary;
	}
	public int[] getLocationSummary(){
		return locSummary;
	}
	

}
