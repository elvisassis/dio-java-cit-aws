# 🧬 GenericDAO — Advanced Java Generics in Practice

Este projeto demonstra o uso **avançado e correto de Java Generics**, com foco em:

- contratos de API
- covariância (`extends`)
- contravariância (`super`)
- PECS (Producer Extends, Consumer Super)
- testes unitários como **documentação viva de design**

O objetivo **não é CRUD**, mas sim **ensinar Generics aplicados ao design de APIs reutilizáveis**.

---

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
Java | Spring Boot | Arquitetura | Clean Code | Generics Avançado
