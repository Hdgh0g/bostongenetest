create type translation_status as enum (
  'TRANSLATION_NEEDED',
  'TRANSLATED',
  'NOT_DEFINED_ORIGINAL_LANGUAGE',
  'ERROR_IN_TRANSLATION'
);

create table translations (
  id UUID primary key,
  source_language varchar(30) null,
  target_language varchar(30) null,
  status translation_status not null,
  translated_text varchar null
);

create table appeal_answers (
  id UUID primary key,
  text varchar not null,
  username varchar(30) not null,
  creation_date_time timestamp not null default now(),
  translation_id UUID null references translations
);

create type appeal_status as enum (
  'OPEN',
  'CLOSED'
);

create table appeals (
  id UUID primary key,
  text varchar not null,
  username varchar(30) not null,
  status appeal_status not null,
  creation_date_time timestamp not null default now(),
  answer_id UUID null references appeal_answers,
  translation_id UUID null references translations
);