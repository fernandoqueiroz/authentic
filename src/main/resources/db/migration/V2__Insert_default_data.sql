INSERT INTO `auth`.`platform` (`id` , `uuid` , `name` , `domain` , `subdomains` , `status`)
VALUES (1, '476822d0-8c78-4d35-a112-9cefabec3108', 'Default Platform', 'localhost', '', 1);

INSERT INTO `client_application` (id, client_id, client_secret, grant_types, scope, resource_ids, refresh_token_validity, access_token_validity, redirect_uri, additional_information, platform_id)
VALUES (1,'client','$2a$10$q.HLgBm/xkvEWpUix4NVzud2G3660sXsfmwoWdIvdHIH6O7Onrdce','password,client_credentials,refresh_token,authorization_code','read,write,password,openid', 'resources', NULL, 600, 'http://localhost:8085/me', NULL, 1);

INSERT INTO `role` (id, name, description, platform_id)
VALUES (1,'USER','General users',1),(2,'ADMIN','General admins',1);

INSERT INTO `user` (id, username, password, status, platform_id)
VALUES (1,'user','$2a$10$LX259oNd/sWlbopcXUPMbemzLYIdy1g6k1HIJV761EBoiqaSMvq5q',1,1);

INSERT INTO `user_role` (user_id, role_id) VALUES (1,1),(1,2);

