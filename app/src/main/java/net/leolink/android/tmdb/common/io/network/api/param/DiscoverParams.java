package net.leolink.android.tmdb.common.io.network.api.param;

/**
 * @author Leo
 */
public class DiscoverParams extends BaseParams {
    public DiscoverParams(int page) {
        put("language", "en-US");
        put("include_video", false);
        put("include_adult", false);
        put("sort_by", "primary_release_date.desc");
        put("page", page);
    }
}
