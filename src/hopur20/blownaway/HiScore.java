package hopur20.blownaway;

import java.util.Random;

import android.content.Context;

public class HiScore {
	
public String[] names; 
public int[] scores;
private final Context context;

public HiScore(Context context)
{
	this.names = getnames();
	this.scores = getScores();
	this.context = context;
}

private int[] getScores() 
{
    Random rand = new Random();
	int[] hiscores= new int[10];
	for (int i=0; i!=10;i++)
	{
		hiscores[i]=10-i;	
	}
	return hiscores;
}

private String[] getnames()
{
	String[] names = new String[10];
	for (int i=1; i!=11;i++)
		{
		names[i-1]="AAA";	
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
	
}

	
}
