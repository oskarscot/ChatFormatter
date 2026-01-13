package scot.oskar.chatformatter;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.Message;
import java.util.List;

public class MessageFormatter {
  private String name;
  private Message message;

  public MessageFormatter() {}

  public MessageFormatter(String name, Message message) {
    this.name = name;
    this.message = message;
  }

  public static final BuilderCodec<MessageFormatter> CODEC =
      BuilderCodec.builder(MessageFormatter.class, MessageFormatter::new)
          .append(
              new KeyedCodec<>("Name", Codec.STRING),
              (f, value) -> f.name = value,
              f -> f.name)
          .add()
          .append(
              new KeyedCodec<>("Message", Message.CODEC),
              (f, value) -> f.message = value,
              f -> f.message)
          .add()
          .build();

  public static final ListCodec<MessageFormatter> LIST_CODEC = new ListCodec<>(CODEC);

  public String getName() { return name; }
  public Message getMessage() { return message; }
}