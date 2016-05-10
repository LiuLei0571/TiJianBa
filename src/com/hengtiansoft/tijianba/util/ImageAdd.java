package com.hengtiansoft.tijianba.util;

import java.util.ArrayList;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;

public class ImageAdd {
  public static ArrayList<Bitmap>  AddList=new ArrayList<Bitmap>();
  public static Bitmap bitmap;
  public static ArrayList<Bitmap>  smallList=new ArrayList<Bitmap>();
  @SuppressLint("UseSparseArrays")
  public static HashMap<Integer,Bitmap> detailMap=new HashMap<Integer,Bitmap>();
  public static HashMap<Integer,Bitmap> smallMap=new HashMap<Integer,Bitmap>();
}
