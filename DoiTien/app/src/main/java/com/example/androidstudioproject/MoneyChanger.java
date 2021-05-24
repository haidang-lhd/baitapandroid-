package com.example.androidstudioproject;

public class MoneyChanger {
    String option_1,option_2;
    Double value,result;

    public MoneyChanger() {
    }

    public MoneyChanger(String option_1, String option_2, Double value, Double result) {
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.value = value;
        this.result = result;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String toString() {

        return value+" "+option_1+" = "+result+" "+option_2;
    }
}
