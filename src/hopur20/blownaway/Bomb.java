
package hopur20.blownaway;

import java.util.ArrayList;


/**
 * @author thorey
 *
 */
public class Bomb {

	private Wire[] wires;
	private boolean hasExploded;
	private boolean isDefused;
	private int[] locationSum,colorSum;
	/**
	 * 
	 */
	public Bomb(int[] locationSum, int[] colorSum) {
		hasExploded = false;
		isDefused = false;
		this.locationSum = locationSum;
		this.colorSum = colorSum;
		wires = new Wire[locationSum.length*colorSum.length];
		
		for(int i=0;i!=locationSum.length;i++){
			for(int j =0; j!= colorSum.length;j++){
				//make one wire of each color on each side
				wires[map2index(i,j)] = new Wire(i,j); 
			}
		}
		
	}
	public boolean hasExploded(){
		return hasExploded;
	}
	public boolean isDefused(){
		return isDefused;
	}
	public boolean checkDefused(){
		int toCut = 0;
		for(int i =0;i!=colorSum.length;i++){
			toCut +=colorSum[i];
		}
		for(int j=0;j!=locationSum.length;j++){
			toCut +=locationSum[j];
		}
		return toCut==0;
	}
	

	
	public Wire[] getWires(){
		return wires;
	}
	
	public void cutWire( int location,int color){
		Wire w = wires[map2index(location,color)];
		if(locationSum[location]>0 && colorSum[color]>0){
			w.cut();
			locationSum[location]--;
			colorSum[color]--;
			isDefused = checkDefused();
		}
		else{
			hasExploded = true;
		}
	}
	private int map2index(int location, int color){
		return color + location*colorSum.length;
	}

}
