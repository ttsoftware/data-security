PRAGMA foreign_keys = ON;

DROP TABLE Users;
DROP TABLE UserRolePermissions;
DROP TABLE UserRoles;

CREATE TABLE IF NOT EXISTS `UserRoles` (
  `id`   INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR UNIQUE NOT NULL,
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
INSERT OR IGNORE INTO "UserRoles" (id, name) VALUES (1, 'manager');
INSERT OR IGNORE INTO "UserRoles" (id, name) VALUES (2, 'service_technician');
INSERT OR IGNORE INTO "UserRoles" (id, name) VALUES (3, 'power_user');
INSERT OR IGNORE INTO "UserRoles" (id, name) VALUES (4, 'regular_user');

INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_RESTART', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_START', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_STOP', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_PRINT', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_CONFIG', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_WRITE_CONFIG', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_STATUS', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_QUEUE', 1);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_EDIT_QUEUE', 1);

INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_RESTART', 2);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_START', 2);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_STOP', 2);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_CONFIG', 2);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_WRITE_CONFIG', 2);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_STATUS', 2);

INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_RESTART', 3);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_PRINT', 3);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_QUEUE', 3);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_EDIT_QUEUE', 3);

INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_PRINT', 4);
INSERT OR IGNORE INTO "UserRolePermissions" (permission, fk_user_role) VALUES ('CAN_READ_QUEUE', 4);

INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('troels', 'rX5F15Rt1q0nECQxaM+NLGEZL3ZvB4MTYim72OzqFyv5CGdASHLG991DHouSw9neMlkFYHyUB9Y5xPUDFfMOqQ==', 'gdQ2KNLpIg0=', 1);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('Alice', '/BM6AM/HvoholWa8ivBtNGek+Ijt0If5p8XZABaEgABWb37KI60XXggxKKecYVgooMEApTrm8Cz24LNT10WxTg==', 'QtRAoSR8Mc4=', 1);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('Bob', '3NlCrvzerJy8OMXBxk4m0k0l7VpR7U7wC+KGzU4b7W34OlUFlZp9cuc+fvgloY0qNaGqq8Av1jz0eUlMeP8Nsg==', 'b2M0D7gMNas=', 2);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('Cecilia', 'zlwzlhznMu8ljhVstoiG8b0U87LZ0ee2Ifc1p5qO/vYJOFSZPs2rJw/ez9shpLbh3WODoe8iNY9vYFVWZZgN5Q==', 'Gq8100+aVe0=', 3);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('David', 'ZFn7tb7AsHQEuWCf5Fpzw3IFi7HG/6RC1tPnocdFynLjCz4le20PJhe037OhqBiAlFsCjMLacjUDgcHHnFHg/A==', '9iZF+dSQ3K8=', 4);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('Erica', 'vYX2zTod9oewI6+kOCg+TkVEVynb/qkEZWYcz+Dz+tpBtRoydyGeumhseuauph2gqRbRsWla2sNHVyHDHmjx1w==', 'PpGBlwFeykc=', 4);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('Fred', 'CKZFtv8ElZMsX+7M1pv0cJDg6OM7Hc9/hCBAzQ9etxZxYP2ZvBiBjb8dcjlAguvDI7Ts0W3+Z0h0F5jSdNH79g==', 'cH4bpAyP1oA=', 4);
INSERT OR IGNORE INTO "Users" (name, password, salt, fk_user_role) VALUES ('George', '61NEDEOP3zzJ3R2ntYGV/mV1aXLABQZyTO/++hxMiJQlZqosWxDU1Cbt1ZoH+LExENd1V0ZOLidGKd5ba2lT8g==', 'kcl0cco3NWo=', 4);

COMMIT;