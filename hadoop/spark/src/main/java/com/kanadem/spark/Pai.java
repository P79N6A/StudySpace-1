package com.kanadem.spark;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Kanadem 2019/4/4
 */

public class Pai {

  public static void main(String[] args) throws Exception{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("How many darts shoud I toss at the board?\n");
    String s = br.readLine();
    int numberOfDarts = Integer.parseInt(s.trim());
    double radius = 1.0;
    Dartboard d = new Dartboard(radius);

    for(int i=1; i<=numberOfDarts; i++){
      Toss t = Toss.getRandom(radius);
      d.strike(t);
    }

    double fractionIn = d.getFractionIn();
    double pi = 4.0 * fractionIn;
    System.out.println("Pi is approximately "+pi);
  }

}

class Dartboard{

  private double radius;
  private int insideCircle, outsideCircle;

  public Dartboard(double radius){
    this.radius = radius;
    insideCircle = 0;
    outsideCircle = 0;
  }

  public void strike(Toss toss){
    double x = toss.getX();
    double y = toss.getY();

    if(Math.sqrt(x*x + y*y) < radius)
      insideCircle++;
    else
      outsideCircle++;
  }

  public double getFractionIn() {
    double total = (double) (insideCircle + outsideCircle);
    return (double) insideCircle/total;
  }
}

class Toss{

  private double x,y;

  public Toss(double x, double y){
    this.x = x;
    this.y = y;
  }
  public double getX(){return x;}
  public double getY(){return y;}

  public static Toss getRandom(double radius){

    double x,y;
    double size = Math.random();
    double sign = Math.random();
    size = size * radius;

    if(sign > 0.5)
      x = size;
    else
      x = -size;

    size = Math.random();
    sign = Math.random();
    size = size * radius;

    if(sign > 0.5)
      y = size;
    else
      y = -size;

    return new Toss(x,y);
  }

}
