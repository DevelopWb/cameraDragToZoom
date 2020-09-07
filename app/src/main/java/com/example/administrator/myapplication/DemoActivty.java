package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.example.surfaceviewdemo.R;

import java.io.IOException;

public class DemoActivty extends Activity {
	public static final String TAG = "MyGesture";

	Button btn_start;
	Button btn_end;
	CustomSurfaceView customSurfaceView;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer mediaPlayer;
	String pathVideo;
	LinearLayout line_view;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_layout);
		btn_start = (Button) findViewById(R.id.start_vedio);
		btn_end = (Button) findViewById(R.id.stop_vedio);
		line_view = (LinearLayout) findViewById(R.id.line_view);
		customSurfaceView = (CustomSurfaceView) findViewById(R.id.hv_scrollView);
		LayoutParams layoutParams = (LayoutParams) customSurfaceView.getLayoutParams();
		layoutParams.gravity = Gravity.CENTER;
		customSurfaceView.setLayoutParams(layoutParams);
		initgetViewW_H();
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSurfaceView();
			}
		});

		btn_end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopSurfaceView();
			}
		});
		surfaceHolder = customSurfaceView.getHolder();
		surfaceHolder.setFixedSize(800, 800);
		surfaceHolder.setKeepScreenOn(true);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
			}
		});

	}

	private void initgetViewW_H() {
		line_view.postDelayed(new Runnable() {
			@Override
			public void run() {
				customSurfaceView.setFatherW_H(customSurfaceView.getTop(), customSurfaceView.getBottom());
			}
		}, 100);
	}

	// 鏆傚仠
	public void stopSurfaceView() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	// 寮�濮嬫挱鏀捐棰�
	public void playSurfaceView() {
		try {
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
			}
			mediaPlayer.reset();
			AssetFileDescriptor in = getResources().getAssets().openFd("hgz.mp4");
			mediaPlayer.setDataSource(in.getFileDescriptor(), in.getStartOffset(), in.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
