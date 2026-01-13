package scot.oskar.chatformatter.config;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.Message;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import scot.oskar.chatformatter.MessageFormatter;

public class MessagesConfig {

  public static BuilderCodec<MessagesConfig> CODEC =
      BuilderCodec.builder(MessagesConfig.class, MessagesConfig::new)
          .append(
              new KeyedCodec<>("BlockedPhraseMessage", Message.CODEC),
              (cfg, value) -> cfg.blockedPhrase = value,
              cfg -> cfg.blockedPhrase)
          .add()
          .append(
              new KeyedCodec<>("Formatters", MessageFormatter.LIST_CODEC),
              (cfg, value) -> cfg.formatters = value,
              cfg -> cfg.formatters)
          .add()
          .build();

  public Message blockedPhrase = Message.raw("Hey, you cannot say that!").color(Color.RED).bold(true);
  public List<MessageFormatter> formatters = List.of(
      new MessageFormatter("OP", Message.join(
          Message.raw("[ADMIN] ").color(Color.RED),
          Message.raw("{username}").color(Color.YELLOW),
          Message.raw(": {message}").color(Color.PINK)
      )));

  public Optional<Message> getFormatter(String name) {
    return formatters.stream()
        .filter(f -> f.getName().equals(name))
        .map(MessageFormatter::getMessage)
        .findFirst();
  }

}
