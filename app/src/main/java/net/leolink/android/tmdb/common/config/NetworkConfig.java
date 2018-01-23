package net.leolink.android.tmdb.common.config;

/**
 * Configurations for all network components
 *
 * @author Leo
 */
public class NetworkConfig {
    public static final String BASE_API_URL = "https://api.themoviedb.org/3/";
    public static final String RETROFIT_CACHE_DIR_NAME = "retrofit_cache";
    public static final long RETROFIT_CACHE_SIZE = 20 * 1024 * 1024; // 20mb

    // TODO: use this API to get base image url instead of hardcoding
    // TODO: https://developers.themoviedb.org/3/configuration/get-api-configuration
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300/%s";
}
