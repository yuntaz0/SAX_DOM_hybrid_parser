import java.util.ArrayList;
import java.util.List;

public class Cast {
    private String fid;
    private String starId;

    public Cast(String fid, String starId) {
        this.fid = fid;
        this.starId = starId;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getStarId() {
        return starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "fid='" + fid + '\'' +
                ", starId=" + starId +
                '}';
    }
}
