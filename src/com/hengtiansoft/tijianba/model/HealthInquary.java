package com.hengtiansoft.tijianba.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class HealthInquary implements Serializable {
  private int id;
  private int imgResource;
  private String title;
  private String detial;

  public HealthInquary(int id, int imgResource, String title, String detial) {
    super();
    this.id = id;
    this.imgResource = imgResource;
    this.title = title;
    this.detial = detial;
  }


  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getImgResource() {
    return this.imgResource;
  }

  public void setImgResource(int imgResource) {
    this.imgResource = imgResource;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetial() {
    return this.detial;
  }

  public void setDetial(String detial) {
    this.detial = detial;
  }

  public HealthInquary() {
    super();
  }

}
