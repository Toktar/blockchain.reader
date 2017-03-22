package ru.blockboxchain.reader.models.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toktar.
 */
public class CarAccidentEvent extends CarEvent {


    public List<String> getBreakageCodeList() {
        return breakageCodeList;
    }

    public void setBreakageCodeList(List<String> breakageCodeList) {
        this.breakageCodeList = breakageCodeList;
    }

    private List<String> breakageCodeList = new ArrayList<>();
}
