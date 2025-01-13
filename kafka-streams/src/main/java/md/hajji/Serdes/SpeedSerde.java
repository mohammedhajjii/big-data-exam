package md.hajji.Serdes;

import md.hajji.models.Speed;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class SpeedSerde implements Serde<Speed> {
    @Override
    public Serializer<Speed> serializer() {
        return null;
    }

    @Override
    public Deserializer<Speed> deserializer() {
        return null;
    }
}


class SpeedSerializer implements Serializer<Speed> {

    @Override
    public byte[] serialize(String s, Speed speed) {
        return SerdesUtils.write(speed);
    }
}

class SpeedDeserializer implements Deserializer<Speed> {

    @Override
    public Speed deserialize(String s, byte[] bytes) {
        return SerdesUtils.read(bytes, Speed.class);
    }
}
