package net.leolink.android.tmdb.common.io.network.api.param;

/**
 * @author Leo
 */
// TODO: change to builder pattern if the number of constructors grows
public class DiscoverParams extends BaseParams {
    public DiscoverParams(int page) {
        put("language", "en-US");
        put("include_video", false);
        put("include_adult", false);
        put("sort_by", "primary_release_date.desc");
        put("page", page);
    }

    public DiscoverParams(int page, int year) {
        this(page);
        put("primary_release_year", year);
    }
}
