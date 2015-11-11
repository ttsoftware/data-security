PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS `UserRoles` (
  `id`   INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR NOT NULL,
  UNIQUE (`name`)
);

CREATE TABLE IF NOT EXISTS `UserRolePermissions` (
  `id`           INTEGER PRIMARY KEY AUTOINCREMENT,
  `permission`   VARCHAR NOT NULL,
  `fk_user_role` INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS `Users` (
  `id`           INTEGER PRIMARY KEY AUTOINCREMENT,
  `name`         VARCHAR NOT NULL,
  `password`     VARCHAR NOT NULL,
  `salt`         VARCHAR NOT NULL,
  `fk_user_role` INTEGER NOT NULL,
  UNIQUE (`name`)
);

BEGIN TRANSACTION;
INSERT INTO "UserRoles" VALUES (1, 'manager');

INSERT INTO "UserRolePermissions" VALUES (1, 'CAN_RESTART', 1);
INSERT INTO "UserRolePermissions" VALUES (2, 'CAN_START', 1);
INSERT INTO "UserRolePermissions" VALUES (3, 'CAN_STOP', 1);
INSERT INTO "UserRolePermissions" VALUES (4, 'CAN_PRINT', 1);
INSERT INTO "UserRolePermissions" VALUES (5, 'CAN_READ_CONFIG', 1);
INSERT INTO "UserRolePermissions" VALUES (6, 'CAN_SET_CONFIG', 1);
INSERT INTO "UserRolePermissions" VALUES (7, 'CAN_READ_QUEUE', 1);
INSERT INTO "UserRolePermissions" VALUES (8, 'CAN_EDIT_QUEUE', 1);

INSERT INTO "Users" VALUES (1, 'troels', 'QYVAl/IC2PQfgMXCVklxNR32y/PGdQoxCdAXH3QXFBlRJBHACcSZpHqn65e7vQ+bvNdk4Df2lQg0
1Zbd1/tkBA==', '0yPuOpV3qbA=', 1);

DELETE FROM sqlite_sequence;

INSERT INTO "sqlite_sequence" VALUES ('UserRoles', 1);
INSERT INTO "sqlite_sequence" VALUES ('UserRolePermissions', 8);
INSERT INTO "sqlite_sequence" VALUES ('Users', 1);
COMMIT;
