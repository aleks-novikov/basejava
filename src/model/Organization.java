package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private List<OrganizationInfo> list = new ArrayList<>();

    public Organization(String name, String url) {
        Objects.requireNonNull(name, "name mustn't be null");
        this.homepage = new Link(name, url);
    }

    public void addOrganizationInfo (LocalDate startDate, LocalDate endDate, String position, String description) {
        list.add(new OrganizationInfo(startDate, endDate, position, description));
    }

    public class OrganizationInfo {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String position;
        private final String description;

        public OrganizationInfo(LocalDate startDate, LocalDate endDate, String position, String description) {
            Objects.requireNonNull(startDate, "startDate mustn't be null");
            Objects.requireNonNull(endDate, "endDate mustn't be null");
            Objects.requireNonNull(position, "position mustn't be null");
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
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n" + homepage);
        for (int i = 0; i < list.size(); i++) {
            sb.append("\n" + list.get(i).startDate + " - " + list.get(i).endDate
                    + ", " + list.get(i).position + ", " + list.get(i).description);
        }
        return sb.toString();
    }
}
