<pre>  
                                                        by Fernando Queiroz Fonseca
  ______               __      __                              __      __           
 /      \             /  |    /  |                            /  |    /  |          
/$$$$$$  | __    __  _$$ |_   $$ |____    ______   _______   _$$ |_   $$/   _______ 
$$ |__$$ |/  |  /  |/ $$   |  $$      \  /      \ /       \ / $$   |  /  | /       |
$$    $$ |$$ |  $$ |$$$$$$/   $$$$$$$  |/$$$$$$  |$$$$$$$  |$$$$$$/   $$ |/$$$$$$$/ 
$$$$$$$$ |$$ |  $$ |  $$ | __ $$ |  $$ |$$    $$ |$$ |  $$ |  $$ | __ $$ |$$ |      
$$ |  $$ |$$ \__$$ |  $$ |/  |$$ |  $$ |$$$$$$$$/ $$ |  $$ |  $$ |/  |$$ |$$ \_____ 
$$ |  $$ |$$    $$/   $$  $$/ $$ |  $$ |$$       |$$ |  $$ |  $$  $$/ $$ |$$       |
$$/   $$/  $$$$$$/     $$$$/  $$/   $$/  $$$$$$$/ $$/   $$/    $$$$/  $$/  $$$$$$$/ 

                                          oAuth 2.0 implementation with Spring Boot                                                                                    
</pre>

# authentic v1.0

OAuth 2.0 Authorization Server implementation with Spring Boot, including management for Applications, Users and Token services (JWT format).
Compatible with Spring Cloud Security and MySQL RDBMS.

Features
- Token Service for grant type client_credentials, password, refresh_token and authorization_code
- Services for management Applications
- Services for management Users (*Partial)
- Services for management Tokens with introspect and token_keys (*Partial)
- Automatic database creation scripts for MySQL

# contribute
clone authentic's repository, send me your atlassian e-mail for view our jira board, develop a new feature or bugfix and submit your PR.

Clone repository:
> git clone https://github.com/fernandoqueiroz/authentic.git


# run with docker
Install docker-ce and docker-compose and execute the following cmd's in project root directory:

> ./mvnw clean install

> docker build -t authentic . 

> docker-compose up 

# testing

Authenticate a default application:

> curl --location --request POST 'http://localhost:8085/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--data-urlencode 'grant_type=client_credentials'

Authenticate and get token for default user:

> curl --location --request POST 'http://localhost:8085/oauth/token' \
--header 'Content-Type: multipart/form-data;' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--form 'grant_type=password' \
--form 'username=user' \
--form 'password=pass'

Get token info (introspect):

> curl --location --request POST 'http://localhost:8085/oauth/check_token' \
--header 'Content-Type: multipart/form-data' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--form 'token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjpbInJlc291cmNlcyJdLCJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsicGFzc3dvcmQiLCJyZWFkIiwib3BlbmlkIiwid3JpdGUiXSwicGxhdGZvcm1faWQiOjEsImV4cCI6MTYwMDc0MTgxMSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI3NzE0MTJiNy05YmZmLTQwNjItODcyOC0wZmY5NzYzNGU0OTYiLCJjbGllbnRfaWQiOiJjbGllbnQifQ.JGQDCte8OjLZ2pslgmSlxrgOsZ4sQ1sHMX0HpgXm6SPMsoZfJPGqfn3H0ubDFsW1QLlVSWTiGF3eRrwIhjxmnTjO5dT0k3XUUvhJ7NlDzSSQtY7PjFiFPy5wlQGy2lHab40ZaITxW-w4kduTC4RwkLv3dhbhltXMa7oMqP9Zy0WX3-oOZOwekrAteUNW8ot03h8IkNnycK4bWls2gnJ61rTE6MZIrQf00XTD3HRPXeE4QgwH9cMtyBon-7UYnGpH1rGR-LUGRUoLHcEHQPXOiL4mCfGOQ8OK3Aa6f43qiAq8LsKjwd1tGENt4--miSimZv4Ul1X21NjORXtzonOR9Q'


## contact
> Fernando Queiroz Fonseca - fernandoqf@msn.com
