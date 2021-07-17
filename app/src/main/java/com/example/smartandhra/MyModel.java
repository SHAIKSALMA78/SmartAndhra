package com.example.smartandhra;

public class MyModel{
    String img,name,problem,textview,number;

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
    public String getProblem() {
        return problem;
    }
    public String getTextview() {return textview;}
    public String getNumber() {
        return number;
    }
    public MyModel() {
    }

    public MyModel(String img, String name, String problem,String number,String textview) {
        this.img = img;
        this.name = name;
        this.problem = problem;
        this.number = number;
        this.textview = textview;
    }
}
