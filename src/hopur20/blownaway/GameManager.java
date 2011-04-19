package hopur20.blownaway;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameManager extends Activity implements OnClickListener {
  
	/*
	 * Kallað þegar nýr leikur er ræstur.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private boolean isGameOver;
	private int currentLevel;
	private int score;
	ImageButton okbutton,quitbutton, highscorebutton;
	TextView title,summary, logo;
	public void onCreate(Bundle savedInstanceState) {
		isGameOver = false;
		super.onCreate(savedInstanceState);
		currentLevel = this.getIntent().getIntExtra("startLevel", 1);
		score = this.getIntent().getIntExtra("startScore",0);
		setContentView(R.layout.betweenlevelmenu);
		title = (TextView) findViewById(R.id.titletv);
		summary = (TextView) findViewById(R.id.summarytv);
		logo = (TextView) findViewById(R.id.logo);
		okbutton = (ImageButton) findViewById(R.id.okbutton);
		highscorebutton = (ImageButton) findViewById(R.id.hiscorebutton);
		highscorebutton.setOnClickListener(this);

		if(currentLevel==1){
			title.setText("Blown Away");
			summary.setText("Diffuse the bomb before the time runs out by following the diffusion instructions. Notice that viewing the instructions more than once will cost you extra time!");
			okbutton.setBackgroundResource(R.drawable.start_game);
			highscorebutton.setVisibility(View.INVISIBLE);
		}
		else{
			title.setText("Level "+currentLevel);
			summary.setText("Your score so far: "+score);
			okbutton.setBackgroundResource(R.drawable.start);
		}
		okbutton.setOnClickListener(this);
		
	
	}
	@Override
	protected void onActivityResult(int levelnumber, int resultcode, Intent data) {
		SharedPreferences settings = this.getSharedPreferences(MainMenu.SAVED_PREF, MODE_PRIVATE);
		settings.edit().clear().commit();
		if(resultcode == RESULT_OK){
			isGameOver = data.getBooleanExtra("isGameOver", false);
			if(isGameOver){
				title.setText("Game Over");
				summary.setText("Your final score was: "+score);
				highscorebutton.setVisibility(View.VISIBLE);
				okbutton.setBackgroundResource(R.drawable.main_menu);
				logo.setBackgroundResource(R.drawable.logo_boom);
			}
			else{
				title.setText("Level "+currentLevel+ " completed!");
				score += currentLevel*100+data.getIntExtra("score", 0);
				summary.setText("Level Bonus: "+100*currentLevel + "\n"
							   +"Time Bonus: "+data.getIntExtra("score", 0)+"\n"
							   +"Your score so far: "+score);
				currentLevel++;
				settings.edit().putInt("CURRENT_LEVEL", currentLevel).putInt("CURRENT_SCORE",score).commit();
				highscorebutton.setVisibility(View.INVISIBLE);
				okbutton.setBackgroundResource(R.drawable.next_level);

			}
		}
		else{
			settings.edit().putInt("CURRENT_LEVEL", currentLevel).putInt("CURRENT_SCORE",score).commit();
			finish();
		}
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isGameOver){
				SharedPreferences settings = this.getSharedPreferences(MainMenu.SAVED_PREF, MODE_PRIVATE);
				settings.edit().clear().putInt("CURRENT_LEVEL", currentLevel).putInt("CURRENT_SCORE",score).commit();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	public void onClick(View v) {
		if(v==okbutton && !isGameOver){
			Intent newLevel = new Intent(this,hopur20.blownaway.Game.class);
			newLevel.putExtra("hopur20.blownaway.Game.LEVEL_NUMBER",currentLevel);
			this.startActivityForResult(newLevel, currentLevel);
		}
		if(v==okbutton && isGameOver){
			finish();
		}
		if(v==highscorebutton && isGameOver)
		{
			Intent hiscoredisplay = new Intent (v.getContext(), hopur20.blownaway.HiScoreDisplay.class);
			try {v.getContext().startActivity(hiscoredisplay);}
			catch(ActivityNotFoundException e){e.toString();}	
			hopur20.blownaway.HiScoreDisplay.endscore = score;
			hopur20.blownaway.HiScoreDisplay.addingscore=true;
			finish();
			
		}
		
	}
}
