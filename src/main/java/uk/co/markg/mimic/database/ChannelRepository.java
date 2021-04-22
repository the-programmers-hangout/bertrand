package uk.co.markg.mimic.database;

import static uk.co.markg.mimic.db.tables.Channels.CHANNELS;
import java.util.List;
import org.jooq.DSLContext;
import disparse.parser.reflection.Injectable;
import uk.co.markg.mimic.db.tables.pojos.Channels;

/**
 * A {@link org.jooq.DSLContext DSLContext} implementation to access the
 * {@link uk.co.markg.mimic.db.tables.Channels Channels} table generated by JOOQ.
 */
public class ChannelRepository {

  private DSLContext dsl;

  /**
   * {@link disparse.parser.reflection.Injectable Injectable} method used by disparse upon command
   * invocation.
   * 
   * @return A new channel respository instance
   */
  @Injectable
  public static ChannelRepository getRepository() {
    return new ChannelRepository();
  }

  private ChannelRepository() {
    dsl = JooqConnection.getJooqContext();
  }

  /**
   * Constructs a {@link uk.co.markg.mimic.db.tables.pojos.Channels Channels} Object to save to the
   * database.
   * 
   * @param channelid The discord channel id
   * @param read Whether the bot has read access to the channel
   * @param write Whether the bot has write access to the channel
   * @return The number of inserted rows
   */
  public int save(String channelid, Boolean read, Boolean write, Long serverid) {
    return save(new Channels(Long.parseLong(channelid), read, write, serverid));
  }

  /**
   * Saves a channel and its permissions to the database
   * 
   * @param channel The {@link uk.co.markg.mimic.db.tables.pojos.Channels Channel} object to save
   * @return The number of inserted rows
   */
  public int save(Channels channel) {
    return dsl.executeInsert(dsl.newRecord(CHANNELS, channel));
  }

  /**
   * Returns whether a channel exists in the database
   * 
   * @param channelid The target channel
   * @return True if the channel exists in the database
   */
  public boolean isChannelAdded(long channelid) {
    return dsl.selectFrom(CHANNELS).where(CHANNELS.CHANNELID.eq(channelid)).fetchOne(0,
        int.class) != 0;
  }

  /**
   * Returns whether a channel exists in the database and has read permission for the bot.
   * 
   * @param channelid The target channel
   * @return True if the channel has read permission
   */
  public boolean hasReadPermission(long channelid) {
    return dsl.selectFrom(CHANNELS).where(CHANNELS.CHANNELID.eq(channelid).and(CHANNELS.READ_PERM))
        .fetchOne(0, int.class) != 0;
  }

  /**
   * Returns whether a channel exists in the database and has write permission for the bot.
   * 
   * @param channelid The target channel
   * @return True if the channel has write permission
   */
  public boolean hasWritePermission(long channelid) {
    return dsl.selectFrom(CHANNELS).where(CHANNELS.CHANNELID.eq(channelid).and(CHANNELS.WRITE_PERM))
        .fetchOne(0, int.class) != 0;
  }

  /**
   * Updates the bot's read and write permissions to an existing channel in the database.
   * 
   * @param channelid The target channel
   * @param read Whether the bot should have read access to the channel
   * @param write Whether the bot should have write access to the channel
   */
  public void updatePermissions(long channelid, boolean read, boolean write) {
    dsl.update(CHANNELS).set((CHANNELS.READ_PERM), read).set((CHANNELS.WRITE_PERM), write)
        .where(CHANNELS.CHANNELID.eq(channelid)).execute();
  }

  /**
   * Retrieves all channels in the database
   * 
   * @param serverid The target server
   * @return List of all channels
   */
  public List<Channels> getAll(long serverid) {
    return dsl.selectFrom(CHANNELS).where(CHANNELS.SERVERID.eq(serverid)).fetchInto(Channels.class);
  }

  /**
   * Retrieves all channels in the database with read permission
   * 
   * @param serverid The target server
   * @return List of all channels with read permission
   */
  public List<Channels> getAllReadable(long serverid) {
    return dsl.selectFrom(CHANNELS).where(CHANNELS.READ_PERM).and(CHANNELS.SERVERID.eq(serverid))
        .fetchInto(Channels.class);
  }

  /**
   * Convenience method to delete a channel
   * 
   * @param channelid The target channel to delete
   * @return The number of rows deleted in the channel table
   */
  public int delete(String channelid) {
    return delete(Long.parseLong(channelid));
  }

  /**
   * Delete all data related to a channel
   * 
   * @param channelid The target channel to delete
   * @return The number of rows deleted in the channel table
   */
  public int delete(long channelid) {
    return dsl.deleteFrom(CHANNELS).where(CHANNELS.CHANNELID.eq(channelid)).execute();
  }

  /**
   * Delete all data related to a server
   * 
   * @param serverid The target server
   * @return The number of rows deleted in the server table
   */
  public int deleteByServerId(long serverid) {
    return dsl.deleteFrom(CHANNELS).where(CHANNELS.SERVERID.eq(serverid)).execute();
  }
}
