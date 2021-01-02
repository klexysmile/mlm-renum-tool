package mlm.tool.mungwin.com.mlmtool.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class CustomerAccountBean {

    @GeneratedValue
    @Id
    long id;
    private double totalBalance;
    private double availableBalance;
    private int points;
    private int networkSize;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNetworkSize() {
        return networkSize;
    }

    public void setNetworkSize(int networkSize) {
        this.networkSize = networkSize;
    }
}
