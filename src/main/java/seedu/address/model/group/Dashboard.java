package seedu.address.model.group;

/**
 * Represents a Dashboard for a Group with various metrics and notes.
 */
public class Dashboard {
    Group groupAttachedTo;
    String notes;

    public Dashboard(Group groupAttachedTo) {
        this.groupAttachedTo = groupAttachedTo;
        this.notes = "";
    }

    /**
     * Creates a dashboard with existing notes
     */
    public Dashboard(Group groupAttachedTo, String notes) {
        this.groupAttachedTo = groupAttachedTo;
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    @Override
    public String toString() {
        return notes;
    }
}
