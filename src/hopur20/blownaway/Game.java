package hopur20.blownaway;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Activity implements OnClickListener, BombStateListener {

	private Level level; //Borðið sem verið er að spila.
	private int levelScore,levelNumber;
    
  
	/*
	 * Kallað þegar nýr leikur er ræstur.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		levelScore = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        levelNumber = this.getIntent().getIntExtra("hopur20.blownaway.Game.LEVEL_NUMBER", 1);
        // Búum til nýtt level.
        level = new Level(levelNumber);
        level.getBomb().addStateListener(this);
                
        // Framköllum leiðbeiningar fyrir aftengingu sprengju.
        String instructions = "To defuse the bomb, you must cut "+level.getNumberToCut()+" wires: \n";
        for(int i =0; i!=level.getColorSummary().length;i++){
        	instructions+="  " + level.getColorSummary()[i] +
        	              " "  + this.getResources().getStringArray(R.array.wirecolors)[i] + " wires\n";
        }
        instructions+="Their locations are:\n";
        for(int j = 0; j!=level.getLocationSummary().length;j++){
        	instructions+="  " + level.getLocationSummary()[j] +
        	              " on the "  + this.getResources().getStringArray(R.array.wirelocations)[j] 
        	                          + " side\n";
        }
        final AlertDialog helpDialog = new AlertDialog.Builder(this)
        .setMessage(instructions)
        .setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){

			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
        	
        })
        .show();
        
        //Virkjum vírana
        Wire[] wires = level.getBomb().getWires();
        for(int i = 0; i!=wires.length; i++){
        	String color = this.getResources().getStringArray(R.array.wirecolors)[wires[i].getColor()];
        	String location = this.getResources().getStringArray(R.array.wirelocations)[wires[i].getLocation()];
        	String id = color+location;
        	R.id rid = new R.id();
        	Button b;
			try {
				b = (Button) findViewById(rid.getClass().getField(id).getInt(rid));
				R.color rcolor = new R.color();
	        	b.setBackgroundColor(this.getResources().getColor(rcolor.getClass().getField(color).getInt(rcolor)));
	        	b.setOnClickListener(this);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //Virkjum takkann til að sýna leiðbeiningar
        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				helpDialog.show();
			}
        	
        });  

    }


	/*
	 * Slökkvum á klukkunni þegar farið er út úr leik með því að ýta á back.
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			level.getBomb().cancel();
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * Klippum á tilsvarandi vír þegar ýtt er á einhvern takka.
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View clicked) {
		
		
		 Wire[] wires = level.getBomb().getWires();
	        for(int i = 0; i!=wires.length; i++){
	        	String color = this.getResources().getStringArray(R.array.wirecolors)[wires[i].getColor()];
	        	String location = this.getResources().getStringArray(R.array.wirelocations)[wires[i].getLocation()];
	        	R.id rid = new R.id();
	        	try {
					int id =rid.getClass().getField(color+location).getInt(rid);
					if(clicked.getId() == id){
						level.getBomb().cutWire(wires[i].getLocation(), wires[i].getColor());
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	        clicked.setBackgroundColor(R.color.black);
		
	}

	/*
	 * Birtum tilkynningu þegar sprengjan er aftengd.
	 * @see hopur20.blownaway.BombStateListener#onBombDefused(long)
	 */
	public void onBombDefused(long timeRemaining) {
		levelScore = (int) timeRemaining*10;
		Intent data = new Intent();
		data.putExtra("score", levelScore);
		data.putExtra("isGameOver", false);
		this.setResult(RESULT_OK,data);
		finish();
	}

	/*
	 * Birtum tilkynningu þegar sprengjan springur.
	 * @see hopur20.blownaway.BombStateListener#onBombExploded()
	 */
	public void onBombExploded(){
		Intent data = new Intent();
		data.putExtra("score", 0);
		data.putExtra("isGameOver", true);
		this.setResult(RESULT_OK,data);
		finish();
	}

	/*
	 * Birtum tíma sem eftir er á klukku.
	 * @see hopur20.blownaway.BombStateListener#onTick(long)
	 */
	public void onTick(long timeRemaining) {
		 TextView timerDisplay = (TextView) findViewById(R.id.timer);
		 timerDisplay.setText("seconds remaining: " + timeRemaining);
	}
}