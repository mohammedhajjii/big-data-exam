package md.hajji.models;

import java.time.LocalDateTime;

public record Speed(
        Long VID,
        int speed,
        double latitude,
        double longitude,
        double distanceToNextObstacle,
        LocalDateTime timestamp

) {


    public SpeedAlert alert(){
        return  new SpeedAlert(
                VID,
                speed,
                latitude,
                longitude,
                "Overspeeding",
                timestamp
        );
    }


    public ObstacleAlert obstacleAlert(){
        return  new ObstacleAlert(
                VID,
                distanceToNextObstacle,
                "Obstacle too close!"
        );
    }
}
