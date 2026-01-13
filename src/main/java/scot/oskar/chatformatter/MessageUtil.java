package scot.oskar.chatformatter;

import com.hypixel.hytale.codec.EmptyExtraInfo;
import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.server.core.Message;
import java.util.Map;
import javax.annotation.Nonnull;
import org.bson.BsonValue;

public class MessageUtil {

  public static Message replaceText(@Nonnull Message message, @Nonnull String placeholder, @Nonnull String value) {
    FormattedMessage fm = message.getFormattedMessage();

    if (fm.rawText != null) {
      fm.rawText = fm.rawText.replace(placeholder, value);
    }

    if (fm.children != null) {
      for (FormattedMessage child : fm.children) {
        replaceText(new Message(child), placeholder, value);
      }
    }

    return message;
  }

  public static Message replaceAll(@Nonnull Message message, @Nonnull Map<String, String> replacements) {
    replacements.forEach((placeholder, value) -> replaceText(message, placeholder, value));
    return message;
  }

  public static Message copy(@Nonnull Message original) {
    try {
      BsonValue encoded = Message.CODEC.encode(original, EmptyExtraInfo.EMPTY);
      return Message.CODEC.decode(encoded, EmptyExtraInfo.EMPTY);
    } catch (Exception e) {
      throw new RuntimeException("Failed to copy message", e);
    }
  }
}
