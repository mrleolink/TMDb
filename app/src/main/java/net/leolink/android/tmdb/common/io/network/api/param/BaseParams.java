package net.leolink.android.tmdb.common.io.network.api.param;

import net.leolink.android.tmdb.common.config.Config;

import java.util.HashMap;

/**
 * @author Leo
 */
class BaseParams extends HashMap<String, Object> {
    BaseParams() {
        put("api_key", Config.API_KEY);
    }
}
