# 🔐 Profile Activation Guide - Money Manager Application

## Overview

When a user registers, their account is created with `isActive = false`. They must activate their account before they can log in.

---

## 📧 Method 1: Email Activation (Production)

### Step 1: Register a User

```http
POST http://localhost:8080/api/v1.0/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john@example.com",
  "profileImageUrl": null,
  "createdAt": "2025-10-20T09:00:00",
  "updatedAt": "2025-10-20T09:00:00"
}
```

### Step 2: Check Email

An email will be sent to `john@example.com` with an activation link:

```
Subject: Activate your Money Manager Account
Body: Click on the following link to activate your account:
      http://localhost:8080/api/v1.0/activate?token=abc123-def456-ghi789
```

### Step 3: Click Activation Link

Either click the link in the email or visit it in a browser.

**Response (Success):**

```
Profile activated successfully
```

**Response (Failure):**

```
Activation token not found or already used
```

---

## 🧪 Method 2: Manual Activation (Development/Testing)

### Option A: Using the Development Endpoint

#### Step 1: Register a User

Same as Method 1, Step 1

#### Step 2: Get Activation Token

```http
GET http://localhost:8080/api/v1.0/dev/activation-token?email=john@example.com
```

**Response:**

```json
{
  "email": "john@example.com",
  "activationToken": "abc123-def456-ghi789",
  "activationLink": "http://localhost:8080/api/v1.0/activate?token=abc123-def456-ghi789"
}
```

#### Step 3: Use the Activation Link

Copy the `activationLink` from the response and visit it in your browser or API client.

```http
GET http://localhost:8080/api/v1.0/activate?token=abc123-def456-ghi789
```

---

### Option B: Direct Database Query

#### Step 1: Register a User

Same as Method 1, Step 1

#### Step 2: Query MySQL Database

```sql
SELECT activation_token, email, is_active
FROM tbl_profiles
WHERE email = 'john@example.com';
```

**Result:**

```
| activation_token              | email              | is_active |
|-------------------------------|-------------------|-----------|
| abc123-def456-ghi789          | john@example.com  | 0         |
```

#### Step 3: Activate Using Token

```http
GET http://localhost:8080/api/v1.0/activate?token=abc123-def456-ghi789
```

#### Step 4: Verify Activation

```sql
SELECT activation_token, email, is_active
FROM tbl_profiles
WHERE email = 'john@example.com';
```

**Result (After Activation):**

```
| activation_token              | email              | is_active |
|-------------------------------|-------------------|-----------|
| abc123-def456-ghi789          | john@example.com  | 1         |
```

---

## 🔑 Login After Activation

Once the account is activated, you can log in:

```http
POST http://localhost:8080/api/v1.0/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response (Success):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    "profileImageUrl": null,
    "createdAt": "2025-10-20T09:00:00",
    "updatedAt": "2025-10-20T09:00:00"
  }
}
```

**Response (If Not Activated):**

```json
{
  "message": "Account is not active. Please activate your account first."
}
```

---

## 🛠️ Testing with Postman or cURL

### 1. Register

```bash
curl -X POST http://localhost:8080/api/v1.0/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "test123"
  }'
```

### 2. Get Activation Token (Dev Mode)

```bash
curl -X GET "http://localhost:8080/api/v1.0/dev/activation-token?email=test@example.com"
```

### 3. Activate Account

```bash
curl -X GET "http://localhost:8080/api/v1.0/activate?token=YOUR_TOKEN_HERE"
```

### 4. Login

```bash
curl -X POST http://localhost:8080/api/v1.0/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test123"
  }'
```

---

## 📝 Important Notes

1. **Email Configuration**: Make sure your email settings are configured using environment variables. Create a `.env` file based on `.env.example`:

   ```properties
   # Set these environment variables or use .env file
   MAIL_HOST=smtp-relay.brevo.com
   MAIL_PORT=587
   MAIL_USERNAME=your_brevo_username
   MAIL_PASSWORD=your_brevo_api_key
   MAIL_FROM=your_email@example.com
   ```

2. **Security**: The `/dev/activation-token` endpoint should be **removed or disabled** in production.

3. **Token Uniqueness**: Each activation token is generated using `UUID.randomUUID()`, ensuring uniqueness.

4. **One-Time Use**: Once an account is activated (`isActive = true`), the token cannot be used again.

5. **Database**: Ensure your MySQL database `moneymanager` is running and accessible.

---

## 🚀 Quick Test Workflow

```bash
# 1. Register
POST /api/v1.0/register

# 2. Get token (dev mode)
GET /api/v1.0/dev/activation-token?email=YOUR_EMAIL

# 3. Activate
GET /api/v1.0/activate?token=YOUR_TOKEN

# 4. Login
POST /api/v1.0/login
```

---

## ❌ Troubleshooting

### "Activation token not found or already used"

- Token might be incorrect
- Account might already be activated
- Check database: `SELECT * FROM tbl_profiles WHERE email = 'your@email.com';`

### "Account is not active. Please activate your account first."

- Account exists but not activated yet
- Use one of the activation methods above

### Email not received

- Check spam folder
- Verify email configuration in `application.properties`
- Use dev endpoint for testing: `/dev/activation-token`

---

## 🔒 Security Considerations for Production

1. **Remove Dev Endpoints**: Delete or comment out the `/dev/activation-token` endpoint
2. **Use HTTPS**: Always use HTTPS in production
3. **Token Expiration**: Consider adding token expiration (not currently implemented)
4. **Rate Limiting**: Implement rate limiting on registration and activation endpoints
5. **Email Validation**: Add email validation before sending activation emails

---

**Happy Coding! 🎉**
