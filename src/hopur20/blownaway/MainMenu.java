package hopur20.blownaway;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends Activity implements OnClickListener{

	/* 
	 * Kallað þegar forritið er ræst.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public static final String SAVED_PREF = "SavedLevel";
	private ImageButton newgamebutton,resumegamebutton, highscorebutton;	
	private SharedPreferences settings;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		settings = this.getSharedPreferences(SAVED_PREF, MODE_PRIVATE);
		

		//Virkjum hnappinn fyrir nýjan leik til að ræsa Game Activity.
		newgamebutton = (ImageButton) findViewById(R.id.newgame);
		newgamebutton.setOnClickListener(this);

		resumegamebutton = (ImageButton) findViewById(R.id.resumegame);
		resumegamebutton.setOnClickListener(this);
		
		highscorebutton = (ImageButton) findViewById(R.id.hiscore);
		highscorebutton.setOnClickListener(this);

		// Virkjum exit takkann.
		ImageButton exitbutton = (ImageButton) findViewById(R.id.exit);
		OnClickListener exitListener = new OnClickListener(){
			public void onClick(View V)
			{
				finish();
			}
		};
		exitbutton.setOnClickListener(exitListener);

		// Virkjum leiðbeiningatakkann.
		ImageButton instructionsbutton = (ImageButton) findViewById(R.id.instructions);
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
			}
		};
		instructionsbutton.setOnClickListener(instructionsListener);



	}

	public void onClick(View v) {
		if(v==newgamebutton){
			Intent game = new Intent (v.getContext(), hopur20.blownaway.GameManager.class);
			try {
				v.getContext().startActivity(game);
			}
			catch(ActivityNotFoundException e){
				e.toString();
			}
		}
		if(v==resumegamebutton){
			int startLevel = settings.getInt("CURRENT_LEVEL", 1);
			int score = settings.getInt("CURRENT_SCORE", 0);
			Intent game = new Intent (v.getContext(), hopur20.blownaway.GameManager.class);
			game.putExtra("startLevel", startLevel);
			game.putExtra("startScore", score);
			try {
				v.getContext().startActivity(game);
			}
			catch(ActivityNotFoundException e){
				e.toString();
			}
		}
		if (v==highscorebutton)			
		{
			Intent hiscoredisplay = new Intent (v.getContext(), hopur20.blownaway.HiScoreDisplay.class);
			try {v.getContext().startActivity(hiscoredisplay);}
			catch(ActivityNotFoundException e){e.toString();}	
			hopur20.blownaway.HiScoreDisplay.addingscore=false;
		} 	
	        

	}
	protected void onResume() {
		boolean hasSavedGame = settings.getInt("CURRENT_LEVEL", 1)>1;
		if(hasSavedGame){
			resumegamebutton.setEnabled(true);
		}
		else{
			resumegamebutton.setEnabled(false);
		}
		super.onResume();
	}
}
