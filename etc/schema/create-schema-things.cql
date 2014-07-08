CREATE KEYSPACE orm_things
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE orm_things;

CREATE TABLE "Location" (
    id uuid PRIMARY KEY,
    "name" text,
   	"latitude" text,
   	"longitude" text,
   	"modeId" uuid
) WITH COMPACT STORAGE;

CREATE TABLE "Location_IDX" (
    key text,
    column1 uuid,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;

CREATE TABLE "Location_LNK" (
    key text,
    column1 text,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;


CREATE TABLE "Mode" (
    id uuid PRIMARY KEY,
    name text
) WITH COMPACT STORAGE;

CREATE TABLE "Mode_IDX" (
    key text,
    column1 uuid,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;

CREATE TABLE "Mode_LNK" (
    key text,
    column1 text,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;


CREATE TABLE "Hub" (
    id uuid PRIMARY KEY,
    "locationId" uuid,
    "name" text,
	"radioId" text
) WITH COMPACT STORAGE;

CREATE TABLE "Hub_IDX" (
    key text,
    column1 uuid,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;

CREATE TABLE "Hub_LNK" (
    key text,
    column1 text,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;


CREATE TABLE "Device" (
    id uuid PRIMARY KEY,
    "locationId" uuid,
    "hubId" uuid,
    "name" text,
    "deviceNetworkId" text,
    "deviceTypeId" uuid
) WITH COMPACT STORAGE;

CREATE TABLE "Device_IDX" (
    key text,
    column1 uuid,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;

CREATE TABLE "Device_LNK" (
    key text,
    column1 text,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;


CREATE TABLE "DeviceType" (
    id uuid PRIMARY KEY,
    name text
) WITH COMPACT STORAGE;

CREATE TABLE "DeviceType_IDX" (
    key text,
    column1 uuid,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;

CREATE TABLE "DeviceType_LNK" (
    key text,
    column1 text,
    PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE;