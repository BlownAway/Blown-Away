package hopur20.blownaway;

public class Wire {
	
	private int location;
	private int color;
	private boolean isCut;
	
	public Wire(int location, int color){
		this.location = location;
		this.color = color;
		this.isCut = false;
	}
	
	public void cut(){
		isCut = true;
	}
	public boolean isCut(){
		return isCut;
	}
	public int getLocation(){
		return location;
	}
	public int getColor(){
		return color;
	}

}
