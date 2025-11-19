# 🍽️ FEIFood -- Sistema de Delivery em Java (MVC + PostgreSQL)

O **FEIFood** é uma aplicação desktop que simula um sistema de delivery
de comida.\
O usuário pode se cadastrar, fazer login, navegar pelo cardápio, montar
um carrinho e finalizar pedidos --- tudo integrado a um banco
PostgreSQL.

O sistema foi desenvolvido utilizando:

-   **Java 17**
-   **NetBeans**
-   **Swing (GUI)**
-   **PostgreSQL**
-   **JDBC**
-   **MVC**
-   **Batch SQL + Transações**
-   **BoxLayout / Renderização dinâmica de JPanels**

------------------------------------------------------------------------

## 🚀 Funcionalidades

-   Cadastro e login de usuários\
-   Pesquisa de alimentos e categorias\
-   Exibição dinâmica de cardápio\
-   Carrinho de compras com aumento/diminuição de itens\
-   Finalização de pedidos\
-   Avaliação pós-compra\
-   Persistência completa no banco de dados\
-   Implementação de polimorfismo em bebidas alcoólicas

------------------------------------------------------------------------

## 🗂️ Arquitetura (MVC)

### **Model**

-   Usuario\
-   Alimento (abstrata)\
-   Comida\
-   Bebida (implementa ImpostoAlcool)\
-   ItemPedido\
-   Pedido\
-   ImpostoAlcool (interface)

### **View (Swing)**

Telas (JFrames), com elementos dinâmicos criados em runtime: - Login\
- Cadastrar\
- PaginaInicial\
- Busca\
- Carrinho\
- Avaliacao

### **Controller**

Responsáveis por toda a lógica entre GUI e Model: - ControllerLogin\
- ControllerCadastro\
- ControllerBuscar\
- ControllerCarrinho\
- ControllerAvaliacao\
- ControllerUsuario (sessão)\
- ControllerPedido (carrinho)

### **DAO (PostgreSQL via JDBC)**

-   Conexao\
-   UsuarioDAO\
-   AlimentoDAO\
-   PedidoDAO (usa transação + batch insert)

------------------------------------------------------------------------

## 🗄️ Banco de Dados (PostgreSQL)

### Configuração da Conexão

    URL: jdbc:postgresql://localhost:5433/FEIFood_v4
    User: postgres
    Password: fei
    Database: FEIFood_v4

### Tabelas Necessárias

#### **tb_usuario**

-   ID\
-   nome\
-   email\
-   endereço\
-   telefone\
-   senha

#### **tb_alimentos**

-   ID\
-   Nome\
-   Categoria\
-   Valor\
-   Restaurante\
-   Informacoes

#### **tb_itens**

-   ID_Pedido\
-   ID_Alimento\
-   Quantidade\
-   Valor_Unitario\
-   Valor_Total

#### **tb_pedidos**

-   ID_Pedido\
-   ID_Cliente\
-   Valor_Total\
-   Avaliacao

------------------------------------------------------------------------

## ▶️ Como Executar

1.  Certifique-se de que o PostgreSQL está rodando com o banco
    **FEIFood_v4**.

2.  Na pasta `dist/lib`, verifique se existe:

        postgresql-42.7.8.jar

3.  Execute o arquivo principal:

        FEIFood.jar

------------------------------------------------------------------------

## 🛒 Fluxo de Uso

1.  Login ou Cadastro\
2.  Página inicial com busca e categorias\
3.  Tela de resultados\
4.  Carrinho\
5.  Finalização do pedido\
6.  Avaliação

------------------------------------------------------------------------

## ⚙️ Destaques Técnicos

-   Construção dinâmica de JPanels com BoxLayout\
-   Transações SQL com rollback\
-   Sessão persistente via ControllerUsuario\
-   Carrinho global via ControllerPedido\
-   Polimorfismo aplicado a bebidas alcoólicas

------------------------------------------------------------------------

## 📁 Estrutura do Projeto

    src/
     ├── controller/
     ├── dao/
     ├── model/
     └── view/

    dist/
     ├── FEIFood.jar
     └── lib/postgresql-42.7.8.jar

------------------------------------------------------------------------

## 📘 Licença

Esse projeto é de uso educacional e pode ser utilizado livremente.
