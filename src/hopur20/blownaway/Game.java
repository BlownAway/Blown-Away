package hopur20.blownaway;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

	private Level level;
	private CountDownTimer timer;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        level = new Level(3,4,30);
                
        final TextView mTextField = (TextView) findViewById(R.id.timer);
        
        timer = new CountDownTimer(level.getTime()*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setText("done!");
        		new AlertDialog.Builder( mTextField.getContext())
        	    .setMessage("The bomb exploded!")
        	    .setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
	        	public void onClick(DialogInterface arg0, int arg1) {
	        		arg0.dismiss();
	        		finish();
	        	}
	        })
        	    .show();
            }
         }.start();

      
        
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
        Button greennorth = (Button) findViewById(R.id.greennorth);
        greennorth.setOnClickListener(this);
        Button yellownorth = (Button) findViewById(R.id.yellownorth);
        yellownorth.setOnClickListener(this);
        Button rednorth = (Button) findViewById(R.id.rednorth);
        rednorth.setOnClickListener(this);
        Button greenwest = (Button) findViewById(R.id.greenwest);
        greenwest.setOnClickListener(this);
        Button yellowwest = (Button) findViewById(R.id.yellowwest);
        yellowwest.setOnClickListener(this);
        Button redwest = (Button) findViewById(R.id.redwest);
        redwest.setOnClickListener(this);
        Button greensouth = (Button) findViewById(R.id.greensouth);
        greensouth.setOnClickListener(this);
        Button yellowsouth = (Button) findViewById(R.id.yellowsouth);
        yellowsouth.setOnClickListener(this);
        Button redsouth = (Button) findViewById(R.id.redsouth);
        redsouth.setOnClickListener(this);
        Button greeneast = (Button) findViewById(R.id.greeneast);
        greeneast.setOnClickListener(this);
        Button yelloweast = (Button) findViewById(R.id.yelloweast);
        yelloweast.setOnClickListener(this);
        Button redeast = (Button) findViewById(R.id.redeast);
        redeast.setOnClickListener(this);
        
        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				helpDialog.show();
			}
        	
        });
        
        Button quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				timer.cancel();
				finish();
			}
        	
        });
        

    }

	public void onClick(View clicked) {
		
		clicked.setBackgroundColor(R.color.black);
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
			case R.id.greenwest:
				level.getBomb().cutWire(1,0);
				break;
			case R.id.yellowwest:
				level.getBomb().cutWire(1, 1);
				break;
			case R.id.redwest:
				level.getBomb().cutWire(1, 2);
				break;
			case R.id.greensouth:
				level.getBomb().cutWire(2, 0);
				break;
			case R.id.yellowsouth:
				level.getBomb().cutWire(2,1);
				break;
			case R.id.redsouth:
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

		final Context c = clicked.getContext();
		
		if(level.getBomb().hasExploded()){
			timer.cancel();
			new AlertDialog.Builder(c).setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
	        	public void onClick(DialogInterface arg0, int arg1) {
	        		arg0.dismiss();
	        		finish();
	        	}
	        }).setMessage("The bomb exploded!").show();
		}
		if(level.getBomb().isDefused()){
			timer.cancel();
			new AlertDialog.Builder(c).setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
	        	public void onClick(DialogInterface arg0, int arg1) {
	        		arg0.dismiss();
	        		finish();
	        	}
	        }).setMessage("Congratulations, you defused the bomb!").show();
		}
		
	}
}