package com.example.smartinteligentproject.Model;

public class User {

    String T1FireSensor;
    String T2FireSensor;
    String Id;
    String Rain;
    String T1UltrasonicSensor;
    String T2UltrasonicSensor;
    String img;

    public User(String t1FireSensor, String t2FireSensor, String id, String LDR, String rain, String t1UltrasonicSensor, String t2UltrasonicSensor, String img) {
        T1FireSensor = t1FireSensor;
        T2FireSensor = t2FireSensor;
        Id = id;
        Rain = rain;
        T1UltrasonicSensor = t1UltrasonicSensor;
        T2UltrasonicSensor = t2UltrasonicSensor;
        this.img = img;
    }


    public User() {
    }

    public String getT1FireSensor() {
        return T1FireSensor;
    }

    public void setT1FireSensor(String t1FireSensor) {
        T1FireSensor = t1FireSensor;
    }

    public String getT2FireSensor() {
        return T2FireSensor;
    }

    public void setT2FireSensor(String t2FireSensor) {
        T2FireSensor = t2FireSensor;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public String getRain() {
        return Rain;
    }

    public void setRain(String rain) {
        Rain = rain;
    }

    public String getT1UltrasonicSensor() {
        return T1UltrasonicSensor;
    }

    public void setT1UltrasonicSensor(String t1UltrasonicSensor) {
        T1UltrasonicSensor = t1UltrasonicSensor;
    }

    public String getT2UltrasonicSensor() {
        return T2UltrasonicSensor;
    }

    public void setT2UltrasonicSensor(String t2UltrasonicSensor) {
        T2UltrasonicSensor = t2UltrasonicSensor;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
