CREATE TABLE resume
(uuid                       char(36) NOT NULL
   CONSTRAINT resume_pk
     PRIMARY KEY, full_name text     NOT NULL
);

CREATE TABLE contact
(id                           serial   NOT NULL
   CONSTRAINT contact_pk
     PRIMARY KEY, resume_uuid char(36) NOT NULL
   CONSTRAINT contact_resume_uuid_fk
     REFERENCES resume
     ON DELETE CASCADE, type  varchar  NOT NULL, value varchar NOT NULL
);

CREATE TABLE section
(id                           serial      NOT NULL
   CONSTRAINT section_pk
     PRIMARY KEY, resume_uuid char(36) NOT NULL
   CONSTRAINT section_resume_uuid_fk
     REFERENCES resume
     ON DELETE CASCADE, type  varchar     NOT NULL, value varchar NOT NULL
);

create table organization
(
  id serial not null,
  resume_uuid varchar(36) not null
    constraint organization_resume_uuid_fk
      references resume
      on delete cascade,
  name varchar not null,
  start_date timestamp not null,
  end_date timestamp,
  description varchar
);

CREATE UNIQUE INDEX contact_uuid_type_index ON contact(resume_uuid, type);
CREATE UNIQUE INDEX section_uuid_type_index ON section(resume_uuid, type);
CREATE UNIQUE INDEX organization_uuid_type_index ON organization(resume_uuid, name);