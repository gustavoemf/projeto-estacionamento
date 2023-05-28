# 🚗 Estacionamento

O projeto consiste em um sistema de estacionamento desenvolvido utilizando a linguagem Java e o framework Spring Boot.
Nele podemos manipular informações e dados dos veículos, condutores e das movimentações realizadas pelos mesmos através de um banco de dados.

## 💻 Funcionalidades

A classe `MovimentacaoService` contém o método `cadastrarMovimentacao` realiza o cadastro de uma nova movimentação. Nela as seguintes verifiações serão realizadas PrePersist:
```
- Verifica se os valores da entidade Configuração necessários para realizar a movimentação existem
- Impede que o usuário insira os valores usados no cálculos, sendo apenas gerados pelo sistema
- Verifica se o id do condutor e do veiculo foi preenchido
- Verifica se existe uma entrada
- Verifica se existe um tempo de uso, caso haja, executa o cálculo de multa, desconto e valor total
```
A classe `MovimentacaoService` contém o método `atualizarMovimentacao` realiza a atualização de uma movimentação existente. Nela as seguintes verificações serão realizadas PreUpdate:
```
- Verifica se os valores da entidade Configuração necessários para realizar a movimentação existem
- Impede que o usuário insira os valores usados no cálculos, sendo apenas gerados pelo sistema
- Verifica se o id do condutor e do veiculo foi preenchido
- Verifica se existe uma entrada
- Verifica se existe um tempo de uso, caso haja, executa o cálculo de multa, desconto e valor total
- Impede a alteração da data de cadastro
```

## 🛠️ Pré-requisitos

Para iniciar, verifique se sua máquina atende aos seguintes requisitos:
```
1. Você instalou a versão mais recente de `<Java / Postman>`
2. Execute o método `main` na classe `EstacionamentoApplication`
3. A aplicação será iniciada e estará pronta para uso
*Verifique se todas dependências foram instaladas, caso contrário execute o BUILD*
```

## 🚀 Iniciando o projeto

Para iniciar o projeto, siga estas indicações:

O projeto será iniciado por meio da application `EstacionamentoApplication` que é responsável por executar a aplicação Spring Boot.
Ao executa-la, a aplicação inicializará e estará pronta para receber requisições e fornecer os serviços relacionados ao estacionamento.

## 📚 Desenvolvido com

O projeto foi construido utilizando as seguintes tecnologias:

* Java (https://www.java.com/pt-BR/download/help/whatis_java.html)
* Spring Boot (https://spring.io/projects/spring-boot)


## ✒️ Autor

* **Gustavo Müller** - [Github](https://github.com/gustavoemf)

[⬆ Voltar ao topo](#nome-do-projeto)<br>
