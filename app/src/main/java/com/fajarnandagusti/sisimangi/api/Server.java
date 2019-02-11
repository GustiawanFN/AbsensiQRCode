package com.fajarnandagusti.sisimangi.api;

import static com.fajarnandagusti.sisimangi.util.Utility.BASE_URL_API;

/**
 * Created by Gustiawan on 2/2/2019.
 */

public class Server {
    public static ApiService getAPIService() {
        return Client.getClient(BASE_URL_API).create(ApiService.class);
    }

}
