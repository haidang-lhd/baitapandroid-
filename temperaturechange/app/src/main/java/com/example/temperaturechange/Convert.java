package com.example.temperaturechange;

public class Convert {
    private double doF,doC;
    public Convert()
    {

    }
    public double getDoF(){
        return this.doF;
    }
    public double getDoC(){
        return this.doC;
    }
    public void setDoF(double doF){
        this.doF = doF;
    }
    public void setDoC(double doC){
        this.doC = doC;
    }
    public void convertFtoC(){
        this.doC = (this.doF - 32) * 5 / 9;
    }

    public void convertCtoF(){
        this.doF = this.doC * 9 / 5 + 32;
    }
}
