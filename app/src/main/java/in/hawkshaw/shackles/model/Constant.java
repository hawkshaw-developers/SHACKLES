package in.hawkshaw.shackles.model;

public class Constant {
    public static final String CLIENT_ID = "edb468e9c7164ebf9570e24f70f649ba";
    public static final String CLIENT_SECRET = "5a0ec06f7de948f8ae47707c33ffef69";
    public static final String REDIRECT_URI = "http://shackles.com";
    public static final String INSTA_LOGIN_URL = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=public_content";
    public static final String INSTA_BASIC = "https://api.instagram.com/v1/users/self/?access_token=";
    public static final String ACCESS_TOKEN_URL ="https://api.instagram.com/oauth/access_token?";
    public static final String PARAM_CLIENT_ID = "client_id="+CLIENT_ID;
    public static final String PARAM_CLIENT_SECRET = "client_secret="+CLIENT_SECRET;
    public static final String PARAM_AUTH_CODE = "grant_type=authorization_code";
    public static final String PARAM_REDIRECT_URI = "redirect_uri="+REDIRECT_URI;
    public static final String PARAM_CODE = "code";
}
