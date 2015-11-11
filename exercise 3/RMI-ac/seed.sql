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
INSERT OR IGNORE INTO "UserRoles" VALUES (1, 'manager');

INSERT OR IGNORE INTO "UserRolePermissions" VALUES (1, 'CAN_RESTART', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (2, 'CAN_START', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (3, 'CAN_STOP', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (4, 'CAN_PRINT', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (5, 'CAN_READ_CONFIG', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (6, 'CAN_WRITE_CONFIG', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (7, 'CAN_READ_STATUS', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (8, 'CAN_READ_QUEUE', 1);
INSERT OR IGNORE INTO "UserRolePermissions" VALUES (9, 'CAN_EDIT_QUEUE', 1);

INSERT OR IGNORE INTO "Users" VALUES (1, 'troels', 'rX5F15Rt1q0nECQxaM+NLGEZL3ZvB4MTYim72OzqFyv5CGdASHLG991DHouSw9neMlkFYHyUB9Y5xPUDFfMOqQ==', 'gdQ2KNLpIg0=', 1);

DELETE FROM sqlite_sequence;

INSERT INTO "sqlite_sequence" VALUES ('UserRoles', 1);
INSERT INTO "sqlite_sequence" VALUES ('UserRolePermissions', 8);
INSERT INTO "sqlite_sequence" VALUES ('Users', 1);
COMMIT;
