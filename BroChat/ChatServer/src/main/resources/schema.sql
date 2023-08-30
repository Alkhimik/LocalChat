CREATE SCHEMA IF NOT EXISTS chatServerData;

CREATE TABLE chatServerData.userInfo(
id int primary key,
login text not null,
password text not null
);

--DROP TABLE chatServerData.userInfo;