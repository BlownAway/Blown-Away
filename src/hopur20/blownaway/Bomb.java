
package hopur20.blownaway;

import java.util.ArrayList;

import android.os.CountDownTimer;



/**
 * @author thorey
 * 
 */
public class Bomb extends CountDownTimer {

	private Wire[] wires;
	private boolean hasExploded;
	private boolean isDefused;
	private int[] locationSum,colorSum;
	private long time;
	private ArrayList<BombStateListener> listeners;
	/**
	 * Fastayrðing gagna.
	 * 
	 * wires: array sem inniheldur alla víra á sprengjunni.
	 * hasExploded: satt ef sprengjan er sprungin vegna þess að klippt var á rangan vír.
	 * isDefused: satt ef búið er að aftengja sprengju.
	 * locationSum: innheldur fjölda víra sem á að klippa á hverri hlið sprengjunnar.
	 * colorSum: innheldur fjölda víra sem á að klippa af hverjum lit.
	 * time: tíminn í sekúndum uns sprengjan springur.
	 * listeners: listi af hlutum sem fá tilkynningu um breytingu á ástandi sprengju.
	 */
	
	/*
	 * Notkun: bomb= new Bomb(locSum, colSum)
	 * Fyrir: locSum innheldur fjölda víra sem á að klippa á hverri hlið.
	 * 		  colSum innheldur fjölda víra sem á að klippa af hverjum lit
	 * 		  time er tíminn í sekúndum uns sprengjan springur.
	 * Efitr: bomb er sprengja.
	 */
	public Bomb(int[] locationSum, int[] colorSum, long time) {
		super(time*1000,1000);
		this.time = time;
		listeners = new ArrayList<BombStateListener>();
		hasExploded = false;
		isDefused = false;
		this.locationSum = locationSum;
		this.colorSum = colorSum;
		wires = new Wire[locationSum.length*colorSum.length];
		
		//make one wire of each color on each side
		for(int i=0;i!=locationSum.length;i++){
			for(int j =0; j!= colorSum.length;j++){			
				wires[map2index(i,j)] = new Wire(i,j); 
			}
		}
		start();
		
	}
	
	/*
	 * Notkun c = bomb.hasExploded()
	 * Fyrir: bomb er sprengja
	 * Eftir: c er satt ef sprengjan hefur sprungið
	 *        vegna þess að klippt var á rangan vír
	 */
	public boolean hasExploded(){
		return hasExploded;
	}
	/*
	 * Notkun: c = bomb.isDefused()
	 * Fyrir:  bomb er sprengja
	 * Eftir: c er satt ef búið er að aftengja sprengju.
	 */
	public boolean isDefused(){
		return isDefused;
	}		

	/*
	 *  Notkun: x = bomb.getWires()
	 *  Fyrir: bomb er sprengja
	 *  Eftir: x er fylki sem inniheldur alla víra á sprengjunni.
	 */
	public Wire[] getWires(){
		return wires;
	}
	/*
	 * Notkun: bomb.cutWire(location,color)
	 * Fyrir: bomb er sprengja, 
	 *        0 <= location < fjöldi mögulegra hliða
	 *        0 <= color < fjöldi mögulegra lita
	 * Eftir: Búið er að klippa á vírinn sem staðsettur er 
	 * 		  á location með lit color. 
	 */
	
	public void cutWire( int location,int color){
		Wire w = wires[map2index(location,color)];
		if(!w.isCut()){
			if(locationSum[location]>0 && colorSum[color]>0){
				w.cut();
				locationSum[location]--;
				colorSum[color]--;
				isDefused = checkDefused();
			}
			else{
				hasExploded = true;
				cancel();
				announceExplosion();
			}
		}
	}
	/*
	 * Kallað þegar tíminn á sprengjunni rennur út.
	 * @see android.os.CountDownTimer#onFinish()
	 */
	public void onFinish() {
		hasExploded = true;
		announceExplosion();
		
	}

	/*
	 * Kallað þegar klukkan í sprengjunni tifar.
	 * @see android.os.CountDownTimer#onTick(long)
	 */
	public void onTick(long millisUntilFinished) {
		this.time =  millisUntilFinished / 1000;
		for(int i = 0; i!=listeners.size();i++){
			listeners.get(i).onTick(millisUntilFinished / 1000);
		}		
    }
	
	/*
	 * Notkun: t = bomb.getTime()
	 * Eftir: t er tíminn þar til sprengjan springur.
	 */
	public long getTime(){
		return time;
	}
	
	/*
	 * Notkun: bomb.addStateListener(listener)
	 * Eftir:  listener fær tilkynningar um breytingar á ástandi sprengju.
	 */
	public void addStateListener(BombStateListener listener){
		listeners.add(listener);
	}
	
	
	private int map2index(int location, int color){
		return color + location*colorSum.length;
	}
	private boolean checkDefused(){
		int toCut = 0;
		for(int i =0;i!=colorSum.length;i++){
			toCut +=colorSum[i];
		}
		for(int j=0;j!=locationSum.length;j++){
			toCut +=locationSum[j];
		}
		if(toCut==0){
			cancel();
			announceDefusion();
		}
		return toCut==0;
	}
	
	private void announceExplosion(){
		for(int i = 0; i!=listeners.size();i++){
			listeners.get(i).onBombExploded();
		}
	}
	
	private void announceDefusion(){
		for(int i = 0; i!=listeners.size();i++){
			listeners.get(i).onBombDefused(time);
		}
	}

}
