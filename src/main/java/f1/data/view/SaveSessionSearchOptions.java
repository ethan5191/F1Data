package f1.data.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SaveSessionSearchOptions {

    private String lastName = null;
    private String setupId = null;
    private final BooleanProperty superSoft =  new SimpleBooleanProperty(false);
    private final BooleanProperty soft =  new SimpleBooleanProperty(false);
    private final BooleanProperty medium =  new SimpleBooleanProperty(false);
    private final BooleanProperty hard =  new SimpleBooleanProperty(false);
    private final BooleanProperty inter =  new SimpleBooleanProperty(false);
    private final BooleanProperty wet =  new SimpleBooleanProperty(false);

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSetupId() {
        return setupId;
    }

    public void setSetupId(String setupId) {
        this.setupId = setupId;
    }

    public boolean isSuperSoft() {
        return superSoft.get();
    }

    public BooleanProperty superSoftProperty() {
        return superSoft;
    }

    public void setSuperSoft(boolean superSoft) {
        this.superSoft.set(superSoft);
    }

    public boolean isSoft() {
        return soft.get();
    }

    public BooleanProperty softProperty() {
        return soft;
    }

    public void setSoft(boolean soft) {
        this.soft.set(soft);
    }

    public boolean isMedium() {
        return medium.get();
    }

    public BooleanProperty mediumProperty() {
        return medium;
    }

    public void setMedium(boolean medium) {
        this.medium.set(medium);
    }

    public boolean isHard() {
        return hard.get();
    }

    public BooleanProperty hardProperty() {
        return hard;
    }

    public void setHard(boolean hard) {
        this.hard.set(hard);
    }

    public boolean isInter() {
        return inter.get();
    }

    public BooleanProperty interProperty() {
        return inter;
    }

    public void setInter(boolean inter) {
        this.inter.set(inter);
    }

    public boolean isWet() {
        return wet.get();
    }

    public BooleanProperty wetProperty() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet.set(wet);
    }

    public boolean noSearchOptions() {
        return this.setupId == null && !isSuperSoft() && !isSoft() && !isMedium() && !isHard() && !isInter() && !isWet();
    }

    public boolean hasTireSearch() {
        return isSuperSoft() || isSoft() || isMedium() || isHard() || isInter() || isWet();
    }
}
