package com.deificindia.indifun.rest;

import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.FamilyDetails;
import com.deificindia.indifun.Model.FamilyDetailsRes;
import com.deificindia.indifun.Model.FollowAddModel;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.Model.GiftSenderList_Response;
import com.deificindia.indifun.Model.GoldenCoinModal;
import com.deificindia.indifun.Model.HeartPoint;
import com.deificindia.indifun.Model.PostCommentList_Responce;
import com.deificindia.indifun.Model.PostLikeList_Responce;
import com.deificindia.indifun.Model.Recommendadmodel;
import com.deificindia.indifun.Model.SalaryModel;
import com.deificindia.indifun.Model.TotalFamily;
import com.deificindia.indifun.Model.abs2Modals.LeaderBoardModel;
import com.deificindia.indifun.Model.abs_plugs.BlockList;
import com.deificindia.indifun.Model.abs_plugs.BlockModal;
import com.deificindia.indifun.Model.abs_plugs.BroadWatched;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.Model.abs_plugs.PkInvitation;
import com.deificindia.indifun.Model.abs_plugs.PkResult;
import com.deificindia.indifun.Model.abs_plugs.ZipGiftItem;
import com.deificindia.indifun.Model.LikeModel_Response;
import com.deificindia.indifun.Model.TokenRess;
import com.deificindia.indifun.Model.abs2Modals.CheckinResult;
import com.deificindia.indifun.Model.abs_plugs.AddBroadResult;
import com.deificindia.indifun.Model.abs_plugs.GiftDuringBroad;
import com.deificindia.indifun.Model.abs_plugs.GiftPoint;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.Model.abs_plugs.ProfileGalleryModal;
import com.deificindia.indifun.Model.abs_plugs.ServerTime;
import com.deificindia.indifun.Model.abs_plugs.SingleOnlineData;
import com.deificindia.indifun.Model.abs_plugs.UserBroadLevel;
import com.deificindia.indifun.Model.abs_plugs.UserLevelModal;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs.StringModal;
import com.deificindia.indifun.Model.abs_plugs.BackUpMall;
import com.deificindia.indifun.Model.abs_plugs.CoinRecordResult;
import com.deificindia.indifun.Model.abs_plugs.GoldenCoin;
import com.deificindia.indifun.Model.abs_plugs.GroupCategory;
import com.deificindia.indifun.Model.abs_plugs.MallProduct;
import com.deificindia.indifun.Model.abs_plugs.MedalRech;
import com.deificindia.indifun.Model.abs_plugs.Sticker;
import com.deificindia.indifun.Model.abs_plugs.UserShotProfile;
import com.deificindia.indifun.Model.contribution.ContributionModel;
import com.deificindia.indifun.Model.retro.ChatModel_Result;
import com.deificindia.indifun.Model.retro.Commentmodel_Response;
import com.deificindia.indifun.Model.retro.MsgModel_Result;
import com.deificindia.indifun.Model.retro.NewstarIndiaResult;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.Model.retro.TrendingModal;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.Model.retro.UserFollower;
import com.deificindia.indifun.Model.retro.UserFollowing;
import com.deificindia.indifun.Model.retro.UserFriends;
import com.deificindia.indifun.Model.retro.UserGroupList;
import com.deificindia.indifun.Model.retro.UserProfileUpdate;
import com.deificindia.indifun.bindingmodals.UserProfileResult;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyPlanResult;
import com.deificindia.indifun.bindingmodals.otheruserprofile.TopFans;
import com.deificindia.indifun.deificpk.modals.GiftListResponse2;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.Aristocracy;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyCenter;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyPlan;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.PrivilegChart;
import com.deificindia.indifun.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun.deificpk.modals.MusicInfo;
import com.deificindia.indifun.modals.Result;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoadInterface {

    @POST("login") Call<ResponseBody> login(@Query("contact") String user_id);

    @POST("signup")
    Call<ObjectModal<Result>> signup(@Query("contact") String user_id);

    /************user*search***********************/
    /*{  "following": 2, "followers": 0,   "friends": 1, "groups": 3   }  * */
    @POST("user_dashboard") Call<ResponseBody> user_dashboard(@Query("user_id") String user_id);

    ///get user profile APIs////
    @POST("user_profile") Call<ObjectModal<UserProfileResult>> user_profile(@Query("user_id") String user_id);

    @POST("user_profile_update") //6 || like comment
    Call<UserProfileUpdate> user_profile_update(
            @Query("user_id") String user_id,
            @Query("full_name") String full_name,
            @Query("gender") String gender,
            @Query("whatsup") String whatsup,
            @Query("relationship") String relationship,
            @Query("country") String country,
            @Query("state") String state,
            @Query("city") String city
    );


    @POST("other_user_profile")
    Call<OtherUserProfile> other_user_profile(@Query("user_id") String user_id,
                                              @Query("other_user_id") String other_user_id);

    //add photo in profile galary
    /*[
        {
            "id": "1",
            "user_id": "12",
            "image": "8e58f49eb8a0a9961843b204ac1f35a7.png",
            "entry_date": "2020-10-03"
        },*/
    //view photo in profile gallery
    @POST("get_profile_photos")
    Call<ObjectModal<List<ProfileGalleryModal>>> get_profile_photos(@Query("user_id") String user_id);


    /////////////////////////////////////////////////////////////////////////
    //201023/////start following user/
    @POST("follow_add")
    Call<ObjectModal<FollowAddModel>> live_follow_click(@Query("user_id") String user_id,
                                                        @Query("following_id") String following_id);


    @POST("unfollow_friend")
    Call<PostModal> unfollow_friend(@Query("user_id") String user_id, @Query("following_id") String following_id);

    @POST("follow_post")
    Call<PostModal> follow_post(@Query("user_id") String user_id,
                                @Query("post_id") String post_id,
                                @Query("is_like") int is_like,
                                @Query("comments") String comments
    );


    @POST("get_user_level")
    Call<ObjectModal<UserLevelModal>> get_user_level(@Query("user_id") String user_id);

    @POST("get_user_broadcast_level")
    Call<ObjectModal<UserBroadLevel>> get_user_broadcast_level(@Query("user_id") String user_id);

    @POST("purchase_golden_coins") //1
    Call<PostModal> purchase_golden_coins(@Query("user_id") String user_id,
                                          @Query("paid_for") String paid_for /*coins*/,
                                          @Query("transection_id") String transection_id /*jhjh111*/,
                                          @Query("paid_id") String paid_id
    );

    /************************medal**********************************/
    @GET("silvercoin_list") Call<ObjectModal<String>> silvercoin_list();
    @GET("goldencoin_list") Call<ObjectModal<GoldenCoin>> goldencoin_list();

    @POST("get_medal_share_live") Call<ObjectModal<List<MedalRech>>> get_medal_share_live(@Query("medal_category") String medal_category/*social*/);
    @POST("get_medal_recharge") Call<ObjectModal<List<MedalRech>>> get_medal_recharge(@Query("medal_category") String medal_category/*social*/);
    @POST("get_medal_check_in") Call<ObjectModal<List<MedalRech>>> get_medal_check_in();

    //Mall product list in mall tab
    @GET("mall_product")
    Call<ObjectModal<List<MallProduct>>> mall_product();

    /******user relates search***************************************************************************************************/

    //backup tab in mall option
    @POST("backpack")
    Call<ObjectModal<List<BackUpMall>>> backpack(@Query("user_id") String user_id);

    //@POST("get_profile_photos")
    // Call<GetProfilePhotos> get_profile_photos(@Query("user_id") String user_id);
    /*********************************************************************************************************/
    /*********************************************************************************************************/
////Home Tab
    @POST("follow_homepage")
    Call<GetFollow_Result> follow_homepage();

    @POST("follow_homepage_recommended")
    Call<GetFollow_Result> follow_homepage_recommended();

    @POST("hot_hompage")
    Call<GetFollow_Result> hot_homepage();

    ///Explore Tab
    @GET("country")
    Call<CountryNamesResult> getCountry();

    @POST("user_by_country")
    Call<TrendingModal> getCountryUsers(@Query("country_id") int countryid, @Query("index") int i, @Query("size") int s);
    /*201026 @spario*/
    @POST("newstar_india")
    Call<ObjectModal<List<NewstarIndiaResult>>> newstar_india(@Query("index") int i, @Query("size") int s);

    @POST("trending")
    Call<ObjectModal<List<TrendingResult>>> trending(@Query("index") int i, @Query("size") int s);

    ///Post Tab =>
    @POST("post_follow") ///post_follow
    Call<ObjectModal<List<HotpostResult>>> post_follow(@Query("user_id") String user_id,
                                                       @Query("index") int i, @Query("size") int s);
    @POST("post_public") ///post_public
    Call<ObjectModal<List<HotpostResult>>> post_public(@Query("user_id") String user_id,
                                                       @Query("index") int i, @Query("size") int s);
    @POST("post_recommended") //post_recommended
    Call<ObjectModal<List<Recommendadmodel>>> post_recommended(@Query("filter_by_gender") String filter_by_gender,
                                                               @Query("filter_by_age_from") String filter_by_age_from,
                                                               @Query("filter_by_age_to") String filter_by_age_to,
                                                               @Query("index") int i, @Query("size") int s
    );


    @POST("post_view") Call<ResponseBody> post_view(@Query("post_id") String user_id);


    //use token
    @POST("gift_point") //give gift by one user to other user
    Call<ObjectModal<GiftPoint>> gift_point(@Query("to") String to,
                                            @Query("from") String from,
                                            @Query("gift_id") int gift_id,
                                            @Query("pk_id") String pk_id, //local broadcaster pk id
                                            @Query("broadcast_id") String broadcast_id, //local broadcaster broad id
                                            @Query("type") int type, //2=broad|| 3==pk
                                            @Query("uid_local") String local_fuid, //local broadcaster fuid
                                            @Query("uid_remote") String remote_fuid //remote broadcaster fuid
    );


    @POST("get_gifts")//get gift list
    Call<GiftListResponse2> getGiftList(@Query("gift_category") int gift_category);

    @GET("get_sticker")
    Call<ObjectModal<List<Sticker>>> get_sticker();

    /****Group***********************************************/
    //create group
    @POST("user_groups")
    Call<ResponseBody> user_groups(@Query("made_by") String made_by, //12
                                   @Query("group_name") String group_name,
                                   @Query("group_category") String group_category,
                                   @Query("description") String description,
                                   @Query("entry_date") String entry_date /*2020-09-09*/
    );

    /*[
        {
            "id": "1",
            "category_name": "Recommended",
            "entry_date": "0000-00-00"
        },*/
    //> get group category of discover group
    @GET("group_category_list") Call<ObjectModal<List<GroupCategory>>> group_category_list();

    /*[
        {
            "id": "2",
            "group_name": "sham7",
            "cover": "11.jpeg",
            "made_by": "477",
            "group_category": "1",
            "description": "sjwbsj",
            "entry_date": "2020-11-09"
        },*/
    //>get group list by cat id
    @POST("get_group_bycatid")
    Call<List<StringModal>> get_group_bycatid(@Query("group_category") String group_category);
    @POST("join_groups") Call<StringModal> join_groups(@Query("user_id") String user_id, @Query("user_group_id") String user_group_id);
    ////user_groups_list
    @POST("exit_group") Call<StringModal> exit_group(@Query("user_id") String user_id, @Query("user_group_id") String user_group_id);
    @POST("del_group") Call<StringModal> del_group(@Query("id") String id);

    /*******************************************************************************/

    /*shamshad api*/
    @POST("notification_likes") //3
    Call<LikeModel_Response> getByUserid(@Query("user_id") String user_id);

    @POST("notification_comments") //3
    Call<Commentmodel_Response> getComments(@Query("user_id") String user_id);


    //@POST("get_post")
    //Call<GetPost> get_post(@Query("user_id") String user_id);

    @POST("heart_point")
    Call<HeartPoint> heart_point(@Query("to") String to,
                                 @Query("from") String from,
                                 @Query("broadcast_id") String broadid);

    /*201026 anuj*/
    @POST("user_friends") //11 || like user_friends
    Call<UserFriends> user_friends(@Query("user_id") String user_id);
    @POST("user_following") //11 || like user_following
    Call<UserFollowing> user_following(@Query("user_id") String user_id);
    @POST("user_follower") //10 || like user_follower
    Call<UserFollower> user_follower(@Query("user_id") String user_id);
    @POST("user_groups_list") //11 || like user_groups_list
    Call<UserGroupList> user_groups_list(@Query("user_id") String user_id);

    //MessageActivity
    @POST("user_chatbox") Call<ObjectModal<List<ChatModel_Result>>> getChats(@Query("user_id") String id);
    //
    @POST("user_messages") Call<ObjectModal<List<MsgModel_Result>>> user_messages(@Query("messanger_id") String id);
    //send msg to another user
    @POST("user_newchatbox") Call<StringModal> user_newchatbox(
            @Query("image") String image,
            @Query("sticker_id") String sticker_id,
            @Query("messanger_id") String messanger_id,
            @Query("message") String message
    );

    @POST("user_new_message")
    Call<ObjectModal<MsgModel_Result>> user_new_message(@Query("user_from") String id,
                                       @Query("user_to") String id1,
                                       @Query("message") String hii);



    @POST("broadcast_between")
    Call<ResponseBody> broadcast_between(@Query("add_broadcast_1") String add_broadcast_1, @Query("add_broadcast_2") String add_broadcast_2);

    ////onclick buy button aristocracy center plan
    @POST("aristocracy_center_privileges") Call<Aristocracy> aristocracy_center_privileges(@Query("aristocracy_center_id") int aristocracy_center_id);
    ///get aristocracy center tabs
    @GET("aristocracy_center") Call<ObjectModal<List<AristocracyCenter>>> aristocracy_center();
    ///onclick buy button aristocracy center plan
    @POST("aristocracy_center_plan")
    Call<AristocracyPlanResult> aristocracy_center_plan(@Query("aristocracy_center_id") int aristocracy_center_id);
    @GET("privileges_chart") Call<ObjectModal<List<PrivilegChart>>> privileges_chart();
    @GET("change_online_status") Call<ResponseBody> change_online_status(@Query("user_id") String user_id, @Query("status") int s);

    /************************************************************************************************/
    @POST("global_diamond_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_diamond_hourly();
    @POST("global_diamond_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> global_diamond_daily();
    @POST("global_diamond_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_diamond_weekly();
    @POST("local_diamond_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_diamond_hourly();
    @POST("local_diamond_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> local_diamond_daily();
    @POST("local_diamond_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_diamond_weekly();

    @POST("global_coin_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_coin_hourly();
    @POST("global_coin_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> global_coin_daily();
    @POST("global_coin_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_coin_weekly();
    @POST("local_coin_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_coin_hourly();
    @POST("local_coin_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> local_coin_daily();
    @POST("local_coin_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_coin_weekly();

    @POST("global_follower_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_follower_hourly();
    @POST("global_follower_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> global_follower_daily();
    @POST("global_follower_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> global_follower_weekly();
    @POST("local_follower_hourly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_follower_hourly();
    @POST("local_follower_daily")
    Call<ObjectModal<List<LeaderBoardModel>>> local_follower_daily();
    @POST("local_follower_weekly")
    Call<ObjectModal<List<LeaderBoardModel>>> local_follower_weekly();


    @POST("coin_record")
    Call<ObjectModal<List<CoinRecordResult>>> coin_record(@Query("user_id") String user_id);

    @GET("generate_token")
    Call<TokenRess> token(@Query("room") String room, @Query("identity") String identity);

    @POST("user_checkin")
    Call<ObjectModal<CheckinResult>> update_checkintime(@Query("user_id") String user_id, @Query("firebase_id") String fuid);

    @POST("data_after_checkin")
    Call<PostModal> data_after_checkin(@Query("user_id") String user_id, @Query("point") int point);

    @POST("data_after_checkin2")
    Call<PostModal> data_after_checkin2(@Query("user_id") String user_id, @Query("point") int point);


    ///////////////////////////////////////////////////
    ///////////braodcaste/////////////////////////////
    //broad watched
    @POST("broadast_by_userid")
    Call<ObjectModal<List<BroadWatched>>> broadast_by_userid(@Query("user_id") String userid,
                                                             @Query("index") int index,
                                                             @Query("size") int size);

    //generate pk id for user//
    @POST("pk_invitation")
    Call<ObjectModal<PkInvitation>> pk_invitation(@Query("broadcast_id") String str_uid,
                                                  @Query("duration") String pk_duration,
                                                  @Query("pk_with") String pk_with_wuid,
                                                  @Query("firebase_uid") String firebase_uid);

    @POST("pk_end")
    Call<ObjectModal<StringModal>> pk_end(@Query("id") String pk_id,
                                           @Query("winner") String pk_winner);

    @POST("pk_result")
    Call<ObjectModal<PkResult>> pk_result(@Query("id") String pk_id);

    //vs point during pk
    @POST("gift_during_broad")
    Call<ObjectModal<GiftDuringBroad>> gift_during_broad(@Query("pk1") int broad1, @Query("pk2") int broad2);



    /*if user broadcast has not finished*/
    @POST("is_broadcasting")
    Call<ObjectModal<AddBroadResult>> is_broadcasting(@Query("user_id") String str_uid);

    //start a new broadcast get broadcast id AddBroadcast
    //used by volly
    @POST("add_broadcasting")
    Call<ObjectModal<AddBroadResult>> go_live(@Query("user_id") String str_uid,
                                              @Query("title") String title,
                                              @Query("broadcasting_type") int broadcasting_type,
                                              @Query("temp_profile") String temp_profile
    );

    /*update on end of broadcast tell to end session*/
    @POST("update_broadcasting")
    Call<PostModal> update_broadcasting(
            @Query("user_id") String str_uid,
            @Query("id") String broadid);

    /*---get remote user info---*/
    @POST("get_user_profile")
    Call<ObjectModal<UserShotProfile>> get_user_profile(@Query("firebase_uid") String firebase_uid,
                                                        @Query("uid") String uid);
    @POST("joiner_name")
    Call<ObjectModal<JoinerName>> joiner_name(@Query("firebase_uid") String uisrid);

    @POST("singleonline_user_data")
    Call<ObjectModal<SingleOnlineData>> singleonline_user_data(@Query("uid") String user_id /*460*/,
                                                               @Query("remoteuid") String uidremote /*586*/);

    @POST("send_message")
    Call<StringModal> send_message(@Query("user_1") String uid,
                                    @Query("user_2") String ouid2,
                                    @Query("messanger_id") String ouid,
                                    @Query("message") String msg
    );

    @POST("get_server_time")
    Call<ObjectModal<ServerTime>> get_server_time();

    @POST("gift_sender_list")
    Call<GiftSenderList_Response> gift_sender_list(@Query("broadcast_id") long broad_id);


    @POST("user_postlikes_list")
    Call<PostLikeList_Responce> user_postlikes_list(@Query("post_id") String mPostId);

    @POST("user_postcomment_list")
    Call<PostCommentList_Responce> user_postcomment_list(@Query("post_id") String mPostId);


    @POST("blockuser")
    Call<ObjectModal<BlockModal>> blockuser(@Query("blockedby_uid") String blockedby_uid, /*460*/
                                @Query("blocked_uid") String locked_uid, /*12*/
                                @Query("type") int ty); //1:block, 2:mute, 3:kick, 4:unbloked

    @POST("blocklist")
    Call<ObjectModal<List<BlockList>>> blocklist(@Query("user1") String userid /*460*/);

    @POST("get_zip_gifts")
    Call<ObjectModal<List<ZipGiftItem>>> get_zip_gifts();

    @POST("music_list")
    Call<ObjectModal<List<MusicInfo>>> music_list();

    @POST("level_data")
    Call<ObjectModal<List<LevelData>>> level_data();

    //use token
    @POST("know_golden_coin")
    Call<ObjectModal<GoldenCoinModal>> know_golden_coin();

    //use token
    @POST("join_broad")
    Call<ObjectModal<Object>> join_broad(
            @Query("user_broad_identity") String user_broad_identity,
            @Query("owner_broadcast_id") String owner_broadcast_id,
            @Query("room_id") String room_id,
            @Query("room_owner_fuid") String room_owner_fuid
    );

    @FormUrlEncoded
    @POST("uploadImage")
    Call<ObjectModal<String>> uploadImage(@Field("uploadimage") String str);

    @POST("purchase_coins")
    Call<ObjectModal<PaymentPost>> Coin_purchase(@Query("id") String userid ,/*460*/
                                                 @Query("coin_count") int coin,
                                                 @Query("paid_id") int paymentid ,
                                                 @Query("transaction_id") String orderid,
                                                 @Query("paid_amount") int amount);

    @POST("total_top_fan")
    Call<ObjectModal<List<TopFans>>> top_fans(@Query("user_id") String user_id);

    @POST("thirty_days_top_fan")
    Call<ObjectModal<List<TopFans>>> top_fans1(@Query("user_id") String user_id);
    @POST("family_total")
    Call<ObjectModal<List<TotalFamily>>> total_family();
   @POST("family_monthly")
    Call<ObjectModal<List<TotalFamily>>> total_monthly_family();
   @POST("family_daily")
    Call<ObjectModal<List<TotalFamily>>> total_daily_family();
    @POST("family_detail")
    Call<FamilyDetailsRes> total_details(@Query("family_id") String family_id);

    @POST("add_post_new")
    Call<ObjectModal<String>> add_post_new(@Query("content") String content, @Query("images") String str);

    @POST("calculate_my_salary")
    Call<ObjectModal<SalaryModel>> calculate_my_salary();

    @POST("subscription")
    Call<AristocracySubcriptionResp> subscription(@Query("aristocracy_id") String aristrocracy_id,
                                                  @Query("coin") String coin,
                                                   @Query("duration_month") String month);
    @FormUrlEncoded
    @POST("create_family")
    Call<AristocracySubcriptionResp> create_family(
                                                   @Query("family_name") String family_name,
                                                   @Query("family_level") String family_level,
                                                   @Query("family_about") String family_about ,
                                                   @Field("family_icon") String family_icon,
                                                   @Field("family_background") String family_background);
    @POST("family_join_request")
    Call<AristocracySubcriptionResp> family_join_request(@Query("user_id") String user_id,
                                                 @Query("family_id") String family_id,
                                                 @Query("join") String join);

    @POST("contribution") Call<ObjectModal<List<ContributionModel>>> contribution(@Query("family_id") String family_id);
    @POST("contribution_monthly") Call<ObjectModal<List<ContributionModel>>> contribution_monthly(@Query("family_id") String family_id);
    @POST("contribution_weekly") Call<ObjectModal<List<ContributionModel>>> contribution_weekly(@Query("family_id") String family_id);
    @POST("sent") Call<ObjectModal<List<ContributionModel>>> sent();
    @POST("sent_monthly") Call<ObjectModal<List<ContributionModel>>> sent_monthly();
    @POST("sent_weekly") Call<ObjectModal<List<ContributionModel>>> sent_weekly();
    @POST("received") Call<ObjectModal<List<ContributionModel>>> received();
    @POST("received_monthly") Call<ObjectModal<List<ContributionModel>>> received_monthly();
    @POST("received_weekly") Call<ObjectModal<List<ContributionModel>>> received_weekly();
    @POST("recharged") Call<ObjectModal<List<ContributionModel>>> recharged();
    @POST("recharged_monthly") Call<ObjectModal<List<ContributionModel>>> recharged_monthly();
    @POST("recharged_weekly") Call<ObjectModal<List<ContributionModel>>> recharged_weekly();

}

