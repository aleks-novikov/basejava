package model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private final List<LocalDate> startDate;
    private final List<LocalDate> endDate;
    private final List<String> position;
    private final List<String> description;

    public Organization(String name, String url) {
        Objects.requireNonNull(name, "name mustn't be null");
        this.homepage = new Link(name, url);
        this.startDate = new LinkedList<>();
        this.endDate = new LinkedList<>();
        this.position = new LinkedList<>();
        this.description = new LinkedList<>();
    }

    public void addOrganisationInfo(LocalDate startDate, LocalDate endDate, String position, String description) {
        Objects.requireNonNull(startDate, "startDate mustn't be null");
        Objects.requireNonNull(endDate, "endDate mustn't be null");
        Objects.requireNonNull(position, "position mustn't be null");
        this.startDate.add(startDate);
        this.endDate.add(endDate);
        this.position.add(position);
        this.description.add(description);
    }

    public LocalDate getStartDate(int index) {
        return startDate.get(index);
    }

    public LocalDate getEndDate(int index) {
        return endDate.get(index);
    }

    public String getPosition(int index) {
        return position.get(index);
    }

    public String getDescription(int index) {
        return description.get(index);
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
        StringBuilder sb = new StringBuilder("\n" + homepage);
        for (int i = 0; i < position.size(); i++) {
            sb.append(" " + "\n" + startDate.get(i) + " - " + endDate.get(i) + ", " + position.get(i) + ", " + description.get(i));
        }
        return sb.toString();
    }
}
