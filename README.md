# Online Bookstore

A React/Vite storefront backed by a Spring Boot REST API and an H2 in-memory database.

## Prerequisites

- Java 17
- Node.js 18 or newer

## Run the backend

```powershell
cd backend
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17.0.18"
.\mvnw.cmd spring-boot:run
```

The backend starts at `http://localhost:8080`; the H2 console is available at `http://localhost:8080/h2-console`.

## Run the frontend

```powershell
cd frontend
npm install
npm run dev
```

Open `http://localhost:3000`.

## API endpoints

| Purpose | Method and endpoint |
| --- | --- |
| List books | `GET /api/books` |
| Register | `POST /api/auth/register` |
| Login | `POST /api/auth/login` |
| Read cart | `GET /api/cart/{username}/items` |
| Add cart item | `POST /api/cart/{username}/items` |
| Change quantity | `PUT /api/cart/{username}/items/{bookId}` |
| Remove cart item | `DELETE /api/cart/{username}/items/{bookId}` |
| Create order | `POST /api/orders?username={username}` |

Cart request bodies use `{ "bookId": 1, "quantity": 1 }`. The order endpoint accepts an array of book IDs, for example `[1, 1, 2]`.

## Verify

```powershell
cd backend
.\mvnw.cmd test

cd ..\frontend
npm run build
```

The API returns `201 Created` for registration, cart additions, and completed orders; `200 OK` for reads and updates; `204 No Content` for removal; `400 Bad Request` for invalid requests; and `404 Not Found` for unknown users, books, or cart items.
