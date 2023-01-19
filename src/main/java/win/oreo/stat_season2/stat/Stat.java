package win.oreo.stat_season2.stat;

public class Stat {
    private double STR;
    private double INT;
    private double PHY;

    public Stat(double STR, double INT, double PHY) {
        this.STR = STR;
        this.INT = INT;
        this.PHY = PHY;
    }

    public double getSTR() {
        return STR;
    }

    public void setSTR(double STR) {
        this.STR = STR;
    }

    public double getINT() {
        return INT;
    }

    public void setINT(double INT) {
        this.INT = INT;
    }

    public double getPHY() {
        return PHY;
    }

    public void setPHY(double PHY) {
        this.PHY = PHY;
    }
}
