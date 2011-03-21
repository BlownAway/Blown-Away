package hopur20.blownaway;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Game extends Activity implements OnClickListener, BombStateListener {

	private Level level; //Borðið sem verið er að spila.
    
  
	/*
	 * Kallað þegar nýr leikur er ræstur.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        
        // Búum til nýtt level.
        level = new Level(5);
        level.getBomb().addStateListener(this);
                
        // Framköllum leiðbeiningar fyrir aftengingu sprengju.
        String instructions = "To defuse the bomb, you must cut: \n";
        for(int i =0; i!=level.getColorSummary().length;i++){
        	instructions+="  " + level.getColorSummary()[i] +
        	              " "  + this.getResources().getStringArray(R.array.wirecolors)[i] + " wires\n";
        }
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
        
        //Virkjum takkana fyrir vírana.
        ImageButton greennorth = (ImageButton) findViewById(R.id.greennorth);
        greennorth.setOnClickListener(this);
        ImageButton yellownorth = (ImageButton) findViewById(R.id.yellownorth);
        yellownorth.setOnClickListener(this);
        ImageButton rednorth = (ImageButton) findViewById(R.id.rednorth);
        rednorth.setOnClickListener(this);
        ImageButton greenwest = (ImageButton) findViewById(R.id.greenwest);
        greenwest.setOnClickListener(this);
        ImageButton yellowwest = (ImageButton) findViewById(R.id.yellowwest);
        yellowwest.setOnClickListener(this);
        ImageButton redwest = (ImageButton) findViewById(R.id.redwest);
        redwest.setOnClickListener(this);
        ImageButton greensouth = (ImageButton) findViewById(R.id.greensouth);
        greensouth.setOnClickListener(this);
        ImageButton yellowsouth = (ImageButton) findViewById(R.id.yellowsouth);
        yellowsouth.setOnClickListener(this);
        ImageButton redsouth = (ImageButton) findViewById(R.id.redsouth);
        redsouth.setOnClickListener(this);
        ImageButton greeneast = (ImageButton) findViewById(R.id.greeneast);
        greeneast.setOnClickListener(this);
        ImageButton yelloweast = (ImageButton) findViewById(R.id.yelloweast);
        yelloweast.setOnClickListener(this);
        ImageButton redeast = (ImageButton) findViewById(R.id.redeast);
        redeast.setOnClickListener(this);
        
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
		
		//clicked.setBackgroundColor(R.color.orange);
		clicked.setVisibility(View.INVISIBLE);
		switch(clicked.getId()){
			case R.id.greennorth:
				level.getBomb().cutWire(0,0);
				break;
			case R.id.yellownorth:
				level.getBomb().cutWire(0, 1);
				break;
			case R.id.rednorth:
				level.getBomb().cutWire(0, 2);
				break;
			case R.id.greensouth:
				level.getBomb().cutWire(1,0);
				break;
			case R.id.yellowsouth:
				level.getBomb().cutWire(1, 1);
				break;
			case R.id.redsouth:
				level.getBomb().cutWire(1, 2);
				break;
			case R.id.greenwest:
				level.getBomb().cutWire(2, 0);
				break;
			case R.id.yellowwest:
				level.getBomb().cutWire(2,1);
				break;
			case R.id.redwest:
				level.getBomb().cutWire(2, 2);
				break;
			case R.id.greeneast:
				level.getBomb().cutWire(3, 0);
				break;
			case R.id.yelloweast:
				level.getBomb().cutWire(3, 1);
				break;
			case R.id.redeast:
				level.getBomb().cutWire(3, 2);
				break;
		}
		
	}

	/*
	 * Birtum tilkynningu þegar sprengjan er aftengd.
	 * @see hopur20.blownaway.BombStateListener#onBombDefused(long)
	 */
	public void onBombDefused(long timeRemaining) {
		new AlertDialog.Builder(this).setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
        	public void onClick(DialogInterface arg0, int arg1) {
        		arg0.dismiss();
        		finish();
        	}
        }).setMessage("Congratulations, you defused the bomb!").show();
	}

	/*
	 * Birtum tilkynningu þegar sprenfjan springur.
	 * @see hopur20.blownaway.BombStateListener#onBombExploded()
	 */
	public void onBombExploded(){
		new AlertDialog.Builder(this).setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
        	public void onClick(DialogInterface arg0, int arg1) {
        		arg0.dismiss();
        		finish();
        	}
        }).setMessage("The bomb exploded!").show();
		
	}

	/*
	 * Birtum tíma sem eftir er á klukku.
	 * @see hopur20.blownaway.BombStateListener#onTick(long)
	 */
	public void onTick(long timeRemaining) {
		 TextView timerDisplay = (TextView) findViewById(R.id.timer);
		 timerDisplay.setText("Time: " + timeRemaining);
	}
}