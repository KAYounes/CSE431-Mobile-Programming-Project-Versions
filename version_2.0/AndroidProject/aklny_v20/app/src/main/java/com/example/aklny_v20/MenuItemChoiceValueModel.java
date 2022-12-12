package com.example.aklny_v20;

public class MenuItemChoiceValueModel {

    private String choice_value;
    private Double choice_fees;


    public MenuItemChoiceValueModel(String choice_value, Double choice_fees) {
        this.choice_value = choice_value;
        this.choice_fees = choice_fees;
    }

    public String getChoice_value() {
        return choice_value;
    }

    public void setChoice_value(String choice_value) {
        this.choice_value = choice_value;
    }

    public Double getChoice_fees() {
        return choice_fees;
    }

    public void setChoice_fees(Double choice_fees) {
        this.choice_fees = choice_fees;
    }
}
