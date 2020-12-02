package com.pooyeshgaran.nohe.util.player;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.pooyeshgaran.nohe.model.Data;
import com.pooyeshgaran.nohe.util.Constants;
import java.util.List;
import java.util.Random;
import static com.pooyeshgaran.nohe.util.Constants.Broadcast_START_VISUALIZER;
import static com.pooyeshgaran.nohe.util.Constants.INTENT_AUDIO_SESSION_ID;
import static com.pooyeshgaran.nohe.util.Constants.INTENT_IS_REPEAT;
import static com.pooyeshgaran.nohe.util.Constants.INTENT_IS_SHUFFLE;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = MediaPlayerService.class.getSimpleName();
    private final IBinder iBinder = new LocalBinder();
    private ServiceCallbacks serviceCallbacks;
    private MediaPlayer mediaPlayer;
    private int resumePosition;
    private AudioManager audioManager;
    private List<Data> dataList;
    private int songIndex = -1;
    private Data activeData; //An object of the currently playing song
    private boolean ongoingCall = false, isRepeat = false, isShuffle = false, mediaPlayerPrepared = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;
    private static final String ACTION_PLAY = "action_play";
    private static final String ACTION_PAUSE = "action_pause";
    private static final String ACTION_PREVIOUS = "action_previous";
    private static final String ACTION_NEXT = "action_next";
    private static final String ACTION_STOP = "action_stop";

    private void initMediaPlayer() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaFile location
            if (!activeData.getData().isEmpty()) {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(activeData.getData()));
            } else {
                mediaPlayer.setDataSource(activeData.getSongLink());
            }
            mediaPlayer.prepare();
            setVisualizerBroadcast();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage()+"");
            stopSelf();
        }
    }
    private void playMedia() {
        if (mediaPlayer == null) return;
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            if (serviceCallbacks != null) {
                serviceCallbacks.setPlayUI(activeData);
            }
        }
    }
    private void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            if (serviceCallbacks != null) {
                serviceCallbacks.setPlayUI(activeData);
            }
        }
    }
    public void pauseMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
            if (serviceCallbacks != null) {
                serviceCallbacks.setPlayUI(activeData);
            }
        }
    }
    public void resumeMedia() {
        if (mediaPlayer == null) return;
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
            if (serviceCallbacks != null) {
                serviceCallbacks.setPlayUI(activeData);
            }
        }
    }
    public void skipToNext() {
            Log.e("mmmm", "skipToNext songIndex: "+songIndex + "  listSize: "+dataList.size());
        if (songIndex == dataList.size() - 1) {
            if (serviceCallbacks != null)
                serviceCallbacks.setMoreSong();
//            songIndex = 0;/////////////////////////////
//            activeSong = songList.get(songIndex);
        } else {
            songIndex++;
            new StorageUtil(getApplicationContext()).storeSongIndex(songIndex);
//            activeSong = songList.get(++songIndex);
        }

        if (songIndex != -1 && songIndex < dataList.size()) {
            activeData = dataList.get(songIndex);
            //Update stored index
//            new StorageUtil(getApplicationContext()).storeSongIndex(songIndex, "skipToNext");///////
            stopMedia();
            mediaPlayer.reset();
            initMediaPlayer();
            if (serviceCallbacks != null) {
                serviceCallbacks.setActiveData(activeData);
                serviceCallbacks.setAd();
            }
        } else {
            stopSelf();
        }
    }
    public void skipToPrevious() {
        if(songIndex==-1) {
            stopSelf();
            return;
        }
        else if (songIndex == 0) {
            songIndex = dataList.size() - 1;
            activeData = dataList.get(songIndex);
        } else if(songIndex > 0) {
            songIndex--;
            activeData = dataList.get(songIndex);
        }
        //Update stored index
        new StorageUtil(getApplicationContext()).storeSongIndex(songIndex);
        stopMedia();
        mediaPlayer.reset();
        initMediaPlayer();
        if (serviceCallbacks != null) {
            serviceCallbacks.setActiveData(activeData);
            serviceCallbacks.setAd();
        }
    }
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
    public int getPosition(){
        if (mediaPlayerPrepared) {
            if(mediaPlayer==null)
                return 0;
            else
                return mediaPlayer.getCurrentPosition();
        } else
            return 0;
    }
    public int getDuration(){
        if (mediaPlayerPrepared) {
            if(mediaPlayer==null)
                return 0;
            else
                return mediaPlayer.getDuration();
        }
        else
            return 0;
    }
    public void seek(int position){
        mediaPlayer.seekTo(position);
    }
    private void changeActiveSong() {
        activeData = dataList.get(songIndex);
        new StorageUtil(getApplicationContext()).storeSongIndex(songIndex);
        stopMedia();
        mediaPlayer.reset();
        initMediaPlayer();
        if (serviceCallbacks != null) {
            serviceCallbacks.setActiveData(activeData);
        }
    }

    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
        if (serviceCallbacks != null) {
            serviceCallbacks.setActiveData(activeData);
            serviceCallbacks.setPlayUI(activeData);
        }
    }

    private BroadcastReceiver playNewAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get the new media index form SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            songIndex = storage.loadSongIndex();
            Log.e("mmmmm", "onReceive: "+songIndex );
            if (songIndex != -1 && songIndex < dataList.size()) {
                activeData = dataList.get(songIndex);
            } else {
                stopSelf();
            }
            //A PLAY_NEW_AUDIO action received, reset mediaPlayer to play the new Audio
            stopMedia();
            mediaPlayer.reset();
            initMediaPlayer();

            if (serviceCallbacks != null) {
                serviceCallbacks.setActiveData(activeData);
            }
        }
    };
    private void registerPlayNewAudio() {
        //Register playNewMedia receiver
        IntentFilter filter = new IntentFilter(Constants.Broadcast_PLAY_NEW_AUDIO);
        registerReceiver(playNewAudio, filter);
    }

    private BroadcastReceiver playNewListAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StorageUtil storage = new StorageUtil(getApplicationContext());
            dataList = storage.loadSong();
            songIndex = storage.loadSongIndex();

            if (songIndex != -1 && songIndex < dataList.size()) {
                activeData = dataList.get(songIndex);
            } else {
                stopSelf();
            }
            stopMedia();
            mediaPlayer.reset();
            initMediaPlayer();
            if (serviceCallbacks != null) {
                serviceCallbacks.setActiveData(activeData);
            }
        }
    };
    private void registerPlayNewListAudio() {
        IntentFilter filter = new IntentFilter(Constants.Broadcast_PLAY_NEW_LIST_AUDIO);
        registerReceiver(playNewListAudio, filter);
    }

    //Becoming noisy
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();
        }
    };
    private void registerBecomingNoisyReceiver() {
        //Register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);
    }

    //Change play (shuffle - repeat)
    private BroadcastReceiver changePlayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isRepeat = intent.getBooleanExtra(INTENT_IS_REPEAT, false);
            isShuffle = intent.getBooleanExtra(INTENT_IS_SHUFFLE, false);
        }
    };
    private void registerChangePlayReceiver() {
        IntentFilter filter = new IntentFilter(Constants.Broadcast_CHANGE_PLAY);
        registerReceiver(changePlayReceiver, filter);
    }

    //Handle incoming phone calls
    private void callStateListener() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    //if at least one call exists or the phone is ringing pause the MediaPlayer
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            ongoingCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        //Phone idle. Start playing
                        if (mediaPlayer != null) {
                            if (ongoingCall) {
                                ongoingCall = false;
                                resumeMedia();
                            }
                        }
                        break;
                }
            }
        };
        //Register the listener with the telephony manager.Listen for changes to the device call state.
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    private PendingIntent playbackAction(int actionNumber) {
        Intent playbackAction = new Intent(this, MediaPlayerService.class);
        switch (actionNumber) {
            case 0:
                playbackAction.setAction(ACTION_PLAY);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 1:
                playbackAction.setAction(ACTION_PAUSE);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
//            case 2:
//                playbackAction.setAction(ACTION_NEXT);
//                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
//            case 3:
//                playbackAction.setAction(ACTION_PREVIOUS);
//                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            default:
                break;
        }
        return null;
    }

    private void setVisualizerBroadcast() {
        Intent intent = new Intent(Broadcast_START_VISUALIZER);
        intent.putExtra(INTENT_AUDIO_SESSION_ID, mediaPlayer.getAudioSessionId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

// -------------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //Load data from SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            dataList = storage.loadSong();
            songIndex = storage.loadSongIndex();

            if (songIndex != -1 && songIndex < dataList.size()) {
                //index is in a valid range
                activeData = dataList.get(songIndex);
            } else {
                stopSelf();
            }
        } catch (NullPointerException e) {
            stopSelf();
        }
        //Request audio focus
        if (!requestAudioFocus()) {
            //Could not gain focus
            stopSelf();
        }
        if (mediaSessionManager == null) {
            initMediaPlayer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        callStateListener();
        registerBecomingNoisyReceiver();
        registerPlayNewAudio();
        registerChangePlayReceiver();
        registerPlayNewListAudio();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
            mediaPlayer = null;////!!!
        }
        removeAudioFocus();
        //Disable the PhoneStateListener
        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        if (becomingNoisyReceiver != null)
            unregisterReceiver(becomingNoisyReceiver);
        if (playNewAudio != null)
            unregisterReceiver(playNewAudio);
        if (changePlayReceiver != null)
            unregisterReceiver(changePlayReceiver);
        if (playNewListAudio != null)
            unregisterReceiver(playNewListAudio);
        //Clear cached playlist
        new StorageUtil(getApplicationContext()).clearCachedSongPlaylist();
        mediaPlayerPrepared = false;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {//////////
        super.onTaskRemoved(rootIntent);
        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        removeAudioFocus();
        //Disable the PhoneStateListener
        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        if (becomingNoisyReceiver != null)
            unregisterReceiver(becomingNoisyReceiver);
        if (playNewAudio != null)
            unregisterReceiver(playNewAudio);
        if (changePlayReceiver != null)
            unregisterReceiver(changePlayReceiver);
        if (playNewListAudio != null)
            unregisterReceiver(playNewListAudio);
        //Clear cached playlist
        new StorageUtil(getApplicationContext()).clearCachedSongPlaylist();
        mediaPlayerPrepared = false;
    }

// -------------------------------------------------------------------------------------------------

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // Resume playback
                if (mediaPlayer == null) initMediaPlayer();
                else if (!mediaPlayer.isPlaying()) mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing at an attenuated level
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }
    private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(playbackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this)
                    .build();

            int result = audioManager.requestAudioFocus(focusRequest);
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        } else {
            int result = 0;
            if (audioManager != null) {
                result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }
    }
    private void removeAudioFocus() {
        if (audioManager != null)
            audioManager.abandonAudioFocus(this);
    }

// -------------------------------------------------------------------------------------------------

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {}

    @Override
    public void onCompletion(MediaPlayer mp) {
        int currentDuration = mediaPlayer.getDuration();
        if(currentDuration == 0) return;
//        stopMedia();
//        stopSelf();
        if (isRepeat) {
            //repeat is on play same song again
            changeActiveSong();
        } else if (isShuffle) {
            //shuffle is on - play a random song
            Random rand = new Random();
            if (dataList.size() > 0)
                songIndex = rand.nextInt((dataList.size() - 1) - 0 + 1) + 0;
            changeActiveSong();
        } else {
            //no repeat or shuffle ON - play next song
            skipToNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayerPrepared = false;
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.e(TAG, "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e(TAG, "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e(TAG, "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayerPrepared = true;
        playMedia();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {}

// -------------------------------------------------------------------------------------------------
    /**
     * Service Binder
     */
    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            //Return this instance of LocalService so clients can call public methods
            return MediaPlayerService.this;
        }
    }

}
