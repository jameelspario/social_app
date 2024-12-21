package com.deificindia.indifun.rest;

import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.Model.FollowAddModel;
import com.deificindia.indifun.Model.GiftSenderList_Response;
import com.deificindia.indifun.Model.GoldenCoinModal;
import com.deificindia.indifun.Model.HeartPoint;
import com.deificindia.indifun.Model.Recommendadmodel;
import com.deificindia.indifun.Model.abs.DataMod;
import com.deificindia.indifun.Model.abs.StringModal;
import com.deificindia.indifun.Model.abs2Modals.CheckinResult;
import com.deificindia.indifun.Model.abs2Modals.LeaderBoardModel;
import com.deificindia.indifun.Model.abs_plugs.AddBroadResult;
import com.deificindia.indifun.Model.abs_plugs.BlockList;
import com.deificindia.indifun.Model.abs_plugs.BlockModal;
import com.deificindia.indifun.Model.abs_plugs.BroadWatched;
import com.deificindia.indifun.Model.abs_plugs.GiftDuringBroad;
import com.deificindia.indifun.Model.abs_plugs.GiftPoint;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.Model.abs_plugs.PkInvitation;
import com.deificindia.indifun.Model.abs_plugs.PkResult;
import com.deificindia.indifun.Model.abs_plugs.ProfileGalleryModal;
import com.deificindia.indifun.Model.abs_plugs.ServerTime;
import com.deificindia.indifun.Model.abs_plugs.UserBroadLevel;
import com.deificindia.indifun.Model.abs_plugs.UserLevelModal;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs_plugs.CoinRecordResult;
import com.deificindia.indifun.Model.abs_plugs.UserShotProfile;
import com.deificindia.indifun.Model.abs_plugs.XPModal;
import com.deificindia.indifun.Model.abs_plugs.ZipGiftItem;
import com.deificindia.indifun.Model.retro.ChatModel_Result;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.Model.retro.MsgModel_Result;
import com.deificindia.indifun.Model.retro.NewstarIndiaResult;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.MySharePref;

import java.io.IOException;
import java.util.List;

import com.deificindia.indifun.bindingmodals.UserProfileResult;
import com.deificindia.indifun.deificpk.modals.MusicInfo;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.vm.DataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.rest.RetroCallListener.*;

/*
RetroCalls.instance().callType(ONFOLLOWCLICK).listeners(obj -> {

}, (type, code, message) -> {

});
* */

public class RetroCalls {

    private static final String TAG = "RetroCalls";

    int call_type;
    String str_uid;
    String str_fuid;
    String[] str_params;
    int[] int_params;
    long[] long_params;
    int _index, _size;

    static RetroCalls INST;
    RetroCallListener.OnSuccessListener onsuccess;
    OnSuccessListenerV2<UserLevelModal> onsuccess2;
    RetroCallListener.OnFailListener onFail;

    static Result creadential;

    public static synchronized RetroCalls instance(){
            INST = new RetroCalls();
        creadential = IndifunApplication.getInstance().getCredential();
        return INST;
    }

    public RetroCalls callType(int ctype){
        this.call_type = ctype;
        return this;
    }

    public RetroCalls withUID(){
        this.str_uid = MySharePref.getUserId();
        return this;
    }

    public RetroCalls withFUID(){
        this.str_fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return this;
    }

    public RetroCalls stringParam(String... str){
        this.str_params = str;
        return this;
    }

    public RetroCalls intParam(int... ints){
        this.int_params = ints;
        return this;
    }

    public RetroCalls long_params(long... long_params) {
        this.long_params = long_params;
        return this;
    }

    public RetroCalls listeners(OnFailListener onFail){
        this.onFail = onFail;
        return this;
    }

   /* public void listeners(RetroCallListener.OnSuccessListener onSuccess, OnFailListener onFail){
        this.onsuccess = onSuccess;
        listeners(onFail);
        this.callMethods();
    }*/


    public RetroCalls setOnFail(OnFailListener onFail) {
        this.onFail = onFail;

        return  this;
    }

    void callMethods(){
        switch (call_type){
            case ONFOLLOWCLICK:
                //live_follow_click();
                break;
            case FOLLOW_POST:
                //follow_post();
                break;
            case USER_PROFILE:
                //USER_PROFILE();
                break;
            case GET_PROFILE_PHOTOS:
                //GET_PROFILE_PHOTOS();
                break;
            case ADD_PROFILE_PHOTOS:
                //ADD_PROFILE_PHOTOS();
                break;
            case USER_PROFILE_UPDATE:
                //USER_PROFILE_UPDATE();
                break;
            case USER_INTERESTS:
                //USER_INTERESTS();
                break;
            case LANGUAGE_LIST:
                //LANGUAGE_LIST();
                break;
            case GET_POST:
                //GET_POST();
                break;
            case BROADCAST_BETWEEN:
                //broadcast_between();
                break;
            case GENERATE_TOKEN:
                //generate_token();
                break;
            case REQUEST_TOKEN:
                //request_token();
                break;
            case GET_USER_LEVEL:
                break;
            case HEART_POINT:
                break;
        }
    }

    public void getInputStream(LottieAnimationView lottieAnimationView, String url, OnInputStream lstnr){
        /*Call<ResponseBody> call = AppConfig.loadInterface().downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                if(lstnr!=null && inputStream !=null) lstnr.onResult(inputStream,lottieAnimationView);
                else lstnr.onError("Null Error");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(lstnr!=null && t!=null) lstnr.onError(t.getMessage());
            }
        });*/
    }

    /*201023*/
    public void follow_user(OnSuccessListenerV2<ObjectModal<FollowAddModel>> onsuccess3) {
        //Logger.loge(TAG, str_uid, str_params[0]);
        Call<ObjectModal<FollowAddModel>> call = AppConfig.loadInterface()
                .live_follow_click(this.str_uid/*local uid*/, str_params[0]/*remote uid*/);
        call.enqueue(new Callback<ObjectModal<FollowAddModel>>() {
            @Override
            public void onResponse(Call<ObjectModal<FollowAddModel>> call, Response<ObjectModal<FollowAddModel>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<FollowAddModel>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(ONFOLLOWCLICK, 1, t.getMessage());
                }
            }
        });
    }

    public void unfollow_friend(OnSuccessListenerV2<PostModal> onsuccess3) {
        //Logger.loge(TAG, str_uid, str_params[0]);
        Call<PostModal> call = AppConfig.loadInterface()
                .unfollow_friend(this.str_uid/*local uid*/, str_params[0]/*remote uid*/);
        call.enqueue(new Callback<PostModal>() {
            @Override
            public void onResponse(Call<PostModal> call, Response<PostModal> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<PostModal> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }


    //like comment
    public void follow_post(OnSuccessListenerV2<PostModal> onsuccess3) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<PostModal> call = AppConfig.loadInterface()
                .follow_post(this.str_uid, str_params[0], int_params[0], str_params[1]);
        call.enqueue(new Callback<PostModal>() {
            @Override
            public void onResponse(Call<PostModal> call, Response<PostModal> response) {

                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<PostModal> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }


    public void GET_PROFILE_PHOTOS(OnSuccessListenerV2<ObjectModal<List<ProfileGalleryModal>>> onsuccess3/*String user_id,String image*/) {
        //Logger.loge(TAG, str_uid, str_params[0]);
        Call<ObjectModal<List<ProfileGalleryModal>>> call = AppConfig.loadInterface().get_profile_photos(this.str_uid);
        call.enqueue(new Callback<ObjectModal<List<ProfileGalleryModal>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<ProfileGalleryModal>>> call, Response<ObjectModal<List<ProfileGalleryModal>>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<ProfileGalleryModal>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }


    private void GET_POST(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        /*Call<GetPost> call = AppConfig.loadInterface().get_post1(this.str_uid);
        call.enqueue(new Callback<GetPost>() {
            @Override
            public void onResponse(Call<GetPost> call, Response<GetPost> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPost> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });*/

    }

    private void broadcast_between(/*String user_id*/) {
        Logger.loge(TAG, str_params[0], str_params[1]);
        Call<ResponseBody> call = AppConfig.loadInterface().broadcast_between(str_params[0], str_params[1]);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }


    public void get_user_broadcast_level(OnSuccessListenerV2<ObjectModal<UserBroadLevel>> onsuccess3){
        AppConfig.loadInterface()
                .get_user_broadcast_level(str_uid)
                .enqueue(new Callback<ObjectModal<UserBroadLevel>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<UserBroadLevel>> call, Response<ObjectModal<UserBroadLevel>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<UserBroadLevel>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void get_user_level(OnSuccessListenerV2<ObjectModal<UserLevelModal>> onsuccess3){
        AppConfig.loadInterface()
                .get_user_level(str_uid)
                .enqueue(new Callback<ObjectModal<UserLevelModal>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<UserLevelModal>> call, Response<ObjectModal<UserLevelModal>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<UserLevelModal>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void heart_point(OnSuccessListenerV2<HeartPoint> onsuccess3){
        AppConfig.loadInterface()
                .heart_point(str_params[0], str_uid, str_params[1])
                .enqueue(new Callback<HeartPoint>() {
                    @Override
                    public void onResponse(Call<HeartPoint> call, Response<HeartPoint> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<HeartPoint> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    /*----leader-Boeard--------*/
    public void leaderBoardDiamond(OnSuccessListenerV2<ObjectModal<List<LeaderBoardModel>>> onsuccess3){
        LoadInterface loadInterface = AppConfig.loadInterface();
        Call<ObjectModal<List<LeaderBoardModel>>> call;
        switch (call_type) {
            case 110:
                call = loadInterface.global_diamond_hourly();
                break;
            case 210:
                call = loadInterface.global_diamond_daily();
                break;
            case 310:
                call = loadInterface.global_diamond_weekly();
                break;
            case 410:
                call = loadInterface.local_diamond_hourly();
                break;
            case 510:
                call = loadInterface.local_diamond_daily();
                break;
            case 610:
                call = loadInterface.local_diamond_weekly();
                break;
            default:
                call = loadInterface.global_diamond_hourly();
        }

        call.enqueue(new Callback<ObjectModal<List<LeaderBoardModel>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<LeaderBoardModel>>> call, Response<ObjectModal<List<LeaderBoardModel>>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<LeaderBoardModel>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }

    public void leaderBoarcoin(OnSuccessListenerV2<ObjectModal<List<LeaderBoardModel>>> onsuccess3){
        LoadInterface loadInterface = AppConfig.loadInterface();
        Call<ObjectModal<List<LeaderBoardModel>>> call;
        switch (call_type) {
            case 110:
                call = loadInterface.global_coin_hourly();
                break;
            case 210:
                call = loadInterface.global_coin_daily();
                break;
            case 310:
                call = loadInterface.global_coin_weekly();
                break;
            case 410:
                call = loadInterface.local_coin_hourly();
                break;
            case 510:
                call = loadInterface.local_coin_daily();
                break;
            case 610:
                call = loadInterface.local_coin_weekly();
                break;
            default:
                call = loadInterface.global_coin_hourly();
        }

        call.enqueue(new Callback<ObjectModal<List<LeaderBoardModel>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<LeaderBoardModel>>> call, Response<ObjectModal<List<LeaderBoardModel>>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<LeaderBoardModel>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }

    public void leaderBoarfollower(OnSuccessListenerV2<ObjectModal<List<LeaderBoardModel>>> onsuccess3){
        LoadInterface loadInterface = AppConfig.loadInterface();
        Call<ObjectModal<List<LeaderBoardModel>>> call;
        switch (call_type) {
            case 110:
                call = loadInterface.global_follower_hourly();
                break;
            case 210:
                call = loadInterface.global_follower_daily();
                break;
            case 310:
                call = loadInterface.global_follower_weekly();
                break;
            case 410:
                call = loadInterface.local_follower_hourly();
                break;
            case 510:
                call = loadInterface.local_follower_daily();
                break;
            case 610:
                call = loadInterface.local_follower_weekly();
                break;
            default:
                call = loadInterface.global_follower_hourly();
        }

        call.enqueue(new Callback<ObjectModal<List<LeaderBoardModel>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<LeaderBoardModel>>> call, Response<ObjectModal<List<LeaderBoardModel>>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<LeaderBoardModel>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });
    }

    public void coin_record(OnSuccessListenerV2<ObjectModal<List<CoinRecordResult>>> onsuccess3){
        AppConfig.loadInterface()
                .coin_record(str_uid)
                .enqueue(new Callback<ObjectModal<List<CoinRecordResult>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<CoinRecordResult>>> call, Response<ObjectModal<List<CoinRecordResult>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(COIN_RECORD, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<CoinRecordResult>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(COIN_RECORD, 1, t.getMessage());
                        }
                    }
                });
    }

    public void sendgift(OnSuccessListenerV2<ObjectModal<GiftPoint>> onsuccess3/*,
                         String to, String from, int gift_id, long pkid, long broadId, int ty*/){

       RetroConfig2.createService(LoadInterface.class, creadential.user_token)
                .gift_point(str_params[0]/*to*/,
                        str_params[1]/*from*/,
                        int_params[0]/*gift_id*/,
                        str_params[2]/*pkid*/,
                        str_params[3]/*broadId*/,
                        int_params[1]/*type*/,
                        str_params[4]/*local fuid*/,
                        str_params[5]/*remote fuid*/
                )
                .enqueue(new Callback<ObjectModal<GiftPoint>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<GiftPoint>> call, Response<ObjectModal<GiftPoint>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(COIN_RECORD, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<GiftPoint>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(18, 1, t.getMessage());
                        }
                    }
                });
    }



    public void update_checkintime(OnSuccessListenerV2<ObjectModal<CheckinResult>> onsuccess3){
        AppConfig.loadInterface()
                .update_checkintime(str_uid, str_fuid)
                .enqueue(new Callback<ObjectModal<CheckinResult>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<CheckinResult>> call, Response<ObjectModal<CheckinResult>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(CHECKINTIME, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<CheckinResult>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(COIN_RECORD, 1, t.getMessage());
                        }
                    }
                });
    }

    public void data_after_checkin(OnSuccessListenerV2<PostModal> onsuccess3){
        AppConfig.loadInterface()
                .data_after_checkin(str_uid, int_params[0])
                .enqueue(new Callback<PostModal>() {
                    @Override
                    public void onResponse(Call<PostModal> call, Response<PostModal> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(CHECKINTIME, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModal> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(COIN_RECORD, 1, t.getMessage());
                        }
                    }
                });
    }

    public void data_after_checkin2(OnSuccessListenerV2<PostModal> onsuccess3){
        AppConfig.loadInterface()
                .data_after_checkin2(str_uid, int_params[0])
                .enqueue(new Callback<PostModal>() {
                    @Override
                    public void onResponse(Call<PostModal> call, Response<PostModal> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(CHECKINTIME, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModal> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(COIN_RECORD, 1, t.getMessage());
                        }
                    }
                });
    }

    /////////////braodcasting apis//////////
    public void get_user_profile(OnSuccessListenerV2<ObjectModal<UserShotProfile>> onsuccess3){
        AppConfig.loadInterface()
                .get_user_profile(str_params[1], str_params[0])
                .enqueue(new Callback<ObjectModal<UserShotProfile>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<UserShotProfile>> call, Response<ObjectModal<UserShotProfile>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<UserShotProfile>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }


    public void broadast_by_userid(OnSuccessListenerV2<ObjectModal<List<BroadWatched>>> onsuccess3){
        AppConfig.loadInterface()
                .broadast_by_userid(str_uid, _index, _size)
                //.broadast_by_userid(str_uid)
                .enqueue(new Callback<ObjectModal<List<BroadWatched>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<BroadWatched>>> call, Response<ObjectModal<List<BroadWatched>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<BroadWatched>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void pk_invitation(OnSuccessListenerV2<ObjectModal<PkInvitation>> onsuccess3){
        AppConfig.loadInterface()
                .pk_invitation(str_params[0], str_params[1], str_params[2], str_params[3])
                .enqueue(new Callback<ObjectModal<PkInvitation>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<PkInvitation>> call, Response<ObjectModal<PkInvitation>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<PkInvitation>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void pk_end(OnSuccessListenerV2<ObjectModal<StringModal>> onsuccess3){
        AppConfig.loadInterface()
                .pk_end(str_params[0], str_params[1])
                .enqueue(new Callback<ObjectModal<StringModal>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<StringModal>> call, Response<ObjectModal<StringModal>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<StringModal>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void is_broadcasting(OnSuccessListenerV2<ObjectModal<AddBroadResult>> onsuccess3){
        AppConfig.loadInterface()
                .is_broadcasting(str_uid)
                .enqueue(new Callback<ObjectModal<AddBroadResult>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<AddBroadResult>> call, Response<ObjectModal<AddBroadResult>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<AddBroadResult>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    //used by volly
    public void go_live(OnSuccessListenerV2<ObjectModal<AddBroadResult>> onsuccess3){
        AppConfig.loadInterface()
                .go_live(str_uid, str_params[0], int_params[0], str_params[1])
                .enqueue(new Callback<ObjectModal<AddBroadResult>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<AddBroadResult>> call, Response<ObjectModal<AddBroadResult>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<AddBroadResult>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void update_broadcasting(OnSuccessListenerV2<PostModal> onsuccess3){
        AppConfig.loadInterface()
                .update_broadcasting(str_params[0], str_params[1])
                .enqueue(new Callback<PostModal>() {
                    @Override
                    public void onResponse(Call<PostModal> call, Response<PostModal> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModal> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void joiner_name(OnSuccessListenerV2<ObjectModal<JoinerName>> onsuccess3){
        AppConfig.loadInterface()
                .joiner_name(str_params[0])
                .enqueue(new Callback<ObjectModal<JoinerName>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<JoinerName>> call, Response<ObjectModal<JoinerName>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<JoinerName>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public ObjectModal<JoinerName> joiner_name_sync(){
        Call<ObjectModal<JoinerName>> call = AppConfig.loadInterface().joiner_name(str_params[0]);
        try {
            Response<ObjectModal<JoinerName>> t = call.execute();
            return t.body();
        } catch (IOException e) {
            return null;
        }
    }

    public void get_server_time(OnSuccessListenerV2<ObjectModal<ServerTime>> onsuccess3){
        AppConfig.loadInterface()
                .get_server_time()
                .enqueue(new Callback<ObjectModal<ServerTime>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<ServerTime>> call, Response<ObjectModal<ServerTime>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<ServerTime>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void blockuser(OnSuccessListenerV2<ObjectModal<BlockModal>> onsuccess3){
        AppConfig.loadInterface()
                .blockuser(str_uid, str_params[0], int_params[0])
                .enqueue(new Callback<ObjectModal<BlockModal>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<BlockModal>> call, Response<ObjectModal<BlockModal>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<BlockModal>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void blocklist(OnSuccessListenerV2<ObjectModal<List<BlockList>>> onsuccess3){
        AppConfig.loadInterface()
                .blocklist(str_uid)
                .enqueue(new Callback<ObjectModal<List<BlockList>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<BlockList>>> call, Response<ObjectModal<List<BlockList>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<BlockList>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void getChats(OnSuccessListenerV2<ObjectModal<List<ChatModel_Result>>> onsuccess3){
        AppConfig.loadInterface()
                .getChats(str_uid)
                .enqueue(new Callback<ObjectModal<List<ChatModel_Result>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<ChatModel_Result>>> call, Response<ObjectModal<List<ChatModel_Result>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<ChatModel_Result>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void user_messages(OnSuccessListenerV2<ObjectModal<List<MsgModel_Result>>> onsuccess3){
        AppConfig.loadInterface()
                .user_messages(str_params[0])
                .enqueue(new Callback<ObjectModal<List<MsgModel_Result>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<MsgModel_Result>>> call, Response<ObjectModal<List<MsgModel_Result>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<MsgModel_Result>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    /*
    * {"userto":"12","userfrom":"555","imageto":1,"imagefrom":1,"messanger_id":"2","message":"dxc","date":"2021-02-16","time":"02:53:04 PM","mili_time":1613467384237,"is_seen_1":1,"is_seen_2":0}
    *
    * */
    public void user_new_message(OnSuccessListenerV2<ObjectModal<MsgModel_Result>> onsuccess3){
        AppConfig.loadInterface()
                .user_new_message(str_uid, str_params[0], str_params[1])
                .enqueue(new Callback<ObjectModal<MsgModel_Result>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<MsgModel_Result>> call, Response<ObjectModal<MsgModel_Result>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<MsgModel_Result>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void get_zip_gifts(OnSuccessListenerV2<ObjectModal<List<ZipGiftItem>>> onsuccess3){
        AppConfig.loadInterface()
                .get_zip_gifts()
                .enqueue(new Callback<ObjectModal<List<ZipGiftItem>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<ZipGiftItem>>> call, Response<ObjectModal<List<ZipGiftItem>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<ZipGiftItem>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void music_list(OnSuccessListenerV2<ObjectModal<List<MusicInfo>>> onsuccess3){
        AppConfig.loadInterface()
                .music_list()
                .enqueue(new Callback<ObjectModal<List<MusicInfo>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<MusicInfo>>> call, Response<ObjectModal<List<MusicInfo>>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<MusicInfo>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void trending(OnSuccessListenerV2<ObjectModal<List<TrendingResult>>> onsuccess3) {
        AppConfig.loadInterface().trending(int_params[0], int_params[1])
                .enqueue(new Callback<ObjectModal<List<TrendingResult>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<TrendingResult>>> call, Response<ObjectModal<List<TrendingResult>>> response) {

                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<TrendingResult>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });
    }

    public void newstar_india(OnSuccessListenerV2<ObjectModal<List<NewstarIndiaResult>>> onsuccess3) {
       AppConfig.loadInterface().newstar_india(int_params[0], int_params[1])
        .enqueue(new Callback<ObjectModal<List<NewstarIndiaResult>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<NewstarIndiaResult>>> call, Response<ObjectModal<List<NewstarIndiaResult>>> response) {
                if(onsuccess3!=null){
                    onsuccess3.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<List<NewstarIndiaResult>>> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });
    }

    public void post_follow(OnSuccessListenerV2<ObjectModal<List<HotpostResult>>> onsuccess3){
        AppConfig.loadInterface().post_follow(str_uid, int_params[0], int_params[1])
                .enqueue(new Callback<ObjectModal<List<HotpostResult>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<HotpostResult>>> call, Response<ObjectModal<List<HotpostResult>>> response) {

                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<HotpostResult>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void post_public(OnSuccessListenerV2<ObjectModal<List<HotpostResult>>> onsuccess3){
        AppConfig.loadInterface().post_public(str_uid, int_params[0], int_params[1])
                .enqueue(new Callback<ObjectModal<List<HotpostResult>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<HotpostResult>>> call, Response<ObjectModal<List<HotpostResult>>> response) {

                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<HotpostResult>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public void post_recommended(OnSuccessListenerV2<ObjectModal<List<Recommendadmodel>>> onsuccess3){
        AppConfig.loadInterface()
                .post_recommended(str_params[0], str_params[1], str_params[2], int_params[0], int_params[1])
                .enqueue(new Callback<ObjectModal<List<Recommendadmodel>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<Recommendadmodel>>> call, Response<ObjectModal<List<Recommendadmodel>>> response) {

                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(call_type, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<Recommendadmodel>>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(call_type, 1, t.getMessage());
                        }
                    }
                });
    }

    public ObjectModal<List<LevelData>> level_data(){
        Call<ObjectModal<List<LevelData>>> call = AppConfig.loadInterface().level_data();
        try {
            Response<ObjectModal<List<LevelData>>> t = call.execute();
            return t.body();
        } catch (IOException e) {
            return null;
        }
    }

    public GiftSenderList_Response gift_sender_list(){
        Call<GiftSenderList_Response> call = AppConfig.loadInterface().gift_sender_list(long_params[0]);
        try {
            Response<GiftSenderList_Response> t = call.execute();
            return t.body();
        } catch (IOException e) {
            return null;
        }
    }

    public void know_golden_coin(OnSuccessListenerV2<ObjectModal<GoldenCoinModal>> onsuccess3){

        RetroConfig2
                .createService(LoadInterface.class, str_params[0])
                .know_golden_coin()
                .enqueue(new Callback<ObjectModal<GoldenCoinModal>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<GoldenCoinModal>> call, Response<ObjectModal<GoldenCoinModal>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<GoldenCoinModal>> call, Throwable t) {

                        if(onFail!=null){
                            onFail.onError(0, 1, t.getMessage());
                        }
                    }
                });
    }

    public void subscription(OnSuccessListenerV2<AristocracySubcriptionResp> onsuccess3){

        RetroConfig2
                .createService(LoadInterface.class, str_params[0])
                .subscription(str_params[1],str_params[2],str_params[3])
                .enqueue(new Callback<AristocracySubcriptionResp>() {
                    @Override
                    public void onResponse(Call<AristocracySubcriptionResp> call, Response<AristocracySubcriptionResp> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AristocracySubcriptionResp> call, Throwable t) {

                        if(onFail!=null){
                            onFail.onError(0, 1, t.getMessage());
                        }
                    }
                });
    }
    public void create_family(OnSuccessListenerV2<AristocracySubcriptionResp> onsuccess3){

        RetroConfig2
                .createService(LoadInterface.class, str_params[0])
                .create_family(str_params[1],str_params[2],str_params[3],str_params[4],str_params[5])
                .enqueue(new Callback<AristocracySubcriptionResp>() {
                    @Override
                    public void onResponse(Call<AristocracySubcriptionResp> call, Response<AristocracySubcriptionResp> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AristocracySubcriptionResp> call, Throwable t) {

                        if(onFail!=null){
                            onFail.onError(0, 1, t.getMessage());
                        }
                    }
                });
    }


    public void family_join_request(OnSuccessListenerV2<AristocracySubcriptionResp> onsuccess3){

        AppConfig.loadInterface()
                //.createService(LoadInterface.class,null)
                .family_join_request(str_params[0],str_params[1],str_params[2])
                .enqueue(new Callback<AristocracySubcriptionResp>() {
                    @Override
                    public void onResponse(Call<AristocracySubcriptionResp> call, Response<AristocracySubcriptionResp> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AristocracySubcriptionResp> call, Throwable t) {

                        if(onFail!=null){
                            onFail.onError(0, 1, t.getMessage());
                        }
                    }
                });
    }

    public void join_broad(OnSuccessListenerV2<ObjectModal<Object>> onsuccess3){

        RetroConfig2.createService(LoadInterface.class, creadential.user_token)
                .join_broad(
                        str_params[0],
                        str_params[1],
                        str_params[2],
                        str_params[3]
                )
                .enqueue(new Callback<ObjectModal<Object>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<Object>> call, Response<ObjectModal<Object>> response) {
                        if(onsuccess3!=null){
                            onsuccess3.onSuccessResult(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<Object>> call, Throwable t) {
                        if(onFail!=null){
                            onFail.onError(0, 1, t.getMessage());
                        }
                    }
                });
    }



}
