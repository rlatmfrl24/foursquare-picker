package com.soulkey.fspicker.config

object Constant {
    const val CONNECT_TIMEOUT = 180L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 60L
    const val PERMISSION_REQUEST_CODE = 200

    const val BASE_URL = "https://api.foursquare.com/"
    private const val FOURSQUARE_CLIENT_ID = "O4CMJ1FP3E5WJQFHNJ3DB3M4RM5ROO2LMAXRGHOE1QMDECMI"
    private const val FOURSQUARE_CLIENT_SECRET = "VUJJA1HWH3TKJW0IKMJZYDBRY3241RYDY3FMLJSXWEGHPMJE"
    private const val FOURSQUARE_API_VERSION = "20190425"
    const val FOURSQUARE_BASIC_PARAM =
        "client_id=$FOURSQUARE_CLIENT_ID&client_secret=$FOURSQUARE_CLIENT_SECRET&v=$FOURSQUARE_API_VERSION"
}