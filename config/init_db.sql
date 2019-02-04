CREATE TABLE resume
(
  uuid      char(36) NOT NULL
    CONSTRAINT resume_pk
      PRIMARY KEY,
  full_name text     NOT NULL
);

CREATE TABLE contact
(
  id          serial   NOT NULL
    CONSTRAINT contact_pk
      PRIMARY KEY,
  resume_uuid char(36) NOT NULL
    CONSTRAINT contact_resume_uuid_fk
      REFERENCES resume
      ON DELETE CASCADE,
  type        varchar  NOT NULL,
  value       varchar  NOT NULL
);

CREATE TABLE section
(id                    serial  NOT NULL
   CONSTRAINT section_pk
     PRIMARY KEY, type varchar NOT NULL, value varchar NOT NULL, resume_uuid varchar(36)
   CONSTRAINT section_resume_uuid_fk
     REFERENCES resume
     ON UPDATE RESTRICT ON DELETE CASCADE
);

ALTER TABLE section OWNER TO postgres;



CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);