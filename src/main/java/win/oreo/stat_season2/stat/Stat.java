package win.oreo.stat_season2.stat;

public class Stat {
    private double ATK;
    private double CRIT;
    private double SPD;
    private double HP;

    public Stat(double ATK, double CRIT, double SPD, double HP) {
        this.ATK = ATK;
        this.CRIT = CRIT;
        this.SPD = SPD;
        this.HP = HP;
    }

    public double getATK() {
        return ATK;
    }

    public void setATK(double ATK) {
        this.ATK = ATK;
    }

    public double getCRIT() {
        return CRIT;
    }

    public void setCRIT(double CRIT) {
        this.CRIT = CRIT;
    }

    public double getSPD() {
        return SPD;
    }

    public void setSPD(double SPD) {
        this.SPD = SPD;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }
}
