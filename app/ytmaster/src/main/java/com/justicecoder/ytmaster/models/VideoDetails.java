package com.justicecoder.ytmaster.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetails {

@SerializedName("title")
@Expose
private String title;
@SerializedName("description")
@Expose
private String description;
@SerializedName("viewCount")
@Expose
private String viewCount;
@SerializedName("length")
@Expose
private String length;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getViewCount() {
return viewCount;
}

public void setViewCount(String viewCount) {
this.viewCount = viewCount;
}

public String getLength() {
return length;
}

public void setLength(String length) {
this.length = length;
}

}