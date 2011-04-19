package hopur20.blownaway;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HiScoreDisplay extends Activity {
	private HiScore hiscore;
	public static int endscore;
	public static boolean addingscore;


	
	 @Override
		public void onCreate(Bundle savedInstanceState) {
	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.hiscore);
	        hiscore = new HiScore(this);
	   	 	sethiscores();
	   	 	ImageButton addScore = (ImageButton) findViewById(R.id.addscore);
	   	 	TextView score = (TextView) findViewById(R.id.score);
	   	 	final EditText namefield = (EditText) findViewById(R.id.name);
	   	 	
	   	 if (addingscore==true)
			{
				namefield.setVisibility(View.VISIBLE);
				addScore.setVisibility(View.VISIBLE);
				score.setVisibility(View.VISIBLE);
				score.setText(Integer.toString(endscore));
			}
			else
			{
				namefield.setVisibility(View.INVISIBLE);
				addScore.setVisibility(View.INVISIBLE);
				score.setVisibility(View.INVISIBLE);
			}
	   	
	   	addScore.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				ImageButton addScore = (ImageButton) findViewById(R.id.addscore);
				addScore.setVisibility(View.INVISIBLE);
				
				try {
					String name =namefield.getText().toString();
					hiscore.addScore(endscore, name);
					addingscore = false;
					
					sethiscores();
				}
				catch(ActivityNotFoundException e){
					e.toString();
				}
			}       	
        });		                 	 
	 }


public void sethiscores()
{
	TableLayout hiscoretable = (TableLayout)findViewById(R.id.hiscoretable);
	

	hiscoretable.removeAllViews();
	
	
	for (int i=0; i!=hiscore.scores.length; i++) {
		TableRow hiscorerow = new TableRow(this);
		TextView gildi = new TextView(this);
		gildi.setMaxHeight(30);
		gildi.setTextColor(this.getResources().getColor(R.color.orange));
		gildi.setText(hiscore.names[i]+" "+Integer.toString(hiscore.scores[i])+"\n");
		hiscorerow.addView(gildi);
		hiscoretable.addView(hiscorerow);
 	} 
 }
 }
