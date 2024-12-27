package edu.uws.ii.project.editors;

import edu.uws.ii.project.domain.Time;

import java.beans.PropertyEditorSupport;

public class CustomTimeEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) {
        String[] parts = text.split("[+=]");

        if (parts.length != 3) {
            setValue(null);
            return;
        }

        try {
            int prepareTime = Integer.parseInt(parts[0]);
            int cookTime = Integer.parseInt(parts[1]);
            int totalTime = Integer.parseInt(parts[2]);

            if (totalTime != prepareTime + cookTime) {
                setValue(null);
                return;
            }

            Time time = new Time();
            time.setPrepareTime(prepareTime);
            time.setCookTime(cookTime);
            time.setTotalTime(totalTime);
            setValue(time);
        } catch (NumberFormatException e) {
            setValue(null);
        }
    }

    @Override
    public String getAsText() {
        Time time = (Time) getValue();
        return time == null ? "" : String.format("%d+%d=%d", time.getPrepareTime(), time.getCookTime(), time.getTotalTime());
    }
}