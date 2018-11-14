package vn.loitp.app.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import loitp.basemaster.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.ActivityData;
import vn.loitp.data.AdmobData;
import vn.loitp.utils.util.UizaUtils;
import vn.loitp.utils.util.Utils;

//TODO is debug
public class LSApplication extends MultiDexApplication {
    private final String TAG = LSApplication.class.getSimpleName();
    private static LSApplication instance;
    private Gson gson;

    //prod
    public static final String DF_DOMAIN_API = "teamplayer.uiza.co";
    public static final String DF_TOKEN = "uap-01e137ad1b534004ad822035bf89b29f-b9b31f29";
    public static final String DF_APP_ID = "01e137ad1b534004ad822035bf89b29f";
    public static String entityIdDefaultVOD = "7699e10e-5ce3-4dab-a5ad-a615a711101e";
    public static String entityIdDefaultLIVE = "1759f642-e062-4e88-b5f2-e3022bd03b57";
    public static String metadataDefault0 = "53c2e63e-6ddf-4259-8159-cb43371943d1";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (gson == null) {
            gson = new Gson();
        }
        Constants.setIsDebug(true);
        Utils.init(this);
        //config admob id
        AdmobData.getInstance().setIdAdmobFull(getString(R.string.str_f));
        //config activity transition default
        ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT);

        //config realm
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //config font
        LUIUtil.setFontForAll(vn.loitp.core.common.Constants.FONT_PATH);

        //fcm
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC);

        //big imageview
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));

        //uiza rest api
        UizaUtils.initWorkspace(DF_DOMAIN_API, DF_TOKEN, Constants.URL_GET_LINK_PLAY_PROD);
    }

    public Gson getGson() {
        return gson;
    }

    public static LSApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
