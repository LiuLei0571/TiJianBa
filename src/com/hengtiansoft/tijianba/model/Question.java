package com.hengtiansoft.tijianba.model;

import java.util.ArrayList;

public class Question {

  private String title;
  private ArrayList<String> option;
  private String answer;

  public String getAnswer() {
    return this.answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<String> getOption() {
    return this.option;
  }

  public void setOption(ArrayList<String> option) {
    this.option = option;
  }

}
