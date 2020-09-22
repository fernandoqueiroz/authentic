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

OAuth 2.0 Authorization Server implementations with Spring Boot platform, with management for Applications, Users and Token services with RDBMS and compatible with Spring Cloud Security oauth using Json Web Tokens (JWT).

Features
- Token Service for grant type client_credentials, password, refresh_token and authorization_code
- Services for management Applications
- Services for management Users
- Services for management Tokens with introspect and token_keys
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

## contact
> Fernando Queiroz Fonseca - fernandoqf@msn.com