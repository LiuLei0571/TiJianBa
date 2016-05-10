
package com.hengtiansoft.tijianba.model;


public class RedPocket {
    private int num;
    private int money;
    private String time;

    public RedPocket() {
        super();
    }

    public RedPocket(int num, int money, String time) {
        super();
        this.num = num;
        this.money = money;
        this.time = time;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
