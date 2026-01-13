package scot.oskar.chatformatter;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.ExtraInfo;
import com.hypixel.hytale.codec.schema.SchemaContext;
import com.hypixel.hytale.codec.schema.config.Schema;
import com.hypixel.hytale.codec.util.RawJsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.bson.BsonArray;
import org.bson.BsonValue;

public class ListCodec<T> implements Codec<List<T>> {

  private final Codec<T> elementCodec;

  public ListCodec(Codec<T> elementCodec) {
    this.elementCodec = elementCodec;
  }

  @Nonnull
  @Override
  public List<T> decode(BsonValue bsonValue, ExtraInfo extraInfo) {
    return bsonValue.asArray().stream()
        .map(element -> elementCodec.decode(element, extraInfo))
        .collect(Collectors.toList());
  }

  @Override
  public BsonValue encode(List<T> list, ExtraInfo extraInfo) {
    BsonArray array = new BsonArray();
    list.forEach(item -> array.add(elementCodec.encode(item, extraInfo)));
    return array;
  }

  @Nonnull
  @Override
  public List<T> decodeJson(@Nonnull RawJsonReader reader, ExtraInfo extraInfo) throws IOException {
    List<T> result = new ArrayList<>();
    reader.expect('[');
    reader.consumeWhiteSpace();

    if (!reader.tryConsume(']')) {
      while (true) {
        result.add(elementCodec.decodeJson(reader, extraInfo));
        reader.consumeWhiteSpace();

        if (reader.tryConsumeOrExpect(']', ',')) {
          break;
        }
        reader.consumeWhiteSpace();
      }
    }

    return result;
  }

  @Nonnull
  @Override
  public Schema toSchema(@Nonnull SchemaContext context) {
    return elementCodec.toSchema(context);
  }
}