package scot.oskar.chatformatter;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import javax.annotation.Nonnull;
import scot.oskar.chatformatter.config.PluginConfiguration;
import scot.oskar.chatformatter.handler.PlayerChatHandler;

public class ChatFormatter extends JavaPlugin {

  private final Config<PluginConfiguration> config;

  public ChatFormatter(@Nonnull JavaPluginInit init) {
    super(init);
    this.config = this.withConfig("ChatFormatter", PluginConfiguration.CODEC);
  }

  @Override
  protected void setup() {
    this.config.save();
    new PlayerChatHandler(config.get()).register(this.getEventRegistry());
  }
}
