package com.example.musicplayer;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MuSica extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mu_sica);
		
		Thread t=new Thread()
		{
			public void run()
			{
				try
				{
					sleep(3000);
					Intent i=new Intent(MuSica.this,MusicActivity.class);
					startActivity(i);
					MuSica.this.finish();
		
				}
				catch(Exception e)
				{}
			}
		};
		t.start();
	}
}
