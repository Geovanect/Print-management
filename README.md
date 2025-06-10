# Print-Management
Projeto da faculdade que teve como objetivo desafiar nossas capacidades de racicinios durante os assuntos estudados na matéria de Estrutura de Dados e o uso de filas.

Print-Management é um projeto que simula o funcionamento de uma impressora, onde teremos metodos de adição removação e listagem de documentos, para esse projeto foi pedido o uso de uma estrutura heap e para isso fizemos o uso da PriorityQueue para ordenação e impressão de documentos com prioridade




## Roadmap

Comecei pelo backend declarando a entidade documento e os atributos que ele teria

Herdei a interface Comparable para usar do metodo CompareTo de forma que eu pudesse fazer uma classifcação de prioridades para arquivos que tivessem prioridade na fila

Defini uma propriedade para ordem de chegada, essa será o criterio de desempate para caso arquivos de mesma prioridade fossem adicionados na PriorityQueue

Fiz os services contendo os metodos de Adicionar Remover e Listar a união das duas listas

Nesse passo sabiamos que iriamos implementar uma estrutura de frontend para poder complementar nosso projeto e apartir dai fui atrás de maneiras de fazer com que esse back ganhasse forma

Encontrei o Maven que iria gerenciar todas as dependecias na qual meu projeto precisaria incluindo o spring boot

Comecei o desenvolvimento dos componentes do front

utilizei o react para poder realizar as requisições Http usando Vite e a construção dos componentes do front, finalizando assim a interface grafica da nossa impressora digital.




## Instalação

Pré-requisitos: Antes de iniciar, certifique-se de que os seguintes softwares estão instalados na sua máquina:

Java JDK (versão 17 ou superior)

Apache Maven

Node.js e NPM (versão 16 ou superior)

Rodar Backend

```bash
cd Backend
  mvn spring-boot:run
```

Rodar Frontend

```bash
cd Frontend
  npm install
  npm run dev
```
