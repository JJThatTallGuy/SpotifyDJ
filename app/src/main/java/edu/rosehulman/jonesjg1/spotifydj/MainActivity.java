package edu.rosehulman.jonesjg1.spotifydj;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, OnFragmentInteractionListener {
    private static final String CLIENT_ID = "0cfd4201950a4a69a67f01bb5bf9d8a6";
    private static final String REDIRECT_URI = "Code-Croc-Spotify-DJ://callback";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;
    private Button buttonPausePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        buttonPausePlay = findViewById(R.id.pause_button);
        buttonPausePlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mPlayer.getPlaybackState().isPlaying) {
                    mPlayer.pause(null);
                } else {
                    mPlayer.resume(null);
                }
                updatePausePlay();
            }
        });

        Button heyJude = findViewById(R.id.play_middle);
        Button stairwayHeaven = findViewById(R.id.play_top);
        Button hotelCalifornia = findViewById(R.id.play_bottom);

        heyJude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:0aym2LBJBk9DAYuHHutrIl", 0, 0);
                updatePausePlay();
            }
        });

        stairwayHeaven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:5CQ30WqJwcep0pYcV4AMNc", 0, 0);
                updatePausePlay();
            }
        });

        hotelCalifornia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:40riOy7x9W7GXjyGp4pjAv", 0, 0);
                updatePausePlay();
            }
        });

    }

    public void updatePausePlay() {
        String s;
        if (mPlayer.getPlaybackState().isPlaying) {
            s = getString(R.string.play);
        } else {
            s = getString(R.string.pause);
        }
        buttonPausePlay.setText(s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");

        changeFragment(R.id.joinFragment);
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error var1) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void changeFragment(int id) {
        Fragment switchTo = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.joinFragment) {
            switchTo = new JoinOrCreateFragment();

        } else if (id == R.id.queue_in_list) {
            switchTo = new QueueListFragment();

        } else if (id == R.id.fragment_main) {
            for (Fragment frag : getSupportFragmentManager().getFragments()) {
                ft.remove(frag).commit();
            }
        }

        if (switchTo != null) {
            ft.replace(R.id.fragment_main, switchTo);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
