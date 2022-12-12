package com.example.aklny_v20;

import java.util.ArrayList;

public class MenuItemChoiceModel {
    private String choice_name;
    private ArrayList<MenuItemChoiceValueModel> choice_values;

    public enum choiceType{SINGLE_VALUE, MULTI_CHOICE}
    private choiceType type;

    public MenuItemChoiceModel(String choice_name, ArrayList<MenuItemChoiceValueModel> choice_values, choiceType type) {
        this.choice_name = choice_name;
        this.choice_values = choice_values;
        this.type = type;
    }

    public String getChoice_name() {
        return choice_name;
    }

    public void setChoice_name(String choice_name) {
        this.choice_name = choice_name;
    }

    public ArrayList<MenuItemChoiceValueModel> getChoice_values() {
        return choice_values;
    }

    public void setChoice_values(ArrayList<MenuItemChoiceValueModel> choice_values) {
        this.choice_values = choice_values;
    }

    public choiceType getType() {
        return type;
    }

    public void setType(choiceType type) {
        this.type = type;
    }
}
