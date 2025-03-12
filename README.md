# User API Documentation

## Base URL

```
http://localhost:9000/users
```

## Authentication

This API uses **OAuth 2.0 with JWT tokens** for authentication. Clients must include a **Bearer Token** in the `Authorization` header for protected endpoints.

**Example Header:**

```
Authorization: JWT_TOKEN
```

## Endpoints

### 1. User Signup (Public)

**Endpoint:**

```
POST /users/signup
```

**Description:** Creates a new user. **Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe",
  "street": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zipcode": "10001",
  "country": "USA",
  "roles": ["ROLE_USER"],
  "resetPasswordQuestion": "What is your pet's name?",
  "resetPasswordAnswer": "Fluffy"
}
```

**Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "roles": ["ROLE_USER"]
}
```

**Response Code:** `201 Created`

---

### 2. Get All Users (Requires Authentication)

**Endpoint:**

```
GET /users/getuser/all
```

**Description:** Fetches all users (Only accessible by `ROLE_SUPER_ADMIN`). **Response:**

```json
[
  {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe",
    "roles": ["ROLE_USER"]
  }
]
```

**Response Code:** `200 OK`

---

### 3. Get User by Email (Requires Authentication)

**Endpoint:**

```
GET /users/getuser/{email}
```

**Description:** Fetches a user by their email (Only accessible to the user themselves). **Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "roles": ["ROLE_USER"]
}
```

**Response Code:** `200 OK`

---

### 4. Get Reset Password Question (Public)

**Endpoint:**

```
GET /users/getresetpasswordquestion/{email}
```

**Description:** Returns the reset password security question for a user. **Response:**

```json
{
  "resetPasswordQuestion": "What is your pet's name?"
}
```

**Response Code:** `200 OK`

---

### 5. Reset Password (Public)

**Endpoint:**

```
POST /users/resetpassword
```

**Description:** Resets the user's password. **Request Body:**

```json
{
  "email": "user@example.com",
  "resetPasswordQuestion": "What is your pet's name?",
  "resetPasswordAnswer": "Fluffy",
  "newPassword": "newpassword123"
}
```

**Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "roles": ["ROLE_USER"]
}
```

**Response Code:** `200 OK`

---

### 6. Update User Details (Requires Authentication)

**Endpoint:**

```
PATCH /users/updateuser/{id}
```

**Description:** Updates the details of a user (Only accessible to the user themselves). **Request Body:**

```json
{
  "name": "John Smith",
  "city": "Los Angeles"
}
```

**Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Smith",
  "city": "Los Angeles"
}
```

**Response Code:** `200 OK`

---

### 7. Add Role to User (Requires Authentication)

**Endpoint:**

```
PATCH /users/addrole/{id}
```

**Description:** Adds a role to the user (Only accessible to the user themselves). **Query Parameter:**

```
roleName=ROLE_ADMIN
```

**Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "roles": ["ROLE_USER", "ROLE_ADMIN"]
}
```

**Response Code:** `200 OK`

---

### 8. Remove Role from User (Requires Authentication)

**Endpoint:**

```
PATCH /users/removerole/{id}
```

**Description:** Removes a role from the user (Only accessible to the user themselves). **Query Parameter:**

```
roleName=ROLE_ADMIN
```

**Response:**

```json
{
  "id": 1,
  "email": "user@example.com",
  "roles": ["ROLE_USER"]
}
```

**Response Code:** `200 OK`

---

### 9. Delete User (Requires Authentication)

**Endpoint:**

```
DELETE /users/deleteuser/{email}
```

**Description:** Deletes a user account (Only accessible to the user themselves). **Response Code:** `200 OK`

---

## Security & Authentication

- **OAuth 2.0 with JWT authentication required**
- **Public Endpoints:**
  - `/users/signup`
  - `/users/resetpassword`
  - `/users/getresetpasswordquestion/{email}`
- **Authentication Required:**
  - `/users/getuser/**`
  - `/users/updateuser/**`
  - `/users/addrole/**`
  - `/users/removerole/**`
  - `/users/deleteuser/**`

## Error Responses

- **400 Bad Request:** Invalid request format.
- **401 Unauthorized:** Invalid or missing JWT token.
- **403 Forbidden:** User does not have permission.
- **404 Not Found:** User not found.
- **500 Internal Server Error:** Unexpected server error.

---

### Author

*API developed for user management with OAuth 2.0 authentication and role-based access control.*

