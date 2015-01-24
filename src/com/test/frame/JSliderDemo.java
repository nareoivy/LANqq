package com.test.frame;

import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;

public class JSliderDemo
{
 public static void main (String[] args)
 {
  JSlider empty = new JSlider ();
  
  JSlider age = new JSlider (JSlider.VERTICAL, 0, 150, 20);//设置方向,最小值，最大值，初始值
  
  JSlider append = new JSlider ();
  append.setOrientation (JSlider.HORIZONTAL);//设置方向
  append.setMinimum (0);//设置最小值
  append.setMaximum (100);//设置最大值
  append.setMajorTickSpacing (20);//设置主标号间隔
  append.setMinorTickSpacing (5);//设置辅标号间隔
  append.setPaintLabels (true);//Default:false显示标签
  append.setPaintTicks (true);//Default:false显示标号
  append.setPaintTrack (true);//Determines whether the track is painted on the slider
  append.setValue (0);//设置初始值
  
  
  JPanel panel = new JPanel (new GridLayout (0,1));
  panel.setPreferredSize (new Dimension (600,400));
//  panel.add (empty);
  panel.add (age);
  panel.add (append);
  
  
  JFrame frame = new JFrame ("JProgressBarDemo");
  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
  frame.setContentPane (panel);
  frame.pack();
  frame.setVisible(true);
 }
}  
