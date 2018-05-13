package edu.rosehulman.jonesjg1.spotifydj;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.UserPrivate;
import kaaes.spotify.webapi.android.models.UserPublic;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, OnFragmentInteractionListener {
    private static final String CLIENT_ID = "0cfd4201950a4a69a67f01bb5bf9d8a6";
    private static final String REDIRECT_URI = "Code-Croc-Spotify-DJ://callback";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;
    private SpotifyService mSpoty;
    private SpotifyApi mApi;
    private Party party;
    private UserPublic mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        mApi = new SpotifyApi();
        mSpoty = mApi.getService();
//        TracksPager tp = mSpoty.searchTracks("Buddy Holly");
//        String songuri = tp.tracks.items.get(0).uri;
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

    public void fetchUserInfo() {
        new AsyncTask<Void, Void, UserPrivate>() {

            @Override
            protected UserPrivate doInBackground(Void... voids) {
                UserPrivate userPrivate = null;
                try {
                    userPrivate = mSpoty.getMe();
                } catch (Exception e) {
                    Log.e("Error", "Error fetching UserInfo: " + e);
                }
                return userPrivate;
            }

            @Override
            protected void onPostExecute(UserPrivate userPrivate) {
                // Do what you need to with user data
                mUser = userPrivate;
                Log.d("USERID", mUser.id);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public SpotifyService getWebAPI(){
        return mSpoty;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mApi.setAccessToken(response.getAccessToken());
                        fetchUserInfo();
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

    public String getUserID() {
        return mUser.id;
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

    public Player getPlayer() {
        return mPlayer;
    }
    
    public void setCurrentQueue(Party party){
        this.party = party;
    }

    @Override
    public void changeFragment(int id) {
        Fragment switchTo = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.joinFragment) {
            switchTo = new JoinOrCreateFragment();

        } else if (id == R.id.queue_in_list) {
            switchTo = new QueueListFragment();
            mPlayer.pause(null);
        }
        else if (id == R.id.queue_fragment) {
            switchTo = new QueueFragment();
        }

        if(id == R.id.search_fragment){
            switchTo = new SearchFragment();
            ft.replace(R.id.fragment_main, switchTo);
            ft.commit();
        }

        if (switchTo != null && id != R.id.search_fragment) {
            ft.replace(R.id.fragment_main, switchTo);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public Party getParty() {
        return party;
    }
}
