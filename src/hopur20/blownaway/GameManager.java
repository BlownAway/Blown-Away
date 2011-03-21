package hopur20.blownaway;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameManager extends Activity implements OnClickListener {
  
	/*
	 * Kallað þegar nýr leikur er ræstur.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private boolean isGameOver;
	private int currentLevel;
	private int score;
	Button okbutton,quitbutton;
	TextView title,summary;
	public void onCreate(Bundle savedInstanceState) {
		isGameOver = false;
		super.onCreate(savedInstanceState);
		currentLevel = this.getIntent().getIntExtra("startLevel", 1);
		score = this.getIntent().getIntExtra("startScore",0);
		setContentView(R.layout.betweenlevelmenu);
		title = (TextView) findViewById(R.id.titletv);
		summary = (TextView) findViewById(R.id.summarytv);
		okbutton = (Button) findViewById(R.id.okbutton);
		if(currentLevel==1){
			title.setText("Blown Away");
			summary.setText("Diffuse the bomb before the time runs out by following the diffusion instructions. Notice that viewing the instructions more than once will cost you extra time!");
			okbutton.setText("Start Game");
			
		}
		else{
			title.setText("Level "+currentLevel);
			okbutton.setText("Start");
			summary.setText("Your score so far: "+score);
		}
		okbutton.setOnClickListener(this);
		
	
	}
	@Override
	protected void onActivityResult(int levelnumber, int resultcode, Intent data) {
		SharedPreferences settings = this.getSharedPreferences(MainMenu.SAVED_PREF, MODE_PRIVATE);
		if(resultcode == RESULT_OK){
			isGameOver = data.getBooleanExtra("isGameOver", true);
			if(isGameOver){
				title.setText("Game Over");
				summary.setText("Your final score was: "+score);
				okbutton.setText("Main Menu");
				settings.edit().putInt("currentLevel", 1).commit();
				settings.edit().putInt("score",0).commit();
			}
			else{
				title.setText("Level "+currentLevel+ " completed!");
				score += currentLevel*100+data.getIntExtra("score", 0);
				summary.setText("Level Bonus: "+100*currentLevel + "\n"
							   +"Time Bonus: "+data.getIntExtra("score", 0)+"\n"
							   +"Your score so far: "+score);
				currentLevel++;
				okbutton.setText("Next level");
			}
		}
		else{
			settings.edit().putInt("currentLevel", currentLevel).commit();
			settings.edit().putInt("score",score).commit();
			finish();
			
			
		}
		
	}
	public void onClick(View v) {
		if(!isGameOver){
			Intent newLevel = new Intent(this,hopur20.blownaway.Game.class);
			newLevel.putExtra("hopur20.blownaway.Game.LEVEL_NUMBER",currentLevel);
			this.startActivityForResult(newLevel, currentLevel);
		}
		if(isGameOver){
			finish();
		}
		
	}
}
