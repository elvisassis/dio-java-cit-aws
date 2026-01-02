# 🧬 Java Generics in Practice: A Generic DAO

Este projeto é uma demonstração prática e aprofundada da utilização de **Java Generics** para a construção de um **Data Access Object (DAO) genérico** e reutilizável.

O foco principal é ilustrar como os Generics, quando aplicados corretamente, permitem a criação de APIs robustas, flexíveis e seguras em tempo de compilação. Mais do que apenas uma implementação de CRUD, este projeto serve como um guia para o design de componentes de software que são tanto extensíveis quanto fáceis de manter.

## ✨ Funcionalidades

O `GenericDAO` oferece um conjunto de operações de persistência de dados em memória, que podem ser facilmente adaptadas para outras formas de armazenamento (como bancos de dados). As principais funcionalidades incluem:

- **Salvar uma entidade:** `save(T domain)`
- **Salvar múltiplas entidades:** `saveBatch(T... domains)` e `saveAll(List<? extends T> items)`
- **Buscar uma entidade:** `find(Predicate<T> filter)`
- **Buscar todas as entidades:** `findAll()`
- **Atualizar uma entidade:** `update(ID id, T domain)`
- **Deletar uma entidade:** `delete(T domain)`
- **Contar o número de entidades:** `count()`

Além disso, a interface `Repository` demonstra o uso de métodos estáticos e genéricos para operações auxiliares, como `printIds` e `addIntegers`.

## 🚀 Como Usar

Para utilizar o `GenericDAO`, você precisa seguir estes passos:

1. **Definir sua entidade de domínio**, que deve estender `GenericDomain<ID>`:

```java
public class User extends GenericDomain<Long> {
    private String name;

    public User(Long id, String name) {
        super(id);
        this.name = name;
    }

    // Getters e Setters
}
```

2. **Criar uma implementação de DAO** que estenda `GenericDAO<ID, T>`:

```java
public class UserDAO extends GenericDAO<Long, User> {
    // Implementação específica do UserDAO, se necessário
}
```

3. **Utilizar o DAO em sua aplicação:**

```java
public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User user1 = new User(1L, "John Doe");
        userDAO.save(user1);

        System.out.println("Usuários salvos: " + userDAO.count());
    }
}
```

## 🧠 Conceitos-Chave Aplicados

### ✔ Generic Bounds

```java
public abstract class GenericDAO<ID, T extends GenericDomain<ID>>
```

Garante que:
- todo domínio possui um **ID**
- o DAO **nunca trabalha com tipos inválidos**
- existe consistência entre entidade e identificador

---

### ✔ PECS — Producer Extends, Consumer Super

| Cenário | Wildcard |
|------|---------|
| Só lê da coleção | `? extends T` |
| Só escreve na coleção | `? super T` |
| Lê e escreve | `T` |

Essa regra é aplicada conscientemente em todos os métodos do DAO.

---

## 🧪 Estratégia de Testes

Os testes **não validam apenas comportamento**, mas sim:

- contratos genéricos
- flexibilidade de tipos
- segurança da API
- intenção do design

Cada teste utiliza `@DisplayName` para funcionar como **documentação executável**.

---

## 🧪 Testes — Explicação por Método

---

### 🔹 `save(T)`

```java
@DisplayName("save(T) → Consumes and produces the same generic type")
```

📌 **Por quê?**  
O método **consome e produz `T`**, portanto **não utiliza wildcard**.

✔ Demonstra quando **NÃO usar** `extends` ou `super`.

---

### 🔹 `saveBatch(T...)`

```java
@DisplayName("saveBatch(T...) → Saves multiple elements keeping type safety")
```

📌 Demonstra:
- uso correto de **varargs genérico**
- preservação total do tipo `T`

---

### 🔹 `saveAll(Collection<? extends T>)`

```java
@DisplayName("saveAll(Collection<? extends T>) → Reads from a producer collection (covariance)")
```

📌 **Ponto mais importante do projeto**

Mesmo utilizando `addAll`, o método:
- ❌ não escreve em `items`
- ✅ apenas lê dela

Logo:
- `items` → **PRODUZ `T`**
- `db` → **CONSOME `T`**

✔ Uso correto de **covariância**

---

### 🔹 Teste parametrizado — `saveAll`

```java
@ParameterizedTest
@ValueSource(ints = {1, 3, 5})
```

📌 Demonstra que:
- o método funciona para qualquer tamanho de coleção
- o contrato genérico é independente da implementação concreta

---

### 🔹 `find(Predicate<T>)`

```java
@DisplayName("find(Predicate<T>) → Finds elements using a generic predicate")
```

📌 Demonstra:
- integração de Generics com programação funcional
- segurança de tipo mesmo com lambdas

---

### 🔹 `findAll()`

```java
@DisplayName("findAll() → Returns an immutable snapshot of internal storage")
```

📌 Demonstra:
- encapsulamento
- retorno seguro (`List.copyOf`)
- proteção contra efeitos colaterais

---

### 🔹 `update(ID, T)`

```java
@DisplayName("update(ID, T) → Preserves generic ID consistency across updates")
```

📌 Demonstra:
- uso correto de **ID genérico**
- manutenção do contrato entre entidade e identificador

---

### 🔹 Método genérico estático

```java
public static <T extends GenericDomain<?>> void printIds(List<T> items)
```

📌 Demonstra:
- generics em métodos estáticos
- independência do tipo de ID

Testado com diferentes coleções **sem perda de segurança**.

---

### 🔹 Contravariância — `addIntegers(List<? super Integer>)`

```java
@DisplayName("addIntegers(List<? super Integer>) → Consumes Integers using contravariance")
```

📌 Demonstra escrita segura em:
- `List<Integer>`
- `List<Number>`
- `List<Object>`

✔ Exemplo **real**, não teórico, de contravariância.

---

### 🔹 Teste parametrizado — Contravariância

```java
@MethodSource("superTypeLists")
```

📌 Prova que:
- o método aceita múltiplos supertypes
- o contrato genérico é **estável e extensível**

---

## 🎯 Conclusão

> **Java Generics não são sintaxe.  
> São contrato de API.**

Se seus testes não validam seus Generics,  
eles não estão protegendo sua arquitetura.

---

## 👤 Autor

**Elvis Assis**  

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Elvis%20Assis-blue?logo=linkedin&style=flat-square)](https://www.linkedin.com/in/elvis-assis)

Java | Spring Boot | Arquitetura | Clean Code | Generics Avançado
