package com.pr.im.activites;

import java.io.File;    
import java.io.IOException;    
    








import com.pr.im.R;
import com.pr.im.constant.Constants;
import com.pr.im.util.ImageUtil;

import android.R.string;
import android.app.Activity;    
import android.content.Intent;
import android.media.MediaRecorder;    
import android.os.Bundle;    
import android.os.Environment;    
import android.view.SurfaceHolder;    
import android.view.SurfaceView;    
import android.view.View;    
import android.view.View.OnClickListener;    
import android.widget.Button;    
    
public class VideoActivity extends Activity {    
    
        
    private File myRecAudioFile;    
    private SurfaceView mSurfaceView;       
    private SurfaceHolder mSurfaceHolder;     
    private Button buttonStart;    
    private Button buttonStop;    
    private File dir;    
    private MediaRecorder recorder;   
    public static final int VID = 5;
    private  String path;
    private String video_name;
    private String video_path;
    private static final int TAKE_VIDEO = 6;
    private static final int MOD_FINISH = 7;
        
        
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.video);    
        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);       
        mSurfaceHolder = mSurfaceView.getHolder();       
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);     
        buttonStart=(Button)findViewById(R.id.start);    
        buttonStop=(Button)findViewById(R.id.stop);    
        File defaultDir = Environment.getExternalStorageDirectory();    
//        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//�����ļ��д����Ƶ   
        video_name = "t"+ String.valueOf(System.currentTimeMillis()).substring(5)+".mp4";
        path = Constants.SAVE_VIDEO_PATH;
        dir = new File(path);    
        video_path = dir + "/"+ video_name;
        if(!dir.exists()){    
            dir.mkdir();    
        }    
        recorder = new MediaRecorder();    
            
        buttonStart.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                recorder();  
                
            }    
        });    
            
        buttonStop.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                 recorder.stop();    
                 recorder.reset();    
                 recorder.release();    
                 recorder=null;  
                 
             	Intent vidIntent = new Intent();
				vidIntent.putExtra("videoName", video_name);
				vidIntent.putExtra("base64String", ImageUtil.getBase64StringFromFile(video_path));
				vidIntent.putExtra("videoPath", video_path);
				setResult(RESULT_OK, vidIntent);
				finish();
                
            }    
        });    
    }   
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	// TODO Auto-generated method stub
//    	super.onActivityResult(requestCode, resultCode, data);
//    	if (resultCode==RESULT_OK) {
//    		switch (requestCode) {
//			case TAKE_VIDEO:
//				Intent intent = new Intent();
//				intent.putExtra("path", video_path);
//				startActivityForResult(intent, MOD_FINISH);
//				break;
//			case MOD_FINISH:
//				if (data != null) {
//					path = data.getStringExtra("path");
//					try {
//						Intent vidIntent = new Intent();
//						vidIntent.putExtra("videoName", video_name);
//						vidIntent.putExtra("base64String", ImageUtil.getBase64StringFromFile(path));
//						vidIntent.putExtra("videoPath", path);
//						setResult(RESULT_OK, vidIntent);
//						finish();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				break;
//			default:
//				break;
//			}
//			
//		}
//    }
//        
        
        
    public void recorder() {    
        try {    
//            myRecAudioFile = File.createTempFile("video", ".3gp",dir);//������ʱ�ļ�    
        	myRecAudioFile = new File(video_path);
            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//Ԥ��    
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//��ƵԴ    
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //¼��ԴΪ��˷�    
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//�����ʽΪ3gp    
            recorder.setVideoSize(800, 480);//��Ƶ�ߴ�    
            recorder.setVideoFrameRate(15);//��Ƶ֡Ƶ��    
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//��Ƶ����    
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//��Ƶ����    
            recorder.setMaxDuration(10000);//�������    
            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//����·��    
            recorder.prepare();    
            recorder.start();   
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }    
}    
