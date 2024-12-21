package com.deificindia.indifun.Utility;

public class URLs {

    public static final String BaseUrl0 = "http://deificindia.com/indifun/panel/";

    public static final String BaseUrl = BaseUrl0+ "api/";
    public static final String LogoImageBaseUrl = BaseUrl0+"assets/images/logo/";
    public static final String UserImageBaseUrl = BaseUrl0+"assets/images/user/";
    public static final String UserImageBaseUrlTemp = BaseUrl0+"assets/images/user/tempprofile/";
    public static final String CountryFlagImages = BaseUrl0+"assets/images/country/";
    public static final String StickerImageBaseUrl = BaseUrl0+"assets/images/sticker/";
    public static final String AristocracyImageBaseUrl=BaseUrl0+"assets/images/aristocracy/";
    public static final String GifgBaseUrl=BaseUrl0+"assets/images/gift/";
    public static final String PRIVILEGES=BaseUrl0+"assets/images/privileges/";
    public static final String UserGroupBaseUrl = BaseUrl0+"assets/images/group/";
    public static final String UserPostImagesBaseUrl = BaseUrl0+"assets/images/post/";
    public static final String UserLEVELANIMATIONUrl = BaseUrl0+"assets/images/animation/";
    public static final String MUSICLIST = BaseUrl0+"assets/musics/";
    public static final String FamilYUserIcon=BaseUrl0+"assets/images/family/";

    public static String avtarBaseUrl(long typ){
        return typ==0?URLs.UserImageBaseUrl:URLs.UserImageBaseUrlTemp;
    }

    /*https://deificindia.com/indifun/assets/images/gems/11.jpeg
    https://deificindia.com/indifun/assets/images/gift/11.jpeg
    https://deificindia.com/indifun/assets/images/logo/11.jpeg
    https://deificindia.com/indifun/assets/images/post/11.jpeg
    https://deificindia.com/indifun/assets/images/slider/11.jpeg
    https://deificindia.com/indifun/assets/images/sticker/11.jpeg
    https://deificindia.com/indifun/assets/images/user/11.jpeg*/


    public static final String URL_lOGIN = BaseUrl + "login";
    public static final String VERIFY_OTP = BaseUrl + "verify_otp";
    public static final String URL_RESENDOTP = BaseUrl + "resend_otp";
    public static final String URL_SIGNUP = BaseUrl + "signup";

    public static final String USERDASHBOARD = BaseUrl+"user_dashboard";
    public static final String URL_UPDATEROFILE = BaseUrl+"user_profile_update";
    public static final String UPDATEUSEPROFILEGALLERY =BaseUrl+"update_profile_photos";

    public static final String GETUSEPROFILEGALLERY = BaseUrl+"get_profile_photos" ;
    public static final String ADDUSEPROFILEGALLERY = BaseUrl+"add_profile_photos";
    public static final String GETUSERMOMENTS =BaseUrl+"get_post" ;
    public static final String URL_GETROFILE = BaseUrl+"user_profile";
    public static final String GETINTERSTUSER =BaseUrl+"user_interests" ;
    public static final String MOMENT_IMAGE =BaseUrl+"moment_image" ;
    public static final String GETLANGUAGELIST =BaseUrl+"language_list" ;
    public static final String ONLINECONTACT = BaseUrl+"get_general_settings";
    public static final String GETTERMS = BaseUrl+"get_terms";
    public static final String GETABOUT = BaseUrl+"get_about" ;
    public static final String GETPRIVACY = BaseUrl+"get_privacy_policy" ;
    public static final String URL_SEARCHUSER = BaseUrl+"user_search";
    public static final String GETSILVERCOINS = BaseUrl+"silvercoin_list";
    public static final String GETGOLDENCOINS = BaseUrl+"goldencoin_list";
    public static final String GETSHARELIVEMEDAL = BaseUrl+"get_medal_share_live";
    public static final String GETRCHARGEMEDAL = BaseUrl+"get_medal_recharge";
    public static final String GETSILVERMEDAL = BaseUrl+"get_medal_silver_coin";
    public static final String GETSTICKERS =BaseUrl+"get_sticker" ;
    public static final String DICOVERGROUPS = BaseUrl+"group_category_list";
    public static final String GETGROUPLISTBYCAT =BaseUrl+"get_group_bycatid" ;
    public static final String URL_CREATEGROUP = BaseUrl+"user_groups";
    public static final String GETBROADCASTWATCH = BaseUrl+"broadast_by_userid";
    public static final String ADDPOST = BaseUrl+"add_post";
    public static final String URL_PURCHASECOINS =BaseUrl+"purchase_coins" ;
    public static final String GETCHECKINMEDAL =BaseUrl+"get_medal_check_in" ;
    public static final String DELETEBROADCASTWATCH = BaseUrl+"del_broadcast" ;
    public static final String URL_JOINGROUP =BaseUrl+"join_groups" ;
    public static final String GETUSERLANGUAGELIST =BaseUrl+"user_language" ;
    public static final String URL_UPDATELANG =BaseUrl+"user_language_update" ;
    public static final String URL_UNCHECKLANG =BaseUrl+"user_language_del" ;
    public static final String INTERESTCATLIST =BaseUrl+"intrest_category_list" ;
    public static final String GETINTERSTSUBCATLIST = BaseUrl+"intrest_subategory_list";
    public static final String INTERESTARISTOCRACITYCENTRLIST = BaseUrl+"aristocracy_center";
    public static final String URL_BUYARISTOCARCYCENTER = BaseUrl+"aristocracy_center_plan";
    public static final String URL_UPDATEINTEREST =BaseUrl+"user_intrest_update" ;
    public static final String URL_UNCHECKINTEREST = BaseUrl+"user_intrest_del";
    public static final String ADDBROAD =BaseUrl+"add_broadcasting";
    public static final String ADD_Payment_request =BaseUrl+"get_pay_request";
}
