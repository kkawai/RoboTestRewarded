package test.vdopia.com.chocolatetestapp;

import android.support.multidex.MultiDexApplication;

import com.vdopia.ads.lw.LVDOConstants;

import java.util.ArrayList;
import java.util.List;

public class Application extends MultiDexApplication {

    private static final String TAG = "Application";
    private List<LVDOConstants.PARTNER> partnerList = new ArrayList<>();
    static Application instance;

    static Application getInstance() {
        return instance;
    }

    List<LVDOConstants.PARTNER> getPartnerList() {
        return partnerList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        partnerList.clear();
        partnerList.add(LVDOConstants.PARTNER.ALL);
        partnerList.add(LVDOConstants.PARTNER.CHOCOLATE);
        partnerList.add(LVDOConstants.PARTNER.ADCOLONY);
        partnerList.add(LVDOConstants.PARTNER.BAIDU);
        partnerList.add(LVDOConstants.PARTNER.CHARTBOOST);
        partnerList.add(LVDOConstants.PARTNER.FACEBOOK);
        partnerList.add(LVDOConstants.PARTNER.GOOGLEADMOB);
        partnerList.add(LVDOConstants.PARTNER.INMOBI);
        partnerList.add(LVDOConstants.PARTNER.OGURY);
        partnerList.add(LVDOConstants.PARTNER.TAPJOY);
        partnerList.add(LVDOConstants.PARTNER.UNITY);
        partnerList.add(LVDOConstants.PARTNER.VUNGLE);
        partnerList.add(LVDOConstants.PARTNER.YOUAPPI);
    }
}
