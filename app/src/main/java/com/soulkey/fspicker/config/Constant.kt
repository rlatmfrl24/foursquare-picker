package com.soulkey.fspicker.config

object Constant {
    const val CONNECT_TIMEOUT = 180L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 60L
    const val PERMISSION_REQUEST_CODE = 200

    const val BASE_URL = "https://api.foursquare.com/"
    private const val FOURSQUARE_CLIENT_ID = "BKHG05THZOQ0Y44CHALB2UQ3SELZV4A114LY4TU0RM3NXCQ1"
    private const val FOURSQUARE_CLIENT_SECRET = "X2XLA04O2DLZXNTTQUFEY0NBBMLIQONB2SMMR2JKSR5LXR1E"
    private const val FOURSQUARE_API_VERSION = "20190425"
    const val FOURSQUARE_BASIC_PARAM =
        "client_id=$FOURSQUARE_CLIENT_ID&client_secret=$FOURSQUARE_CLIENT_SECRET&v=$FOURSQUARE_API_VERSION"
}