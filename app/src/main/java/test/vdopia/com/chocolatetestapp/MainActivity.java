package test.vdopia.com.chocolatetestapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vdopia.ads.lw.Chocolate;
import com.vdopia.ads.lw.ChocolateAdException;
import com.vdopia.ads.lw.InitCallback;
import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOConstants;
import com.vdopia.ads.lw.LVDORewardedAd;
import com.vdopia.ads.lw.RewardedAdListener;

public class MainActivity extends AppCompatActivity implements RewardedAdListener {

    private static final String TAG = "MainActivity";
    private static final String API_KEY = "XqjhRR";
    private LVDOAdRequest adRequest;
    private LVDORewardedAd rewardedAd;
    private LVDOConstants.PARTNER partner;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adRequest = new LVDOAdRequest(this);
        if (getIntent() != null && getIntent().hasExtra("partner")) {
            partner = (LVDOConstants.PARTNER) getIntent().getSerializableExtra("partner");
        }
        if (partner == null) {
            partner = LVDOConstants.PARTNER.ALL;
        }
        adRequest.addPartnerName(partner);
        Application.getInstance().getPartnerList().remove(partner);

        Chocolate.init(this, API_KEY, new InitCallback() {
            @Override
            public void onSuccess() {
                loadAd();
            }

            @Override
            public void onError(String s) {
                loadAd();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"onNewIntent ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onNewIntent ");
    }

    public void onStartTest(View view) {
    }

    @Override
    public void onRewardedVideoLoaded(LVDORewardedAd lvdoRewardedAd) {
        //secretID, userID, rewardName, rewardAmount
        Log.d(TAG,"onRewardedVideoLoaded "+lvdoRewardedAd.getWinningPartnerName());
        try {
            Toast.makeText(this, lvdoRewardedAd.getWinningPartnerName(), Toast.LENGTH_SHORT).show();
            lvdoRewardedAd.showRewardAd("123456789", "kevink", "coins", "30");
        }catch (ChocolateAdException e) {
            Toast.makeText(this, "failed to show rewarded ad", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardedVideoFailed(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {
        Toast.makeText(this, "onRewardedVideoFailed", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRewardedVideoFailed "+partner);
        done();
    }

    @Override
    public void onRewardedVideoShown(LVDORewardedAd lvdoRewardedAd) {
        Toast.makeText(this, "onRewardedVideoShown", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRewardedVideoShown "+lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onRewardedVideoShownError(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {
        Toast.makeText(this, "onRewardedVideoShownError", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRewardedVideoShownError "+lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onRewardedVideoDismissed(LVDORewardedAd lvdoRewardedAd) {
        Toast.makeText(this, "onRewardedVideoDismissed", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRewardedVideoDismissed "+lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onRewardedVideoCompleted(LVDORewardedAd lvdoRewardedAd) {
        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onRewardedVideoCompleted "+lvdoRewardedAd.getWinningPartnerName());
        done();
    }

    private void done() {
        //dont wait until the timeout; load the next ad
        handler.removeCallbacks(runnable);
        loadNext();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        if (rewardedAd != null) {
            rewardedAd.destroyView();
        }
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void loadAd() {
        Log.d(TAG,"loadAd "+partner);
        rewardedAd = new LVDORewardedAd(this, API_KEY, this);
        rewardedAd.loadAd(adRequest);
        queueNext();
    }

    private void queueNext() {
        runnable = new Runnable() {
            @Override
            public void run() {
                loadNext();
            }
        };
        handler.postDelayed(runnable, 45000);
    }

    private void loadNext() {
        if (Application.getInstance().getPartnerList().isEmpty()) {
            Log.d(TAG,"done with all partners");
            finish(); //done testing
            return;
        }
        //finish();
        LVDOConstants.PARTNER partner = Application.getInstance().getPartnerList().get(0);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("partner", partner);
        startActivity(intent);
    }

}

