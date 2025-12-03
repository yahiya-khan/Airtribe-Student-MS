package airtribe.studentms.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Person {
    private final String id;
    private final String name;
    private final LocalDate dob;

    protected Person(String id, String name, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDob() { return dob; }

    public String getDobFormatted() {
        return dob.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) DOB: %s", name, id, getDobFormatted());
    }
}
