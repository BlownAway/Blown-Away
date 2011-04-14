package hopur20.blownaway;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

import android.content.Context;

public class HiScore {
	
public String[] names; 
public int[] scores;
private final Context context;

public HiScore(Context context)
{
	String[] namesandscores = readHiScorecsv(context);
	this.scores = getScores(namesandscores);
	this.names = getNames(namesandscores);
	this.context = context;
}

private String[] readHiScorecsv(Context context) 
{
	InputStream is = null;
	try {
		is = context.openFileInput("hiscores.csv") ;
	} catch (FileNotFoundException e1) {
	is = context.getResources().openRawResource(R.raw.hiscores);
	}
	String[] hiscores= new String[20];
	int i=0;
	String line = null;
	
	BufferedReader br = null;
	br = new BufferedReader(new InputStreamReader(is));
	
	try {
		while((line = br.readLine()) != null && i < 20)
		{	
		StringTokenizer st = new StringTokenizer(line,",");
		while (st.hasMoreTokens())
		{
			//get next token and store it in the array
			hiscores[i] = st.nextToken();
			i++;
		}
		
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return hiscores;
}

private int[] getScores(String[] scoresandnames)
{
	int[] scores = new int[10];
	int i=1;
	int j=0;
	while (j<10)
		{
		scores[j]=Integer.parseInt(scoresandnames[i]);
		i=i+2;
		j++;
		}
	return scores;                            
	                            
}

private String[] getNames(String[] scoresandnames)
{
	String[] names = new String[10];
	int i=0;
	int j=0;
	
	while (j<10)
	{
	names[j]=scoresandnames[i];
	i=i+2;
	j++;
	}
	
	return names;

	
}

public int compareScore(int score)
{
	int place=scores.length;
	int hiscore=scores[scores.length-1];

	while (score>hiscore && place!=0)
	{
		hiscore=scores[place-1];
		place=place-1;
		
	}
	
	return place;
}

public void addScore(int score, String name)
{
	int place = compareScore(score);
	if (place>=scores.length){return;}
	else
	{
		for (int i=scores.length-1; i!=place;i--)
		{
			scores[i]=scores[i-1];
			names[i]=names[i-1];
		}
		names[place]=name;
		scores[place]=score;
	}
	 
	FileOutputStream fos;
	String comma = ",";
	String newline = "\n";
	
	try {
		fos = context.openFileOutput("hiscores.csv", Context.MODE_PRIVATE);
		for(int i = 0; i < 10; i++)
	     {
	    	fos.write(names[i].getBytes());
	    	fos.write(comma.getBytes());
	    	fos.write(Integer.toString(scores[i]).getBytes());
	    	fos.write(comma.getBytes());
	    	fos.write(newline.getBytes());
	     }
		 fos.close();
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	

}
	
}
