package hopur20.blownaway;


import android.app.Activity;
import android.content.Intent;
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
	private int currentLevel=1;
	private int score = 0;
	Button okbutton,quitbutton;
	TextView title,summary;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.betweenlevelmenu);
		title = (TextView) findViewById(R.id.titletv);
		title.setText("Blown Away");
		summary = (TextView) findViewById(R.id.summarytv);
		summary.setText("Diffuse the bomb before the time runs out by following the diffusion instructions. Notice that viewing the instructions more than once will cost you extra time!");
		okbutton = (Button) findViewById(R.id.okbutton);
		okbutton.setText("Start Game");
		quitbutton = (Button) findViewById(R.id.quitbutton);
		quitbutton.setOnClickListener(this);
		okbutton.setOnClickListener(this);
		
	
	}
	@Override
	protected void onActivityResult(int levelnumber, int resultcode, Intent data) {
		if(resultcode == RESULT_OK){
			title.setText("Level "+currentLevel+ " completed!");
			score += currentLevel*100+data.getIntExtra("score", 0);
			summary.setText("Your score so far: "+score);
			currentLevel++;
			okbutton.setText("Next level");
		}
		else{
			title.setText("Game Over");
			okbutton.setVisibility(View.INVISIBLE);
		}
		
	}
	public void onClick(View v) {
		if(v==okbutton){
			Intent newLevel = new Intent(this,hopur20.blownaway.Game.class);
			newLevel.putExtra("hopur20.blownaway.Game.LEVEL_NUMBER",currentLevel);
			this.startActivityForResult(newLevel, currentLevel);
		}
		if(v==quitbutton){
			finish();
		}
		
	}
}
