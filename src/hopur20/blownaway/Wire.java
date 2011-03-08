package hopur20.blownaway;

public class Wire {
	/*
	 * Fastayrðing gagna:
	 * 
	 * location er númer hliðar sem vír er staðsettur á
	 * color er númer litar vírs
	 * isCut er satt ef búið er að klippa á vírinn, annars ekki.
	 */
	private int location;
	private int color;
	private boolean isCut;
	
	/*
	 * Notkun: wire = new Wire(location, color)
	 * Fyrir: 0 <= location < fjöldi mögulegra hliða
	 * 		  0 <= color < fjöldi mögulegra lita
	 * Eftir: wire er vír staðsettur á hlið númer location með lit númer color.
	 */
	public Wire(int location, int color){
		this.location = location;
		this.color = color;
		this.isCut = false;
	}
	
	/*
	 * Notkun: w.cut()
	 * Fyrir: w er vír
	 * Eftir: Búið er að klippa á vírinn
	 */
	public void cut(){
		isCut = true;
	}
	/*
	 * Notkun: c = w.isCut()
	 * Fyrir: w er vír
	 * Eftir: c er satt ef búið er að klippa á vírinn, annars ekki.
	 */
	public boolean isCut(){
		return isCut;
	}
	/*
	 * Notkun: loc = w.getLocation()
	 * Fyrir: w er vír
	 * Eftir: loc er númer hliðar sem w er staðsettur á.
	 */
	public int getLocation(){
		return location;
	}
	/*
	 * Notkun col = w.getColor()
	 * Fyrir: w er vír
	 * Eftir: col er litur vírsins w.
	 */
	public int getColor(){
		return color;
	}

}
