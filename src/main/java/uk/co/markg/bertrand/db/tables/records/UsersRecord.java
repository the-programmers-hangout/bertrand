/*
 * This file is generated by jOOQ.
 */
package uk.co.markg.bertrand.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.UpdatableRecordImpl;

import uk.co.markg.bertrand.db.tables.Users;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record1<Long> {

    private static final long serialVersionUID = -1151084952;

    /**
     * Setter for <code>users.userid</code>.
     */
    public void setUserid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>users.userid</code>.
     */
    public Long getUserid() {
        return (Long) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<Long> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<Long> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Users.USERS.USERID;
    }

    @Override
    public Long component1() {
        return getUserid();
    }

    @Override
    public Long value1() {
        return getUserid();
    }

    @Override
    public UsersRecord value1(Long value) {
        setUserid(value);
        return this;
    }

    @Override
    public UsersRecord values(Long value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(Long userid) {
        super(Users.USERS);

        set(0, userid);
    }
}
