# Git User Request Count

---
### Description
This is a Spring Boot application.  
We have one endpoint which returns GitHub custom user info with some calculation.  
Information from GitHub is provided by hitting its API.
After request has been successful we increment and save a counter in a database (H2).
### EndPoints
* http://localhost:8080/users/{login}
  * login - Path variable containing GitHub user login