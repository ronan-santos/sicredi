# sicredi

# SWAGGER

* Json = /sicredi/api/v1/swagger.json
* Swagger ui = <HOST>/sicredi/docs/
  
 sendo: HOST = http://localhost:8080/, caso seja diferente, favor alterar a propriedade basePath da classe ApplicationConfig, do pacote sicredi.view
 
 # BANCO
 
 O banco foi hospedado na Amazon e para se conectar deve ser utilizado o datasource abaixo:
 
 <!-- DATASOURCE -->
  <datasource jndi-name="java:jboss/datasources/PostgresqlSicrediDS" pool-name="PostgresqlSicrediDS" enabled="true" use-java-context="true">
      <connection-url>jdbc:postgresql://teste-sicredi.czvvszd8niiw.us-east-2.rds.amazonaws.com:5432/SICREDI</connection-url>
      <driver>postgresql</driver>
      <security>
          <user-name>userSicredi</user-name>
          <password>sicredi123P</password>
      </security>
  </datasource>
  
  <!-- DRIVER -->
   <driver name="postgresql" module="org.postgresql">
       <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
    </driver>
 
 
