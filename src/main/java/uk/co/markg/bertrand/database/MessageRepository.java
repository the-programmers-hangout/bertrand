package uk.co.markg.bertrand.database;

import static uk.co.markg.bertrand.db.tables.Messages.MESSAGES;
import java.util.List;
import org.jooq.DSLContext;
import disparse.parser.reflection.Injectable;
import net.dv8tion.jda.api.entities.Message;
import uk.co.markg.bertrand.db.tables.pojos.Messages;
import uk.co.markg.bertrand.db.tables.records.MessagesRecord;

public class MessageRepository {

  private DSLContext dsl;

  /**
   * {@link disparse.parser.reflection.Injectable Injectable} method used by disparse upon command
   * invocation.
   * 
   * @return a new message repository instance
   */
  @Injectable
  public static MessageRepository getRepository() {
    return new MessageRepository();
  }

  private MessageRepository() {
    dsl = JooqConnection.getJooqContext();
  }

  /**
   * Create a {@link uk.co.markg.bertrand.db.tables.pojos.Messages Messages} instance from a userid
   * and a discord message and saves it to the database
   * 
   * @param userid  the userid of the message author
   * @param message the discord message object
   * @return the number of rows inserted
   */
  public int save(long userid, Message message) {
    var msg = new Messages(message.getIdLong(), userid, message.getContentRaw(),
        message.getChannel().getIdLong());
    return save(msg);
  }

  /**
   * Save a message into the database
   * 
   * @param message the message to be saved
   * @return the number of rows inserted
   */
  public int save(Messages message) {
    return dsl.executeInsert(dsl.newRecord(MESSAGES, message));
  }

  /**
   * Returns a list of messages as strings belonging to all userids in the list
   * 
   * @param userids the userids to get the messages for
   * @return the list of messages
   */
  public List<String> getByUsers(List<Long> userids) {
    return dsl.select(MESSAGES.CONTENT).from(MESSAGES).where(MESSAGES.USERID.in(userids))
        .fetchInto(String.class);
  }

  /**
   * Batch inserts a list of {@link uk.co.markg.bertrand.db.tables.records.MessagesRecord
   * MessageRecord}
   * 
   * @param batch the batch of messages to insert
   */
  public void batchInsert(List<MessagesRecord> batch) {
    dsl.batchInsert(batch).execute();
  }

  /**
   * Deletes a message by its message id from the database if it exists
   * 
   * @param messageid the messageid of the message to delete
   * @return the number of rows deleted
   */
  public int deleteById(long messageid) {
    return dsl.deleteFrom(MESSAGES).where(MESSAGES.MESSAGEID.eq(messageid)).execute();
  }

}
