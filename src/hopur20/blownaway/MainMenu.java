package hopur20.blownaway;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity{
	
	    /* 
	     * Kallað þegar forritið er ræst.
	     * @see android.app.Activity#onCreate(android.os.Bundle)
	     */
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       	        
	        setContentView(R.layout.main);
	        
	        //Virkjum hnappinn fyrir nýjan leik til að ræsa Game Activity.
	        Button newgamebutton = (Button) findViewById(R.id.newgame);
	        newgamebutton.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					Intent game = new Intent (v.getContext(), hopur20.blownaway.Game.class);
					try {
						v.getContext().startActivity(game);
					}
					catch(ActivityNotFoundException e){
						e.toString();
					}
					
				}
	        	
	        });
	        Button resumegamebutton = (Button) findViewById(R.id.resumegame);
	        Button highscorebutton = (Button) findViewById(R.id.hiscore);
	        
	        // Virkjum exit takkann.
	        Button exitbutton = (Button) findViewById(R.id.exit);
	        OnClickListener exitListener = new OnClickListener(){
	        	public void onClick(View V)
	        	{
	        		finish();
	        	}
	        };
	        exitbutton.setOnClickListener(exitListener);
	        
	        // Virkjum leiðbeiningatakkann.
	        Button instructionsbutton = (Button) findViewById(R.id.instructions);
	        OnClickListener instructionsListener = new OnClickListener(){
	        	public void onClick(View V)
	        	{
	        		new AlertDialog.Builder(V.getContext())
	        		.setMessage("Diffuse the bomb before the time runs out by following the diffusion instructions.\n"
	        				+"Notice that viewing the instructions more than once will cost you extra time!")
	        		.setTitle("Instructions")
	        		.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener(){
	        			public void onClick(DialogInterface arg0, int arg1) {
	        				arg0.dismiss();
	        			}
	        		}).show();
//	        		final Dialog instructionsdialog = new Dialog(MainMenu.this);
//	        		instructionsdialog.setContentView(R.layout.instructionsdialog);
//	                instructionsdialog.setTitle("Instructions");
//	                instructionsdialog.setCancelable(true);
//	               
//	                instructionsdialog.show();
	       
	               /*Button dialogbutton = (Button) findViewById(R.id.okbutton);
	               dialogbutton.setOnClickListener(new OnClickListener() {
	                @Override
	                    public void onClick(View v) {
	                	instructionsdialog.dismiss();
	                	};
	                
	                }); */
	        	}
	        };
	        instructionsbutton.setOnClickListener(instructionsListener);
	        
	        
	        
	    }
}
