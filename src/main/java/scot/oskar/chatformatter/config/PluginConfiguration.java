package scot.oskar.chatformatter.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import java.util.List;
import scot.oskar.chatformatter.ListCodec;

public class PluginConfiguration {

  public static final ListCodec<String> STRING_LIST_CODEC = new ListCodec<>(Codec.STRING);

  public static BuilderCodec<PluginConfiguration> CODEC =
      BuilderCodec.builder(PluginConfiguration.class, PluginConfiguration::new)
          .append(
              new KeyedCodec<>("Messages", MessagesConfig.CODEC),
              (cfg, value) -> cfg.messagesConfig = value,
              cfg -> cfg.messagesConfig)
          .add()
          .append(
              new KeyedCodec<>("BlockedPhrases", STRING_LIST_CODEC),
              (cfg, value) -> cfg.blockedPhrases = value,
              cfg -> cfg.blockedPhrases)
          .add()
          .build();

  public MessagesConfig messagesConfig = new MessagesConfig();
  public List<String> blockedPhrases = List.of("shit", "fuck");
}
