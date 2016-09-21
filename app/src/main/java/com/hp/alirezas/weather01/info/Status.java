package com.hp.alirezas.weather01.info;

/**
 * Created by alireza s on 6/24/2016.
 */
public class Status {
    private String date;
    private String temp;
    private String main;


    private String humidity;
    private String presure;


    private String chill;
    private String speed;
    private String direction;

    public String getDate() {
        return date;
    }

    public Status setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTemp() {
        return temp;
    }

    public Status setTemp(String temp) {
        this.temp = temp;
        return this;
    }

    public String getMain() {
        return main;
    }

    public Status setMain(String main) {
        this.main = main;
        return this;
    }

    public String getHumidity() {
        return humidity;
    }

    public Status setHumidity(String humidity) {
        this.humidity = humidity;
        return this;
    }

    public String getPresure() {
        return presure;
    }

    public Status setPresure(String presure) {
        this.presure = presure;
        return this;
    }

    public String getChill() {
        return chill;
    }

    public Status setChill(String chill) {
        this.chill = chill;
        return this;
    }

    public String getSpeed() {
        return speed;
    }

    public Status setSpeed(String speed) {
        this.speed = speed;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public Status setDirection(String direction) {
        this.direction = direction;
        return this;
    }
}
