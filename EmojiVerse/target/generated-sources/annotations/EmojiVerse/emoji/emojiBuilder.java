package EmojiVerse.emoji;

import io.norberg.automatter.AutoMatter;
import javax.annotation.processing.Generated;

@Generated("io.norberg.automatter.processor.AutoMatterProcessor")
public final class emojiBuilder {
  private String name;

  private String img;

  public emojiBuilder() {
  }

  private emojiBuilder(emoji v) {
    this.name = v.name();
    this.img = v.img();
  }

  private emojiBuilder(emojiBuilder v) {
    this.name = v.name;
    this.img = v.img;
  }

  public String name() {
    return name;
  }

  public emojiBuilder name(String name) {
    if (name == null) {
      throw new NullPointerException("name");
    }
    this.name = name;
    return this;
  }

  public String img() {
    return img;
  }

  public emojiBuilder img(String img) {
    if (img == null) {
      throw new NullPointerException("img");
    }
    this.img = img;
    return this;
  }

  public emoji build() {
    return new Value(name, img);
  }

  public static emojiBuilder from(emoji v) {
    return new emojiBuilder(v);
  }

  public static emojiBuilder from(emojiBuilder v) {
    return new emojiBuilder(v);
  }

  private static final class Value implements emoji {
    private final String name;

    private final String img;

    private Value(@AutoMatter.Field("name") String name, @AutoMatter.Field("img") String img) {
      if (name == null) {
        throw new NullPointerException("name");
      }
      if (img == null) {
        throw new NullPointerException("img");
      }
      this.name = name;
      this.img = img;
    }

    @AutoMatter.Field
    @Override
    public String name() {
      return name;
    }

    @AutoMatter.Field
    @Override
    public String img() {
      return img;
    }

    public emojiBuilder builder() {
      return new emojiBuilder(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof emoji)) {
        return false;
      }
      final emoji that = (emoji) o;
      if (name != null ? !name.equals(that.name()) : that.name() != null) {
        return false;
      }
      if (img != null ? !img.equals(that.img()) : that.img() != null) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      long temp;
      result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
      result = 31 * result + (this.img != null ? this.img.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "emoji{" +
      "name=" + name +
      ", img=" + img +
      '}';
    }
  }
}
