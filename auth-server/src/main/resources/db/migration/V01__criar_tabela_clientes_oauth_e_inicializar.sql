create table oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

INSERT INTO OAUTH_CLIENT_DETAILS
	(CLIENT_ID, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, ACCESS_TOKEN_VALIDITY, WEB_SERVER_REDIRECT_URI, AUTOAPPROVE)
 VALUES 
 	('sipas', '123', 'LISTAR_PROCEDIMENTOS', 'password,authorization_code,refresh_token,implicit,client_credentials', 300, 'http://localhost:8082/token-handler', 'true'),
 	('bse', '123', 'LISTAR_PUBLICACOES', 'password,authorization_code,refresh_token,implicit,client_credentials', 300, 'http://localhost:8081/token-handler', 'true');