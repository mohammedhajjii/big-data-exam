package md.hajji;

import md.hajji.models.Speed;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ObstacleAlertStreams {


    static final String APPLICATION_ID = "SPEED_ALERT_STREAMS_APP";
    static final String INPUT_TOPIC = "vehicle-data";
    static final String OUTPUT_TOPIC = "obstacle-alert";
    static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    static Speed mapToSpeed(String line){
        String[] fields = line.split("\\|");
        return  new Speed(
                Long.parseLong(fields[0]),
                Integer.parseInt(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3]),
                Double.parseDouble(fields[4]),
                LocalDateTime.parse(fields[5], FORMATTER)
        );
    }

    public static void main(String[] args) {


        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(ObstacleAlertStreams::mapToSpeed)
                .peek((k, speed) -> System.out.println(speed))
                .filter((k, speed) -> speed.distanceToNextObstacle() < 10)
                .peek((k, speed) -> System.out.println(speed))
                .mapValues(speed -> speed.obstacleAlert().toString())
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));


        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));



    }
}
