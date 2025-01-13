package md.hajji.Serdes;

import md.hajji.models.SpeedAlert;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class SpeedAlertSerde implements Serde<SpeedAlert> {
    @Override
    public Serializer<SpeedAlert> serializer() {
        return null;
    }

    @Override
    public Deserializer<SpeedAlert> deserializer() {
        return null;
    }
}


class SpeedAlertSerializer implements Serializer<SpeedAlert> {
    @Override
    public byte[] serialize(String s, SpeedAlert speedAlert) {
        return SerdesUtils.write(speedAlert);
    }
}


class SpeedAlertDeserializer implements Deserializer<SpeedAlert> {

    @Override
    public SpeedAlert deserialize(String s, byte[] bytes) {
        return SerdesUtils.read(bytes, SpeedAlert.class);
    }
}
