package forpdateam.ru.forpda.ui.fragments;

/**
 * Created by radiationx on 20.03.17.
 */

public class TabConfiguration {
    private boolean isAlone = false;
    private boolean isMenu = false;
    private boolean fitSystemWindow = false;
    private String defaultTitle = "";

    public boolean isAlone() {
        return isAlone;
    }

    public void setAlone(boolean alone) {
        isAlone = alone;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public boolean isFitSystemWindow() {
        return fitSystemWindow;
    }

    public void setFitSystemWindow(boolean fitSystemWindow) {
        this.fitSystemWindow = fitSystemWindow;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    @Override
    public String toString() {
        return "TabConfiguration{" + isAlone() + ", " + isMenu() + ", " + getDefaultTitle() + "}";
    }
}
