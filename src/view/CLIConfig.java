package src.view;

public class CLIConfig {
    private String mainColor; 
    private String successColor;
    private String warningColor;
    private String dangerColor;
    private String resetColor;

    public CLIConfig(){
        this.mainColor    = "\u001B[34m";
        this.successColor = "\u001B[32m";
        this.warningColor = "\u001B[33m";
        this.dangerColor  = "\u001B[31m";
        this.resetColor = "\u001B[0m";
    }

    public CLIConfig(String mainColor, String successColor, String warningColor, String dangerColor){
        this.mainColor    = mainColor;
        this.successColor = successColor;
        this.warningColor = warningColor;
        this.dangerColor  = dangerColor;
        this.resetColor   = "\u001B[0m";
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public String getSuccessColor() {
        return successColor;
    }

    public void setSuccessColor(String successColor) {
        this.successColor = successColor;
    }

    public String getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(String warningColor) {
        this.warningColor = warningColor;
    }

    public String getDangerColor() {
        return dangerColor;
    }

    public void setDangerColor(String dangerColor) {
        this.dangerColor = dangerColor;
    }

    public String getResetColor() {
        return resetColor;
    }

    public void setResetColor(String resetColor) {
        this.resetColor = resetColor;
    }

    @Override
    public String toString() {
        return "CLIConfig [dangerColor = " + dangerColor + "■" + resetColor + ", mainColor = " + mainColor + "■" + resetColor
                + ", successColor = " + successColor + "■" + resetColor + ", warningColor = " + warningColor + "■" + resetColor + "]";
    }

    
}