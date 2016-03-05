package org.wordpress.android.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.webkit.URLUtil;

public class HealthCheckUtils {
    public static class HealthCheckException extends Exception {
        public final @StringRes int errorMsgId;
        public final String failedUrl;

        public HealthCheckException(@StringRes int errorMsgId, String failedUrl) {
            this.errorMsgId = errorMsgId;
            this.failedUrl = failedUrl;
        }
    }

    public static String canonicalizeSiteUrl(String siteUrl) throws HealthCheckException {
        if (TextUtils.isEmpty(siteUrl) || TextUtils.isEmpty(siteUrl.trim())) {
            throw new HealthCheckException(org.wordpress.android.R.string.invalid_site_url_message, siteUrl);
        }

        // remove padding whitespace
        String baseURL = siteUrl.trim();

        // Convert IDN names to punycode if necessary
        baseURL = UrlUtils.convertUrlToPunycodeIfNeeded(baseURL);
        // Add http to the beginning of the URL if needed
        baseURL = UrlUtils.addUrlSchemeIfNeeded(baseURL, false);
        if (!URLUtil.isValidUrl(baseURL)) {
            throw new HealthCheckException(org.wordpress.android.R.string.invalid_site_url_message, baseURL);
        }

        return baseURL;
    }
}