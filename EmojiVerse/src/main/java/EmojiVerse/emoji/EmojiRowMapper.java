package EmojiVerse.emoji;

import org.jdbi.v3.core.StatementContext;
import org.jdbi.v3.core.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmojiRowMapper implements RowMapper<Emoji> {

    @Override
    public Emoji map(ResultSet rs, org.jdbi.v3.core.statement.StatementContext ctx) throws SQLException {
        Emoji emoji = new Emoji();
        emoji.setName(rs.getString("emoji"));
        emoji.setPrice(rs.getInt("emoji_price"));
        emoji.setCategory(rs.getString("category"));
        return emoji;
    }
}
