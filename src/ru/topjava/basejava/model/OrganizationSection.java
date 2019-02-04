package ru.topjava.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> organisations;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organisations) {
        Objects.requireNonNull(organisations, "organisations mustn't be null");
        this.organisations = organisations;
    }

    public List<Organization> getOrganisations() {
        return organisations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organisations.equals(that.organisations);
    }

    @Override
    public int hashCode() {
        return organisations.hashCode();
    }

    @Override
    public String toString() {
        return organisations.toString();
    }
}
