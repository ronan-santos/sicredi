# sicredi

# SWAGGER

* Json = /sicredi/api/v1/swagger.json
* Swagger ui = <<HOST>>/sicredi/docs/
  
 sendo: HOST = http://localhost:8080/, caso seja diferente, favor alterar a propriedade basePath da classe ApplicationConfig, do pacote sicredi.view
 
 # BANCO
 
 O banco foi hospedado na Amazon e para se conectar deve ser utilizado o datasource e o driver que estão na pasta docs.
 
 # APLICAÇÃO
 
A Aplicação utilizou o servidor wildfly 15.0.1 e o jdk 11.0.8

Tarefa Bônus

1 - Integração com sistemas externos
Foi realizada a integração no momento de armazenar o voto
classe da integração: CpfAssociadoIntegracao, no pacote sicredi.negocio.integracao

2 - Versionamento da API 
A api foi versionada na url, de forma que várias versões da mesma api podem ser implantadads no mesmo servidor
 

 
 
