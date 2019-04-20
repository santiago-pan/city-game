package com.nadisam.citybombing;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseIntArray;

import com.nadisam.citybombing.levels.utils.Defines;
import com.nadisam.citybombing.log.Logger;
import com.nadisam.citybombing.pro.R;

public class AppCore extends Application
{
    private static AppCore mContext;

    // SOUNDS
    private MediaPlayer    mediaPlayer;
    private SoundPool      soundPool;
    private SparseIntArray soundsMap;
    private boolean        bSoundOn = true;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mContext = this;

//        ACRA.getConfig().setResToastText(R.string.crash_toast_text);
//        ACRA.getConfig().setResDialogText(R.string.crash_dialog_text);
//        ACRA.getConfig().setResDialogTitle(R.string.crash_dialog_title);
//        ACRA.getConfig().setResDialogCommentPrompt(R.string.crash_dialog_comment_prompt);
//        ACRA.getConfig().setResDialogOkToast(R.string.crash_dialog_ok_toast);
//        ACRA.init(this);
    }

    public static AppCore getContext()
    {
        return mContext;
    }

    public void startSoundPool(Context context)
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true);

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        soundsMap = new SparseIntArray();

        soundsMap.append(Defines.BOMB_TYPE_A, soundPool.load(context, R.raw.sound_big_bomb_2, 1));
        soundsMap.append(Defines.BOMB_TYPE_B, soundPool.load(context, R.raw.sound_mid_bomb, 1));
        soundsMap.append(Defines.BOMB_TYPE_C, soundPool.load(context, R.raw.sound_bomb_launch, 1));
    }

    public void soundOn()
    {
        this.bSoundOn = true;
        playMusic();
    }

    public void soundOff()
    {
        this.bSoundOn = false;
        stopMusic();
    }

    public void playMusic()
    {
        if (true == mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopMusic()
    {
        mediaPlayer.stop();
    }

    public void playSound(int bombType)
    {
        try
        {
            if (true == this.bSoundOn)
            {
                AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
                float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = streamVolumeCurrent / streamVolumeMax;
                soundPool.play(soundsMap.get(bombType), volume, volume, 1, 0, 1);
            }
        }
        catch (Exception e)
        {
            Logger.error(this.toString(), e);
        }
    }

}
