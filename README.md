# 🎮 GameOn Backend API

Este projeto é o backend da aplicação **GameOn**, desenvolvido com Spring Boot. Ele fornece uma API RESTful para gerenciar usuários, postagens, comentários e listas de amigos.

---

## 🚀 Como rodar o projeto

### Pré-requisitos

- Java 17+ (recomendado)
- Maven 3.6+

### Passos

```bash
git clone <repo-url>
cd gameonBack-master
./mvnw spring-boot:run
```
# 📁 Estrutura do Projeto
```bash
gameonBack-master/
│
├── src/main/java/com/gameonDatabse/gameOn/
│   ├── config/                  # Configurações gerais do projeto
│   ├── models/                  # Modelos (entidades)
│   ├── repositories/            # Interfaces JPA
│   └── routesapi/RouterApi.java # Controlador principal da API
│
├── ProfileImages/               # Imagens de perfil dos usuários
├── pom.xml                      # Dependências Maven
└── README.md                    # Este arquivo
```
# 📚 Endpoints da API
Base URL: http://localhost:8080/api

## ✅Teste
| Método | Rota   | Descrição              |
| ------ | ------ | ---------------------- |
| GET    | /hello | Retorna "Hello world!" |

## 👤 Usuários 
| Método | Rota            | Descrição            |
| ------ | --------------- | -------------------- |
| POST   | /user\_create   | Cria um novo usuário |
| GET    | /get\_user/{id} | Retorna um usuário   |

* Exemplo de requisição para criação de usuário:
```bash
POST /api/user_create
Content-Type: application/json

{
  "userName": "joao",
  "email": "joao@email.com",
  "password": "123456"
}
```
* Exemplo de resposta:
```bash
{
  "id": 1,
  "userName": "joao",
  "email": "joao@email.com"
}
```
# 📝 Postagens
| Método | Rota            | Descrição              |
| ------ | --------------- | ---------------------- |
| GET    | /get\_post/{id} | Retorna um post por ID |

# 🧩 Modelos de Dados

* 📌 UserModel
```java
Long id
String userName
String email
String password
```
* 🧾 UserPosts
```java
Long id
String postContent
Date postDate
Long userId
```
* 💬 PostComments
```java
Long id
String content
Long postId
Long userId
```
* 🤝 FriendList
```java
Long id
Long userId
Long friendId
```
### 🛠 Tecnologias Usadas
* Java 21

* Spring Boot

* Spring Web

* Spring Data JPA

* Jackson

* Maven
  
# 📸 Imagens de Perfil
As imagens de perfil dos usuários são armazenadas na pasta:
```
/ProfileImages
```
E podem ser acessadas/atualizadas por meio de endpoints de upload (se implementado).

# 📄 Licença
Este projeto é acadêmico/exemplo e não possui uma licença definida.

# Autores
- Rafael Zachesky de Oliveira, RA:21010026
- Diogo Leal de Silva, RA:21000426
