package com.example.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MusicActivity extends Activity {

	ListView lv;
	String items[];
	public static ArrayList<File> mySongs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		
		lv=(ListView)findViewById(R.id.MusicList);
		mySongs=new ArrayList<File>();
		findSongs(Environment.getExternalStorageDirectory());
		File f=new File("/storage");
		if(f.isDirectory())
			findSongs(f);
		//Toast.makeText(getApplicationContext(), Environment.getExternalStorageDirectory().toString(), Toast.LENGTH_LONG).show();

		items=new String[mySongs.size()];
		for(int i=0;i<mySongs.size();i++)
		{
            items[i]=mySongs.get(i).getName().toString().replace(".mp3","");
		}
		ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.name_list,items);
		lv.setAdapter(adp);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songList",mySongs);
				startActivity(i);
			}
			
		});
	}
	public ArrayList<File> findSongs(File root)
	{
		File[] files=root.listFiles();
		ArrayList<File> al=new ArrayList<File>();
		for(File singeFile : files)
        {
            if(singeFile.isDirectory() && !singeFile.isHidden() && singeFile.canRead())
            {
                mySongs.addAll(findSongs(singeFile));
            }
            else
            {
                if(singeFile.getName().endsWith(".mp3"))
                {
                    mySongs.add(singeFile);
                }
            }
        }
        return al;
	}
}
