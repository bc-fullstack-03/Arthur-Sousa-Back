# Arthur-Sousa-Back



### Pontos iniciais

- Essa API possui uma interface gráfica gerada pelo Swagger para auxiliar na compreensão das requisições, porém será necessário utilizar um serviço como Postman, pois grande partes das requisições necessitam do token de autenticação .
-  O ambiente de desenvolvimento está na porta 8080.


# Banco de dados e upload de arquivos.

 O banco de dados utilizado na aplicação é o MongoDB, manipulado pelo framework Spring Data MongoDB. Para upload de arquivos.
## Observação 
 Meu notebook ele não tem processador pra rodar o docker. 
 Foi no suporte do docker eles falaram que precisavar de memória e de ran

# Serviço Autenticação
 Aonde realiza o verbo POST, obtendo o email e a senha, retornando os dados do usuários, além  de gerar um token de autenticação.

No application.properties vai ficar os dados do token e expiração.

 

---
### Token de autenticação
O token de autenticação é gerado utilizando a biblioteca JSON WEB Token, ele utiliza uma chave BASE64 e o criptografa em HS256.


