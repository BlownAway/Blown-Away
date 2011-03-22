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

public class MainMenu extends Activity implements OnClickListener{

	/* 
	 * Kallað þegar forritið er ræst.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public static final String SAVED_PREF = "SavedLevel";
	private Button newgamebutton,resumegamebutton;	
	private SharedPreferences settings;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		settings = this.getSharedPreferences(SAVED_PREF, 0);
		
		boolean hasSavedGame = settings.getInt("currentLevel", 1)>1;

		//Virkjum hnappinn fyrir nýjan leik til að ræsa Game Activity.
		newgamebutton = (Button) findViewById(R.id.newgame);
		
		newgamebutton.setOnClickListener(this);

		resumegamebutton = (Button) findViewById(R.id.resumegame);
		resumegamebutton.setOnClickListener(this);
		//resumegamebutton.setEnabled(hasSavedGame);
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
			}
		};
		instructionsbutton.setOnClickListener(instructionsListener);



	}

	@Override
	protected void onResume() {
		super.onResume();
		//resumegamebutton.setEnabled(settings.getInt("currentLevel", 1)>1);
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
			int startLevel = settings.getInt("currentLevel", 1);
			int score = settings.getInt("score", 0);
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

	}
}
