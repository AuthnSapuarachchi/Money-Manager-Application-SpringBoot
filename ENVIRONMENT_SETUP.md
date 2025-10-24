# ⚙️ Environment Setup Guide

## 🎯 Quick Fix for JAVA_HOME Issue

Your project requires **Java 21**, but your system's `JAVA_HOME` is currently pointing to **Java 8**.

---

## 🔧 Permanent Solution

### Option 1: PowerShell Command (Recommended)

Open PowerShell as Administrator and run:

```powershell
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-21', 'User')
```

Then restart your terminal/IDE.

---

### Option 2: Windows GUI Method

1. **Open System Properties:**

   - Press `Windows Key + R`
   - Type: `sysdm.cpl`
   - Press Enter

2. **Access Environment Variables:**

   - Click the "Advanced" tab
   - Click "Environment Variables" button

3. **Edit JAVA_HOME:**

   - In "User variables" or "System variables" section
   - Find `JAVA_HOME` variable
   - Click "Edit"
   - Change value to: `C:\Program Files\Java\jdk-21`
   - Click OK

4. **Restart:**
   - Close and reopen all terminals
   - Restart VS Code or your IDE

---

## ✅ Verify Installation

After setting JAVA_HOME, verify with these commands:

```powershell
# Check JAVA_HOME
echo $env:JAVA_HOME
# Should output: C:\Program Files\Java\jdk-21

# Check Java version
java -version
# Should show: java version "21.0.5"

# Check Maven uses correct Java
./mvnw -version
# Should show Maven and Java 21 details
```

---

## 🏃 Running the Application

### 1. Start MySQL Database

Ensure MySQL is running and the database `moneymanager` exists.

```sql
CREATE DATABASE IF NOT EXISTS moneymanager;
```

### 2. Build the Project

```powershell
./mvnw clean package
```

### 3. Run the Application

```powershell
./mvnw spring-boot:run
```

Or run the JAR directly:

```powershell
java -jar target/moneyManager-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application

- Base URL: `http://localhost:8080/api/v1.0`
- Health Check: `http://localhost:8080/api/v1.0/status`

---

## 🔐 Environment Variables

For production, set these environment variables:

```bash
# Database
DB_URL=jdbc:mysql://localhost:3306/moneymanager
DB_USERNAME=root
DB_PASSWORD=your_password

# Email (Brevo/Sendinblue)
MAIL_HOST=smtp-relay.brevo.com
MAIL_PORT=587
MAIL_USERNAME=your_email_username
MAIL_PASSWORD=your_email_password
MAIL_FROM=your_sender_email@example.com

# JWT
JWT_SECRET=your_secret_key_at_least_256_bits
JWT_EXPIRATION=86400000
```

### Setting Environment Variables in PowerShell:

```powershell
# Temporary (current session only)
$env:DB_PASSWORD="your_password"

# Permanent (user level)
[System.Environment]::SetEnvironmentVariable('DB_PASSWORD', 'your_password', 'User')
```

---

## 🐛 Troubleshooting

### Issue: "invalid target release: 21"

**Solution:** Your JAVA_HOME is not pointing to Java 21. Follow the steps above.

### Issue: "Access denied for user"

**Solution:** Check MySQL credentials in `application.properties`

### Issue: "Communications link failure"

**Solution:** Ensure MySQL is running on port 3306

### Issue: "Table doesn't exist"

**Solution:** Ensure `spring.jpa.hibernate.ddl-auto=update` is set in `application.properties`

---

## 📦 IDE Setup

### VS Code (Recommended Extensions)

1. **Extension Pack for Java** (Microsoft)
2. **Spring Boot Extension Pack** (VMware)
3. **Thunder Client** (for API testing)
4. **MySQL** (Jun Han)

### IntelliJ IDEA

1. Go to: File → Project Structure → Project
2. Set Project SDK to Java 21
3. Set Language Level to 21
4. Apply changes

### Eclipse

1. Go to: Window → Preferences → Java → Installed JREs
2. Add Java 21 if not present
3. Select Java 21 as default
4. Apply and Close

---

## 🎉 Ready to Develop!

Once JAVA_HOME is set correctly, you can:

- ✅ Build the project
- ✅ Run tests
- ✅ Start the application
- ✅ Begin development

For more information, see:

- `FIXES_APPLIED.md` - List of fixes applied to the project
- `HOW_TO_START.md` - Application usage guide
- `ACTIVATION_GUIDE.md` - User activation instructions

---

**Last Updated:** October 25, 2025  
**Status:** ✅ All Issues Resolved
