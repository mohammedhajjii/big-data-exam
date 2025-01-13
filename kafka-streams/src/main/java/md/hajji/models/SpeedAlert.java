package md.hajji.models;

import java.time.LocalDateTime;

public record SpeedAlert(
        Long VID,
        int speed,
        double latitude,
        double longitude,
        String raison,
        LocalDateTime timestamp
) {


    @Override
    public String toString() {
        return VID + "|" + speed + "|" + latitude + "|" + longitude + "|" + raison + "|" + timestamp;
    }
}
