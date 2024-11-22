/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.dao.jooq.generated.tables.records;


import edu.uci.ics.texera.dao.jooq.generated.enums.WorkflowUserAccessPrivilege;
import edu.uci.ics.texera.dao.jooq.generated.tables.WorkflowUserAccess;
import edu.uci.ics.texera.dao.jooq.generated.tables.interfaces.IWorkflowUserAccess;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WorkflowUserAccessRecord extends UpdatableRecordImpl<WorkflowUserAccessRecord> implements Record3<UInteger, UInteger, WorkflowUserAccessPrivilege>, IWorkflowUserAccess {

    private static final long serialVersionUID = 2064079376;

    /**
     * Setter for <code>texera_db.workflow_user_access.uid</code>.
     */
    @Override
    public void setUid(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>texera_db.workflow_user_access.uid</code>.
     */
    @Override
    public UInteger getUid() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>texera_db.workflow_user_access.wid</code>.
     */
    @Override
    public void setWid(UInteger value) {
        set(1, value);
    }

    /**
     * Getter for <code>texera_db.workflow_user_access.wid</code>.
     */
    @Override
    public UInteger getWid() {
        return (UInteger) get(1);
    }

    /**
     * Setter for <code>texera_db.workflow_user_access.privilege</code>.
     */
    @Override
    public void setPrivilege(WorkflowUserAccessPrivilege value) {
        set(2, value);
    }

    /**
     * Getter for <code>texera_db.workflow_user_access.privilege</code>.
     */
    @Override
    public WorkflowUserAccessPrivilege getPrivilege() {
        return (WorkflowUserAccessPrivilege) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UInteger, UInteger> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UInteger, UInteger, WorkflowUserAccessPrivilege> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UInteger, UInteger, WorkflowUserAccessPrivilege> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UInteger> field1() {
        return WorkflowUserAccess.WORKFLOW_USER_ACCESS.UID;
    }

    @Override
    public Field<UInteger> field2() {
        return WorkflowUserAccess.WORKFLOW_USER_ACCESS.WID;
    }

    @Override
    public Field<WorkflowUserAccessPrivilege> field3() {
        return WorkflowUserAccess.WORKFLOW_USER_ACCESS.PRIVILEGE;
    }

    @Override
    public UInteger component1() {
        return getUid();
    }

    @Override
    public UInteger component2() {
        return getWid();
    }

    @Override
    public WorkflowUserAccessPrivilege component3() {
        return getPrivilege();
    }

    @Override
    public UInteger value1() {
        return getUid();
    }

    @Override
    public UInteger value2() {
        return getWid();
    }

    @Override
    public WorkflowUserAccessPrivilege value3() {
        return getPrivilege();
    }

    @Override
    public WorkflowUserAccessRecord value1(UInteger value) {
        setUid(value);
        return this;
    }

    @Override
    public WorkflowUserAccessRecord value2(UInteger value) {
        setWid(value);
        return this;
    }

    @Override
    public WorkflowUserAccessRecord value3(WorkflowUserAccessPrivilege value) {
        setPrivilege(value);
        return this;
    }

    @Override
    public WorkflowUserAccessRecord values(UInteger value1, UInteger value2, WorkflowUserAccessPrivilege value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWorkflowUserAccess from) {
        setUid(from.getUid());
        setWid(from.getWid());
        setPrivilege(from.getPrivilege());
    }

    @Override
    public <E extends IWorkflowUserAccess> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WorkflowUserAccessRecord
     */
    public WorkflowUserAccessRecord() {
        super(WorkflowUserAccess.WORKFLOW_USER_ACCESS);
    }

    /**
     * Create a detached, initialised WorkflowUserAccessRecord
     */
    public WorkflowUserAccessRecord(UInteger uid, UInteger wid, WorkflowUserAccessPrivilege privilege) {
        super(WorkflowUserAccess.WORKFLOW_USER_ACCESS);

        set(0, uid);
        set(1, wid);
        set(2, privilege);
    }
}