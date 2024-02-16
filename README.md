<h1 align="center">PokeSort API</h1>
<p align="center">Aplicação em Springboot que consome a Api externa do PokéApi
e retorna em forma de lista o pokémon pesquisado ou todos os pokémons</p>

<h1 align="center">
    <a href="https://pokeapi.co/docs/v2">🔗 PokéApi</a>
</h1>
<p align="center">🚀 Documentação da PokéApi usada</p>

# Objetivo
Construir uma API Rest com 2 endpoints:

### GET /pokemons
- query: String, Optional, se a consulta estiver vazia, retornar todos os pokémons listados na PokeApi
- sort: String/Enum, Optional, é possível ordernar por asc ou desc.

A ideia por trás deste endpoint é ser capaz de pesquisar por 
pokémons pelo seu nome - o usuário enviará uma parte 
(qualquer parte) do nome do pokémon como o queryparam para este ponto final
, e o serviço deve responder com uma lista dos pokémons. A busca deve ser insensível.


### GET /pokemons/highlight
- query: String, Optional, se a consulta estiver vazia, retornar todos os pokémons listados na PokeApi
- sort: String/Enum, Optional, é possível ordernar por asc ou desc.

Este endpoint tem, na maioria das vezes, os mesmos requisitos que o primeiro 
(deve receber os mesmos parâmetros da mesma forma), a única diferença é o 
requisito de resposta: juntamente com o nome pokémon, a resposta também deve destacar a 
substring que combinava o nome pokémon.. Se a consulta estiver vazia, deve considerar 
todos os pokemons listados no PokeAPI.

# Tecnologias
- Java 17
- Sprigboot Web
- Gradle

# Diagrama de Arquitetura
<img src="src/main/resources/static/diagrama.png" alt="Diagrama" width="650" height="450">

# Pontos de gargalo
Existem algumas features que podem ser implementadas futuramente, evitando assim gargalos na aplicação.
A primeira sugestão seria implementar um serviço de Cache central, como um Redis. Isso evitaria o problema de cache
local com uma aplicação rodando em pods diferentes, sendo o caso de uma pod ter o cache de uma consulta guardada e
a outra não.

Outro ponto interessante, caso o total de pokémons aumente drasticamente, a ordenação poderá demorar um pouco mais para
ser realizada, então alterar o algoritmo de ordenação para um Merge Sort seria o mais indicado. Mas, visto que a lista
de pokémons é pequena atualmente, o Quick Sort lida muito bem com essa ordenação.

# Rodando o projeto no Docker
O primeiro passo para rodar o projeto utilizando o Docker é gerar o jar na pasta do projeto com o comando:
<b>./gradlew bootjar</b>

Logo em seguida, rodar o comando <b>docker build -t looqbox-backend-challenge .</b>

Ele irá buildar a imagem do Docker. Podemos ver a imagem gerando utilizando o comando docker images

Com a imagem gerada, vamos executar ele localmente com o comando: 
<b>docker run --network="host" looqbox-backend-challenge:latest</b>
O parametro host é para expor a aplicação no endereço local da máquina
<b>http://localhost:8080</b>

# Autor
- Lucas Borges Silva
