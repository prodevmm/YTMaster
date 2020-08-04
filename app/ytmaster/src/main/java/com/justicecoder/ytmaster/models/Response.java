package com.justicecoder.ytmaster.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("errorMessage")
@Expose
private String errorMessage;
@SerializedName("details")
@Expose
private VideoDetails details;
@SerializedName("streamList")
@Expose
private List<Stream> streamList = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getErrorMessage() {
return errorMessage;
}

public void setErrorMessage(String errorMessage) {
this.errorMessage = errorMessage;
}

public VideoDetails getVideoDetails() {
return details;
}

public void setVideoDetails(VideoDetails details) {
this.details = details;
}

public List<Stream> getStreamList() {
return streamList;
}

public void setStreamList(List<Stream> streamList) {
this.streamList = streamList;
}

}