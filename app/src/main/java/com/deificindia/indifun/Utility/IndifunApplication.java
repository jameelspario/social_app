package com.deificindia.indifun.Utility;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.StringDef;
import androidx.multidex.MultiDex;
import androidx.preference.PreferenceManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.deificindia.indifun.R;


import com.deificindia.indifun.crashLytics.CrashConfig;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveDao;
import com.deificindia.indifun.db.TempDao;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.deificpk.modals.LiveConfig;
import com.deificindia.indifun.interfaces.RxBus;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.services.DailyTaskWork;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.opensource.svgaplayer.SVGAParser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.deificindia.indifun.Utility.MySharePref.deleteData;

public class IndifunApplication extends Application {

    private static final String APP_LEVEL_PREFS = "applogindata";
    private static final String APP_LEVEL_PREFS_2 = "applogindata2";

    public static final String CONSTANT_USERDATA = "logindata";
    private static final String CONSTANT_PASSWORD = "password";
    private static final String CONSTANT_LANGUAGE = "language";
    private static final String CONSTANT_USERID = "userid";
    private static final String CONSTANT_USERNAME = "username";
    private static final String CONSTANTMOBILE = "mobile";
    private static final String CONSTANT_EMAIL = "email";
    private static final String CONSTANUSERIMG = "userimg";
    private static final String CONSTANT_COUNTRYID = "countryid";
    private static final String CONSTANT_COUNTRYENAME = "ecountryname";
    private static final String CONSTANT_COUNTRYANAME = "acountryname";
    private static final String CONSTANT_COUNTRYCODE = "countrycode";
    private static final String ADRESSID = "addressid";
    private static final String WALLET = "wallet";
    private static final String CONSTANT_CITYNAME = "cityname";
    private static final String CONSTANT_CITYID = "cityid";
    private static final String CONSTANT_COUNTRYCURRENCY = "countrycurrency";
    private static final String CONSTANT_ECITYNAME = "ecityname";

    public static final String CHANNEL_ID = "IndifunChannel";


    public static final String TAG = IndifunApplication.class
            .getSimpleName();


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENGLISH, ARABIC})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {ENGLISH, ARABIC};
    }

    public static final String ENGLISH = "en";
    public static final String ARABIC = "ar";
    private static final String LANGUAGE_KEY = "language_key";

    private static IndifunApplication mInstance;

    private SharedPreferences mMyDatas;
    private SharedPreferences mPref;
    private SharedPreferences sharedPreferences;
    private SharedPreferences preferences;

    private TempDao tempDao;

    private LiveConfig liveConfig;
    private RxBus bus;

    public LiveDao liveDao;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mMyDatas = getSharedPreferences(MyDatasKt.MYDATAS, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(APP_LEVEL_PREFS, Context.MODE_PRIVATE);
        mPref = getSharedPreferences(APP_LEVEL_PREFS_2, Context.MODE_PRIVATE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        liveConfig = new LiveConfig(preferences);

        tempDao = TempDb.getDatabase(getApplicationContext()).dao();

        bus = new RxBus();
        liveDao = LiveAppDb.getDatabase(getApplicationContext()).userDao();

        Fresco.initialize(this,
                ImagePipelineConfig.newBuilder(getApplicationContext())
                        .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                        .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                        .experiment().setNativeCodeDisabled(true)
                        .build());

        createNotificationChannel();

        //CrashConfig.Builder.create().apply();

        //getWorkerThread().eventHandler().addEventHandler(this); // Move to User session if error
        SVGAParser.Companion.shareParser().init(this);
        initCrashReport();
        //initEngine(getString(R.string.agora_app_id)/*response.data.config.appId*/);

    }

    public static synchronized IndifunApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public synchronized Context setLocale(Context mContext) {
        return updateResources(mContext, getlang(mContext));
    }

    public synchronized String getlang(Context mContext) {
        return sharedPreferences.getString(CONSTANT_LANGUAGE, "");
    }


    public synchronized void savelang(Context mContext, String localeKey) {
        sharedPreferences.edit().putString(CONSTANT_LANGUAGE, localeKey).apply();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * get current locale
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale(this);
    }

    public synchronized SharedPreferences getmMyDatas(){
        return mMyDatas;
    }

    public synchronized TempDao getTempDao(){
        return tempDao;
    }

    public synchronized void saveCredential(Result loginModels) {
        if (loginModels != null) {
            Gson gson = new Gson();
            String json = gson.toJson(loginModels);
            sharedPreferences.edit().putString(CONSTANT_USERDATA, json).apply();
        }
    }

    public synchronized Result getCredential() {
        Result loginModel = null;
        try {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CONSTANT_USERDATA, null);
            loginModel = gson.fromJson(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginModel;
    }

    protected void setDefaultLocale(Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration appConfig = new Configuration();
        appConfig.locale = locale;
        context.getResources().updateConfiguration(appConfig, context.getResources().getDisplayMetrics());

    }

    public synchronized void logout() {
        //unsubscribetopic();
        sharedPreferences.edit().remove(CONSTANT_USERDATA).apply();
        SharedPreferences preferences = getSharedPreferences("counter_preference", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            FirebaseAuth.getInstance().signOut();

        deleteData(getApplicationContext());

    }

    ///////////Agora application///////////////////////////////////////////////////////////////////

    public SharedPreferences preferences() { return mPref; }

    public SharedPreferences getSharedPreferences(){ return sharedPreferences; }


    private void initCrashReport() {
        /*String buglyAppId = getResources().getString(R.string.bugly_app_id);
        if (TextUtils.isEmpty(buglyAppId)) {
            XLog.i("Bugly app id not found, crash report initialize skipped");
        } else {
            *//*CrashReport.initCrashReport(getApplicationContext(),
                    buglyAppId, BuildConfig.DEBUG);*//*
        }*/
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    //////////////////////////////////////////////////////End agora app//////

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //////////////////////////////////////////////////////////////////////

    public LiveConfig config() {

        return liveConfig;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void dailyTask(){
        PeriodicWorkRequest.Builder myWorkBuilder =
                new PeriodicWorkRequest.Builder(DailyTaskWork.class, 24, TimeUnit.HOURS);

        PeriodicWorkRequest myWork = myWorkBuilder.build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("DailyTask", ExistingPeriodicWorkPolicy.KEEP, myWork);
    }

    public RxBus bus() {
        return bus;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Indifun Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
