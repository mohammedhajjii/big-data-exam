package md.hajji.models;

import org.apache.kafka.common.protocol.types.Field;

public record ObstacleAlert(
        Long VID,
        double distanceToNextObstacle,
        String raison
) {

    @Override
    public String toString() {
        return VID + "|" + distanceToNextObstacle + "|" + raison;
    }
}
