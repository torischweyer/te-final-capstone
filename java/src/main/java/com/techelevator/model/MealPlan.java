package com.techelevator.model;

import java.time.LocalDate;
import java.util.Date;

public class MealPlan {

    private int mealPlanId;
    private LocalDate date;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
    private Meal snack;
    private Meal fun;

    public MealPlan () {
        breakfast = new Meal("Breakfast");
        lunch = new Meal("Lunch");
        dinner = new Meal("Dinner");
        snack = new Meal("Snack");
        fun = new Meal("Fun");

    }

    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }

    public Meal getSnack() {
        return snack;
    }

    public void setSnack(Meal snack) {
        this.snack = snack;
    }

    public Meal getFun() {
        return fun;
    }

    public void setFun(Meal fun) {
        this.fun = fun;
    }
}
