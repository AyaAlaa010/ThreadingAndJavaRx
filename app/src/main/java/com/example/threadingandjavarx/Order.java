package com.example.threadingandjavarx;

public class Order {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    String name;

    public Order(String name, boolean isFinished) {
        this.name = name;
        this.isFinished = isFinished;
    }

    boolean isFinished;
}
