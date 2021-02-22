package com.example.musicplayer;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import com.flaviofaria.kenburnsview.KenBurnsView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Player extends Activity implements OnClickListener {

	SeekBar sb;
	Thread updateSeekBar;
	TextView artist,album,genre,songName,prog;
	static int position;
	Uri u;
	static MediaPlayer mp;
	ImageView iv;
	ArrayList<File> mySongs;
	Bitmap songImage;
	ImageButton ibplay,ibnext,ibff,ibprev,ibpp,themebck;
	
	MediaMetadataRetriever metaRetriver;
	byte[] art;
	
	KenBurnsView k;
	
	final Class drawableClass = R.drawable.class;
	final Field[] fields = drawableClass.getFields();

	final Random rand = new Random();
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		sb=(SeekBar)findViewById(R.id.seekBar);
		iv=(ImageView)findViewById(R.id.imageView);
		artist=(TextView)findViewById(R.id.artist);
		album=(TextView)findViewById(R.id.album2);
		genre=(TextView)findViewById(R.id.genre);
		songName=(TextView)findViewById(R.id.songname);
		ibplay=(ImageButton)findViewById(R.id.imagePlayButton6);
		ibnext=(ImageButton)findViewById(R.id.imageNextButton);
		ibff=(ImageButton)findViewById(R.id.imageFFButton);
		ibprev=(ImageButton)findViewById(R.id.imagePrevButton);
		ibpp=(ImageButton)findViewById(R.id.imagePPButton);
		prog=(TextView)findViewById(R.id.progress);
		k=(KenBurnsView)findViewById(R.id.kbv);
		themebck=(ImageButton)findViewById(R.id.themebck);
		
		
		updateSeekBar=new Thread()
				{
			public void run()
			{
				int totalDuration=mp.getDuration();
				int currentPosition=0;
				//sb.setMax(totalDuration);
				while(currentPosition<totalDuration)
				{
					try
					{
						sleep(500);
						currentPosition=mp.getCurrentPosition();
						sb.setProgress(currentPosition);
					}
					catch(Exception e)
					{
						
					}
				}
			}
				};//mySongs.get(position).getName();
		ibplay.setOnClickListener(this);
		ibnext.setOnClickListener(this);
		ibff.setOnClickListener(this);
		ibprev.setOnClickListener(this);
		ibpp.setOnClickListener(this);
		themebck.setOnClickListener(this);
		
		if(mp!=null)
		{
			mp.stop();
			mp.release();
		}
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		
		mySongs=(ArrayList)b.getParcelableArrayList("songList");
		position=b.getInt("pos",0);
		
		u=Uri.parse(mySongs.get(position).toString());
		songName.setText(mySongs.get(position).getName().replace(".mp3",""));
		forSetImage(u.toString());
		//Toast.makeText(getApplicationContext(), u.toString(), Toast.LENGTH_LONG).show();
		
		iv.setBackgroundColor(Color.parseColor("#00000000"));
		mp=MediaPlayer.create(getApplicationContext(), u);
		mp.start();
		ibplay.setBackgroundResource(android.R.drawable.ic_media_pause);
		k.setImageResource(R.drawable.xyz);
		sb.setMax(mp.getDuration());
		updateSeekBar.start();
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				mp.seekTo(seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				//progressSong.setText(Integer.toString(sb.getProgress())+" "+Integer.toString(sb.getMax()));
				prog.setText(Integer.toString(sb.getProgress()));
				if(sb.getProgress()>sb.getMax()-3000)
				{
					mp.stop();
					mp.release();
					position=(position+1)%mySongs.size();
					u=Uri.parse(mySongs.get(position).toString());
					mp=MediaPlayer.create(getApplicationContext(), u);
					mp.start();
					sb.setMax(mp.getDuration());
					
					u=Uri.parse(mySongs.get(position).toString());
					iv.setBackgroundColor(Color.parseColor("#00000000"));
					songName.setText(mySongs.get(position).getName().replace(".mp3",""));
					forSetImage(u.toString());
				}
			}
		});
		//for rand
		
		
		//for rand
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) 
		{
		
		case R.id.imageFFButton:
			mp.seekTo(mp.getCurrentPosition()+5000);
			break;
		case R.id.imagePPButton:
			mp.seekTo(mp.getCurrentPosition()-5000);
			break;
		case R.id.imageNextButton:
			mp.stop();
			mp.release();
			position=(position+1)%mySongs.size();
			u=Uri.parse(mySongs.get(position).toString());
			mp=MediaPlayer.create(getApplicationContext(), u);
			mp.start();
			sb.setMax(mp.getDuration());
			
			u=Uri.parse(mySongs.get(position).toString());
			iv.setBackgroundColor(Color.parseColor("#00000000"));
			songName.setText(mySongs.get(position).getName().replace(".mp3",""));
			forSetImage(u.toString());
			//Toast.makeText(getApplicationContext(), u.toString(), Toast.LENGTH_LONG).show();
			
			break;
		case R.id.imagePrevButton:
			mp.stop();
			mp.release();
			position=(position-1<0)? mySongs.size()-1:position-1;
			u=Uri.parse(mySongs.get(position).toString());
			mp=MediaPlayer.create(getApplicationContext(), u);
			mp.start();
			sb.setMax(mp.getDuration());
			
			u=Uri.parse(mySongs.get(position).toString());
			iv.setBackgroundColor(Color.parseColor("#00000000"));
			songName.setText(mySongs.get(position).getName().replace(".mp3",""));
			forSetImage(u.toString());
			//Toast.makeText(getApplicationContext(), u.toString(), Toast.LENGTH_LONG).show();
			break;
		case R.id.imagePlayButton6:
			if(mp.isPlaying())
			{
				mp.pause();
				ibplay.setBackgroundResource(android.R.drawable.ic_media_play);
			}
			else
			{
				mp.start();
				ibplay.setBackgroundResource(android.R.drawable.ic_media_pause);
			}
			break;
		case R.id.themebck:
			int rndInt = rand.nextInt(fields.length);
			try 
			{
			    int resID = fields[rndInt].getInt(drawableClass);
			    k.setImageResource(resID);
			} 
			catch (Exception e) 
			{
			    e.printStackTrace();
			}
			//k.setImageResource(R.drawable.abc);
			break;
		default:
			
		}
	}
	public void forSetImage(String url)
	{
		metaRetriver = new MediaMetadataRetriever();
		metaRetriver.setDataSource(url);
		
		try
		{
			art=metaRetriver.getEmbeddedPicture();
			songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
			iv.setImageBitmap(songImage);
			album.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
			artist.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
			genre.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
			if(art==null)
				iv.setBackgroundColor(Color.parseColor("#00000000"));
		}
		catch(Exception e)
		{
			iv.setBackgroundColor(Color.parseColor("#00000000"));
			album.setText("Unknown Album");
			artist.setText("Unknown Artist");
			genre.setText("Unknown Genre");
		}
	}
}



