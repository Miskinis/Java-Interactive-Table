package com.company;

public class Worker
{
    public String name;
    public String lastName;
    public String workPlace;
    public String job;
    public float earnings;

    public Worker()
    {
        this.name = "";
        this.lastName = "";
        this.workPlace = "";
        this.job = "";
        this.earnings = 0f;
    }

    public Worker(String name, String lastName, String workPlace, String job, float earnings)
    {
        this.name = name;
        this.lastName = lastName;
        this.workPlace = workPlace;
        this.job = job;
        this.earnings = earnings;
    }
}
