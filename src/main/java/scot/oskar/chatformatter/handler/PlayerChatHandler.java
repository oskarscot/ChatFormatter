package scot.oskar.chatformatter.handler;

import static scot.oskar.chatformatter.MessageUtil.copy;
import static scot.oskar.chatformatter.MessageUtil.replaceText;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import java.util.Optional;
import scot.oskar.chatformatter.config.PluginConfiguration;

public class PlayerChatHandler {

  private final PluginConfiguration pluginConfiguration;

  public PlayerChatHandler(PluginConfiguration pluginConfiguration) {
    this.pluginConfiguration = pluginConfiguration;
  }

  public void onPlayerChat(PlayerChatEvent event) {
    PlayerRef sender = event.getSender();
    String message = event.getContent();
    if(pluginConfiguration.blockedPhrases.contains(message)) {
      sender.sendMessage(pluginConfiguration.messagesConfig.blockedPhrase);
      event.setCancelled(true);
      return;
    }

    PermissionsModule permissionsModule = PermissionsModule.get();
    permissionsModule.getGroupsForUser(sender.getUuid()).stream()
        .map(group -> pluginConfiguration.messagesConfig.getFormatter(group))
        .flatMap(Optional::stream)
        .findFirst()
        .ifPresent(
            originalMsg -> {
              event.setFormatter((playerRef, content) -> {
                Message msg = copy(originalMsg);
                replaceText(msg, "{username}", playerRef.getUsername());
                replaceText(msg, "{message}", content);
                return msg;
              });
            });
  }

  public void register(EventRegistry eventRegistry) {
    eventRegistry.registerGlobal(PlayerChatEvent.class, this::onPlayerChat);
  }
}
