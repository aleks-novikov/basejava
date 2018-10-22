package model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String description;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String position, String description) {
        Objects.requireNonNull(name, "name mustn't be null");
        Objects.requireNonNull(startDate, "startDate mustn't be null");
        Objects.requireNonNull(endDate, "endDate mustn't be null");
        Objects.requireNonNull(position, "position mustn't be null");
        this.homepage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\n" + homepage + "\n" + startDate + " - " + endDate + ", " + position + ", " + description;
//        return startDate + " - " + endDate + ", " + position + ", " + description + "\n";
    }
}
