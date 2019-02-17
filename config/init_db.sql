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

CREATE UNIQUE INDEX contact_uuid_type_index ON contact(resume_uuid, type);
CREATE UNIQUE INDEX section_uuid_type_index ON section(resume_uuid, type);