package model;

import util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Position> positions = new ArrayList<>();
//    private Set<Position> positions = new HashSet<>();

    public Organization() {
    }

    public Organization(String name, String url) {
        this(new Link(name, url));
    }

    public Link getHomePage() {
        return homePage;
    }

    public Organization(Link homePage) {
        this.homePage = homePage;
    }

    public void addOrganizationInfo(LocalDate startDate, LocalDate endDate, String title, String description) {
        positions.add(new Position(startDate, endDate, title, description));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {
        }

        Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate mustn't be null");
            Objects.requireNonNull(endDate, "endDate mustn't be null");
            Objects.requireNonNull(title, "title mustn't be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position oi = (Position) o;
            return startDate.equals(oi.startDate) &&
                    endDate.equals(oi.endDate) &&
                    title.equals(oi.title) &&
                    description.equals(oi.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n" + homePage);
        for (int i = 0; i < positions.size(); i++) {
            sb.append("\n" + positions.get(i).startDate + " - " + positions.get(i).endDate
                    + ", " + positions.get(i).title + ", " + positions.get(i).description);
        }
        return sb.toString();
    }
}
