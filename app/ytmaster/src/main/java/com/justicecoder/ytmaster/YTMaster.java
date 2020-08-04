package com.justicecoder.ytmaster;

import androidx.annotation.NonNull;

import com.justicecoder.ytmaster.models.Response;
import com.justicecoder.ytmaster.models.Stream;
import com.justicecoder.ytmaster.models.VideoDetails;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class YTMaster {

    private String url;
    private String path, domainUrl;

    private static final Pattern patYouTubePageLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)( |\\z|&)");
    private static final Pattern patYouTubeShortLink = Pattern.compile("(http|https)://(www\\.|)youtu.be/(.+?)( |\\z|&)");

    private static final String NULL_API = "set API URL first before you enqueue.";
    private static final String NULL_URL = "set youtube URL before you enqueue.";
    private static final String INVALID_URL = "input URL is invalid or not a youtube URL.";
    private static final String NULL_VIDEO_ID = "cannot get video ID from url.";
    private static final String NULL_RESPONSE = "server return null response";

    private YTMaster() {
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return url.contains("www.youtube.com") || url.contains("youtu.be");
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static YTMaster newInstance(String apiUrl, String phpFile) {
        YTMaster ytMaster = new YTMaster();
        ytMaster.domainUrl = apiUrl;
        ytMaster.path = phpFile;
        return ytMaster;
    }

    public YTMaster setUrl(String url) {
        this.url = url;
        return this;
    }

    public void enqueue(Callback callback) {
        if (domainUrl == null) callback.onFailure(NULL_API);
        if (url == null) callback.onFailure(NULL_URL);
        else if (!isValidUrl(url)) callback.onFailure(INVALID_URL);
        else {
            String videoId = getVideoId(url);
            if (videoId == null) callback.onFailure(NULL_VIDEO_ID);
            else {
                ApiRequest request = ApiUtils.getRequest(domainUrl);
                request.getResponse(path, videoId).enqueue(new retrofit2.Callback<Response>() {
                    @Override
                    public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                        Response response1 =response.body();
                        if (response1 == null) callback.onFailure(NULL_RESPONSE + "\n" + response.raw().message());
                        else handleResponse (response1, callback);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }

    }

    private void handleResponse(Response response1, Callback callback) {
        if (response1.getStatus()){
            callback.onSuccess(response1.getStreamList(), response1.getVideoDetails());
        } else callback.onFailure(response1.getErrorMessage());

    }

    public interface Callback {
        void onSuccess(List<Stream> streamList, VideoDetails videoDetails);

        void onFailure(String errorMessage);

    }

    private String getVideoId(String url) {
        String videoID = null;
        Matcher mat = patYouTubePageLink.matcher(url);
        if (mat.find()) {
            videoID = mat.group(3);
        } else {
            mat = patYouTubeShortLink.matcher(url);
            if (mat.find()) {
                videoID = mat.group(3);
            } else if (url.matches("\\p{Graph}+?")) {
                videoID = url;
            }
        }
        return videoID;
    }

    public static List<String> getQualityList(List<Stream> streamList){
        ArrayList<String> qualityList = new ArrayList<>();
        for (Stream stream : streamList){
            qualityList.add(stream.getQuality());
        }
        return qualityList;
    }
}
