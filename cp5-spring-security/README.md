# CP5 — Parte 2: Spring Security MVC + PostgreSQL

## Como rodar
1) Crie o banco:
```sql
CREATE DATABASE cp5_market;
```
2) Ajuste `src/main/resources/application.yml` com usuário/senha do PostgreSQL.
3) Compile e suba:
```bash
mvn spring-boot:run
```
4) Acesse: http://localhost:8080/
5) Login padrão criado automaticamente: `admin` / `admin123`

## Rotas
- `/` Home
- `/signup` Cadastro
- `/login` Login customizado
- `/products` Lista
- `/products/new` Novo (ADMIN)
- `/products/{id}/edit` Editar (ADMIN)
- `/products/{id}` POST atualizar (ADMIN)
- `/products/{id}/delete` Excluir (ADMIN)

## Perfis
- USER: leitura de produtos
- ADMIN: CRUD completo

## Deploy
Sugestão: Render.com (Web Service, JDK 17). Configurar variáveis `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.
