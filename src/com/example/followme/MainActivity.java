package com.example.followme;
import java.util.Random;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener
{
	ImageButton left,right,up,down,old;
	SharedPreferences preferences;
	Button start;
	 TextView textview,scoreview,clockview,starter;
	TextView upview,leftview,rightview,downview;
	String putontextview;
	String correctans;
	ImageButton correctbutton;
	static ImageButton[] buttonarray; 
	RelativeLayout layout;
	int score,highscore;
	int oldposn=0;
	int count;
	CountDownTimer timer,clock;
	ViewGroup group;
	
	 static String [] stringarray={"Left","Up","Right","Down"};
	 static TextView [] viewarray;
	 static SparseIntArray green;
	 static SparseIntArray red;
	 static SparseIntArray blue;
	boolean bool;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_main);
		android.app.ActionBar actionBar=getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4068E0")));
		
		preferences=getSharedPreferences("HighScore", 0);
		highscore=preferences.getInt("highscore", 0);
		Log.d("highscore", highscore+"");
		layout=(RelativeLayout) findViewById(R.id.layout);
		left=(ImageButton) findViewById(R.id.leftImageButton3);
		right=(ImageButton) findViewById(R.id.rightImageButton2);
		up=(ImageButton) findViewById(R.id.upImageButton1);
		down=(ImageButton) findViewById(R.id.downImageButton4);
		textview= (TextView) findViewById(R.id.textView1);
		scoreview=(TextView) findViewById(R.id.scoretextView);
		clockview=(TextView) findViewById(R.id.clocktextView);
		leftview=(TextView) findViewById(R.id.lefttextView);
		rightview=(TextView) findViewById(R.id.righttextView);
		upview=(TextView) findViewById(R.id.uptextView);
		downview=(TextView) findViewById(R.id.downtextView);
		start=(Button) findViewById(R.id.startbutton);
		scoreview.setText(" "+0);
		starter=new TextView(getApplicationContext());
		starter.setVisibility(View.INVISIBLE);
		starter.setGravity(Gravity.CENTER);
		starter.setTextSize(80);
		layout.addView(starter);
		
		group=(ViewGroup) layout;
		View v;
		for(int i=0;i<group.getChildCount();i++)
		{
			v=group.getChildAt(i);
			if(v instanceof ImageButton)
			{
				v.setOnClickListener(this);
				v.setEnabled(false);
			}
		}
		
		Log.d("left", left.toString());
		Log.d("right", right.toString());
		Log.d("up", up.toString());
		Log.d("down", down.toString());
		final Random random=new Random();
		viewarray=new TextView[4];
		viewarray[0]=leftview;
		viewarray[1]=rightview;
		viewarray[2]=upview;
		viewarray[3]=downview;
		for(TextView tmp:viewarray)
		{
			tmp.setTypeface(null,Typeface.BOLD);
			tmp.setTextSize(20);
		}
		scoreview.setTextColor(Color.BLUE);
		scoreview.setTextSize(20);
		scoreview.setTypeface(null, Typeface.BOLD);
		
		clockview.setTextColor(Color.BLUE);
		clockview.setTextSize(20);
		clockview.setTypeface(null, Typeface.BOLD);
		
		start.setTextColor(Color.BLUE);
		start.setBackgroundColor(Color.WHITE);
		start.setTextSize(20);
		start.setTypeface(null,Typeface.BOLD);
		
		buttonarray=new ImageButton[4];
		buttonarray[0]=left;
		buttonarray[1]=up;
		buttonarray[2]=right;
		buttonarray[3]=down;
		
		
		green=new SparseIntArray(4);
		green.append(R.id.leftImageButton3, R.drawable.leftgreen);
		green.append(R.id.rightImageButton2, R.drawable.arrowgreenright);
		green.append(R.id.upImageButton1, R.drawable.upgreen);
		green.append(R.id.downImageButton4, R.drawable.downgreen);
		
		red=new SparseIntArray(4);
		red.append(R.id.leftImageButton3, R.drawable.redleft);
		red.append(R.id.rightImageButton2, R.drawable.arrowredright);
		red.append(R.id.upImageButton1, R.drawable.upred);
		red.append(R.id.downImageButton4, R.drawable.downred);
		
		blue=new SparseIntArray(4);
		blue.append(R.id.leftImageButton3, R.drawable.leftblue);
		blue.append(R.id.rightImageButton2, R.drawable.arrowblueright);
		blue.append(R.id.upImageButton1, R.drawable.upblue);
		blue.append(R.id.downImageButton4, R.drawable.downblue);
		
		
		 timer=new CountDownTimer(1000,1000) {
			
			
			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				int r=random.nextInt(4);
				int posn=random.nextInt(20)%4;
				if(oldposn==posn)
					posn=(posn+1)%4;
				viewarray[oldposn].setText("");
				oldposn=posn;
				putontextview=stringarray[r];
				//textview.setText(" ");
				viewarray[posn].setText(putontextview);
				//textview.setGravity(Gravity.CENTER);
				//textview.setTypeface(null,Typeface.BOLD);
				//textview.setTextSize(20);
				bool=random.nextBoolean();
				correctbutton=getAnswer(bool, r);
				//Log.d(putontextview+" "+bool, correctbutton.getText()+"");
				viewarray[posn].setTextColor(getColor(bool));
				start();
			}
		};
		
	 clock=new CountDownTimer(60000,1000) {
			
			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				clockview.setText(" "+arg0/1000);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				/*timer.cancel();
				left.setEnabled(false);
				right.setEnabled(false);
				up.setEnabled(false);
				down.setEnabled(false);*/
				if(score>=highscore)
				{
					textview.setTextSize(20);
					textview.setText("High score! "+ score);
					textview.setVisibility(View.VISIBLE);
					SharedPreferences.Editor editor=preferences.edit();
					editor.putInt("highscore", score);
					editor.apply();
				}
				else
				{
					textview.setTextSize(20);
					textview.setText("Game Over!");
					textview.setVisibility(View.VISIBLE);
				}
					
				for(TextView v:viewarray)
					v.setVisibility(View.INVISIBLE);

				disableAll();
			}
		};
		
start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				starter.setVisibility(View.VISIBLE);
				score=0;
				scoreview.setText(""+score);
				for(TextView v:viewarray)
					v.setVisibility(View.VISIBLE);
				left.setEnabled(true);
				right.setEnabled(true);
				up.setEnabled(true);
				down.setEnabled(true);
				textview.setTextSize(80);
				scoreview.setText(score+" ");
				new CountDownTimer(3000,1000) 
				{
					
					@Override
					public void onTick(long arg0) {
						// TODO Auto-generated method stub
						textview.setText(" "+arg0/1000);
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						starter.setVisibility(View.GONE);
						textview.setTextSize(30);
						textview.setVisibility(View.INVISIBLE);
						timer.start();
						clock.start();
					}
				}.start();
			}
		});
		
	}
	
	
	public void disableAll()
	{
		score=0;
		for(int i=0;i<4;i++)
		{
			buttonarray[i].setEnabled(false);
		}
		timer.cancel();
	}
	public static ImageButton getAnswer(boolean b,int i) 
	{
		if(b)
			return buttonarray[i];
		return buttonarray[(i+2)%4];
	}
	public static int getColor(boolean b) 
	{
		if(b)
			return Color.GREEN;
		return Color.RED;
	}
	/*public static int getChangedImageId(int r)
	{
		
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		ImageButton tmp=(ImageButton) v;
		if(old!=null)
			old.setImageResource(blue.get(old.getId()));
		old=tmp;
		int index=tmp.getId();
		if(v.equals(correctbutton) )
		{
			
			scoreview.setText(""+ ++score);
			tmp.setImageResource(green.get(index));
			//Log.d(tmp.getText()+"", ""+correctbutton.getText()+" "+score);
		}
		else
		{
			scoreview.setText(""+ --score);
			//tmp.setBackgroundColor(Color.RED);
			tmp.setImageResource(red.get(index));
			//Log.d(tmp.getText()+"", ""+correctbutton.getText()+" "+score);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();
		clock.cancel();
		super.onDestroy();
	}
}
