CREATE TABLE TEST_RESULT (
    id bigint not null primary key,
    host varchar,
    execute_date timestamp,
    status varchar,
    result varchar
);
CREATE SEQUENCE TEST_RESULT_ID_SEQ;
ALTER TABLE TEST_RESULT ALTER COLUMN id SET DEFAULT nextval('TEST_RESULT_ID_SEQ');