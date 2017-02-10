# Teste Desenvolvedor 2 - Zup

API desenvolvida em JAVA para processo seletivo da Zup, utilizando Spring MVC e Hibernate

## Começando

Abaixo seguem as instruções para configuração do projeto

### Pré requisitos

É necessário ter instalado:

```
Java 8
```
```
Tomcat 8
```
```
MySQL
```
```
Maven
```
```
IDE Eclipse - Utilizei a versão Spring Tool Suite 3.8.3 Release
```
### Instalação

A API foi desenvolvida simulando o funcionamento em três ambientes, utilizando o conceito de <strong>Profiles</strong> do Spring, sendo eles: 
* production 
* development
* test

No arquivo <strong>web.xml</strong>, há um parâmetro de inicialização chamado <strong>spring.profiles.active</strong>. Por padrão está apontado para o ambiente <strong>test</strong>.</br>
Abaixo, segue exemplos de utilização:

```
Ambiente de Testes

<context-param>
		<param-name>spring.profiles.active</param-name>
		<param-value>test</param-value>
</context-param>
 
```
```
Ambiente de Desenvolvimento

<context-param>
		<param-name>spring.profiles.active</param-name>
		<param-value>development</param-value>
</context-param>
 
```
```
Ambiente de Produção

<context-param>
		<param-name>spring.profiles.active</param-name>
		<param-value>production</param-value>
</context-param>
 
 ```


Na pasta <strong>resources</strong>, há um diretório chamado <strong>environment</strong>. Nesse diretório há subdiretórios 
para cada ambiente com os devidos arquivos de configuração (.properties) para o datasource da aplicação. Cada arquivo possui parâmetros de configuração do Hibernate, JDBC e Pool de
conexões. </br></br>
Faz-se necessário criar manualmente o banco de dados conforme definido nas urls do arquivos (ou alterando caso necessário), bem como 
definir o usuário e senha para acesso ao banco de dados. 


## Testes Unitários

Foram realizados testes unitários para as camadas <strong>model</strong>, <strong>dao</strong> e <strong>controller</strong></br>

Assim como no arquivo <strong>web.xml</strong>, é necessário definir em qual ambiente se deseja executar os testes (Exceto os testes
da camada model).</br>
A Annotation utilizada para alternar entre os ambientes é: <strong>@ActiveProfile</strong></br>
Abaixo, segue exemplos de utilização:

```
@ActiveProfile('test') - Ambiente de Testes
```
```
@ActiveProfile('development') - Ambiente de Desenvolvimento
```
```
@ActiveProfile('production') - Ambiente de Produção
```

Nos ambientes <strong>test</strong> e <strong>development</strong>, o Hibernate está configurado para automaticamente limpar e popular
o banco de dados com os dados das POIs informadas na descrição do <strong>Teste Desenvolvedor 2</strong>

### Serviços RESTful

* Salvar POI
```
Método: POST
```
```
Content-Type: application/json
```
```
Path: api/points
```

```
Body:

{
  "name": "Supermercado",
  "coordinateX": 10,
  "coordinateY": 30
}

name: String(Obrigatório, em até 50 caracters)
coordinateX: Int(Obrigatório, positivo entre 1 a 10000)
coordinateX: Int(Obrigatório, positivo entre 1 a 10000)

```

```
Response:
{
  "code": 200,
  "message": null,
  "status": "OK",
  "data": null
}

```

* Listar POIs
```
Método: GET
```
```
Content-Type: application/json
```
```
Path: api/points
```

```
Response:
{
  "code": 200,
  "message": null,
  "status": "OK",
  "data": [
    {
      "id": 1,
      "name": "Lanchonete",
      "coordinateX": 27,
      "coordinateY": 12
    },
  ]
}
```

* Listar POIs por Proximidade
```
Método: GET
```
```
Content-Type: application/json
```
```
Path: api/points?lat=latitude&lng=longitue&distance=distancia_em_metros

lat: Int(Obrigatório e positivo)
lng: Int(Obrigatório e postivo)
distance: Int(Obrigatório e positivo)
```

```
Response:
{
  "code": 200,
  "message": null,
  "status": "OK",
  "data": [
    {
      "id": 1,
      "name": "Lanchonete",
      "coordinateX": 27,
      "coordinateY": 12
    },
  ]
}
```

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

