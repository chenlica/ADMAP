/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.dao.jooq.generated.tables.pojos;


import edu.uci.ics.texera.dao.jooq.generated.enums.ProjectUserAccessPrivilege;
import edu.uci.ics.texera.dao.jooq.generated.tables.interfaces.IProjectUserAccess;

import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProjectUserAccess implements IProjectUserAccess {

    private static final long serialVersionUID = 1449179369;

    private UInteger                   uid;
    private UInteger                   pid;
    private ProjectUserAccessPrivilege privilege;

    public ProjectUserAccess() {}

    public ProjectUserAccess(IProjectUserAccess value) {
        this.uid = value.getUid();
        this.pid = value.getPid();
        this.privilege = value.getPrivilege();
    }

    public ProjectUserAccess(
        UInteger                   uid,
        UInteger                   pid,
        ProjectUserAccessPrivilege privilege
    ) {
        this.uid = uid;
        this.pid = pid;
        this.privilege = privilege;
    }

    @Override
    public UInteger getUid() {
        return this.uid;
    }

    @Override
    public void setUid(UInteger uid) {
        this.uid = uid;
    }

    @Override
    public UInteger getPid() {
        return this.pid;
    }

    @Override
    public void setPid(UInteger pid) {
        this.pid = pid;
    }

    @Override
    public ProjectUserAccessPrivilege getPrivilege() {
        return this.privilege;
    }

    @Override
    public void setPrivilege(ProjectUserAccessPrivilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProjectUserAccess (");

        sb.append(uid);
        sb.append(", ").append(pid);
        sb.append(", ").append(privilege);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IProjectUserAccess from) {
        setUid(from.getUid());
        setPid(from.getPid());
        setPrivilege(from.getPrivilege());
    }

    @Override
    public <E extends IProjectUserAccess> E into(E into) {
        into.from(this);
        return into;
    }
}
