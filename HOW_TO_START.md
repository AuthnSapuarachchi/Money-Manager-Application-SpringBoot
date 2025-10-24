# 🚀 Money Manager Application - Startup Guide

## ⚠️ IMPORTANT: Never Run Individual Java Files!

**DO NOT** try to run files like `MoneyManagerApplication.java` directly using `javac` or `java` commands. This is a **Spring Boot application** that requires the entire framework and dependencies to be loaded.

---

## ✅ **How to Start the Application**

### **Method 1: Using Maven Wrapper (Recommended)**

This is the easiest and most reliable method:

```powershell
# Navigate to project directory
cd "c:\Users\ASUS TUF X506H\Desktop\My Projects\OnGoing Projects\MoneyManager - Authn"

# Set Java Home (if needed)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Start the application
./mvnw spring-boot:run
```

**Expected Output:**

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.5.6)

...
Tomcat started on port 8080 (http) with context path '/api/v1.0'
Started MoneyManagerApplication in X.XXX seconds
```

---

### **Method 2: Build JAR and Run**

If you want to run the compiled JAR file:

```powershell
# Step 1: Build the project
./mvnw clean package -DskipTests

# Step 2: Run the JAR
java -jar target/moneyManager-0.0.1-SNAPSHOT.jar
```

---

### **Method 3: Using VS Code Run Button**

1. **Open** `MoneyManagerApplication.java` in VS Code
2. **Click** the **"Run"** button (▶️) above the `main` method
3. Or **Right-click** in the editor → **"Run Java"**

---

### **Method 4: Using IDE (IntelliJ/Eclipse)**

#### **IntelliJ IDEA:**

1. Right-click on `MoneyManagerApplication.java`
2. Select **"Run 'MoneyManagerApplication'"**

#### **Eclipse:**

1. Right-click on `MoneyManagerApplication.java`
2. Select **"Run As" → "Spring Boot App"**

---

## 🔍 **Verify Application is Running**

Once started, you should see:

- ✅ `Tomcat started on port 8080`
- ✅ `Started MoneyManagerApplication in X.XXX seconds`

### **Test the Application:**

Open your browser or use curl/Postman:

```bash
# Health check
curl http://localhost:8080/api/v1.0/status
# Expected: "Application is running"
```

---

## 🛑 **How to Stop the Application**

### **If running in terminal:**

- Press **`Ctrl + C`** in the terminal

### **If running in VS Code:**

- Click the **"Stop"** button (🔴) in the debug toolbar
- Or press **`Shift + F5`**

### **If port is still in use:**

```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with actual process ID)
taskkill /F /PID <PID>
```

---

## 📋 **Prerequisites Checklist**

Before starting, ensure:

- ✅ **Java 21** is installed

  ```powershell
  java -version
  # Should show: java version "21.x.x"
  ```

- ✅ **MySQL** is running

  ```powershell
  # Check if MySQL service is running
  Get-Service -Name MySQL* | Where-Object {$_.Status -eq "Running"}
  ```

- ✅ **Database exists**

  ```sql
  -- Connect to MySQL and check
  mysql -u root -p
  SHOW DATABASES;
  -- You should see 'moneymanager' database
  ```

- ✅ **Environment variables** are set (if needed)
  - Check `.env` file exists with your credentials
  - Or set them in `application.properties`

---

## 🐛 **Common Startup Issues**

### **1. Port 8080 Already in Use**

```
Web server failed to start. Port 8080 was already in use.
```

**Solution:**

```powershell
# Find and kill the process
netstat -ano | findstr :8080
taskkill /F /PID <PID>
```

### **2. Database Connection Error**

```
Cannot create connection to database
```

**Solution:**

- ✅ Check MySQL is running
- ✅ Verify database credentials in `application.properties` or `.env`
- ✅ Ensure `moneymanager` database exists

### **3. Java Version Mismatch**

```
class file has wrong version
```

**Solution:**

```powershell
# Set correct Java version
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

### **4. Dependencies Not Downloaded**

```
Cannot resolve dependencies
```

**Solution:**

```powershell
# Clean and rebuild
./mvnw clean install
```

---

## 📍 **Application URLs**

Once running, your application is available at:

| Endpoint         | URL                                                 | Auth Required |
| ---------------- | --------------------------------------------------- | ------------- |
| Base URL         | `http://localhost:8080/api/v1.0`                    | -             |
| Health Check     | `http://localhost:8080/api/v1.0/status`             | ❌ No         |
| Register         | `http://localhost:8080/api/v1.0/register`           | ❌ No         |
| Login            | `http://localhost:8080/api/v1.0/login`              | ❌ No         |
| Activate         | `http://localhost:8080/api/v1.0/activate?token=...` | ❌ No         |
| Categories       | `http://localhost:8080/api/v1.0/categories`         | ✅ Yes        |
| Test (Protected) | `http://localhost:8080/api/v1.0/test`               | ✅ Yes        |

---

## 🔥 **Quick Start Commands**

Copy and paste this entire block:

```powershell
# Navigate to project
cd "c:\Users\ASUS TUF X506H\Desktop\My Projects\OnGoing Projects\MoneyManager - Authn"

# Set Java 21
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Clean build
./mvnw clean package -DskipTests

# Run application
./mvnw spring-boot:run
```

---

## 🎯 **Development Workflow**

### **During Development:**

1. **Make code changes**
2. **Stop the application** (Ctrl+C)
3. **Restart** with `./mvnw spring-boot:run`

### **For Faster Restarts:**

Use **Spring Boot DevTools** (already included in your pom.xml):

- Changes in resources are auto-reloaded
- Some Java changes trigger automatic restart

### **Running Tests:**

```powershell
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ProfileServiceTest

# Skip tests during build
./mvnw clean package -DskipTests
```

---

## 📖 **Related Guides**

- **Profile Activation:** See `ACTIVATION_GUIDE.md`
- **Git Issues:** See `QUICK_GIT_FIX.md`
- **Security Setup:** See `GIT_FIX_GUIDE.md`

---

## ✅ **You're Ready!**

Your application should now be running at:

### **http://localhost:8080/api/v1.0**

Test it:

```bash
curl http://localhost:8080/api/v1.0/status
```

Expected response:

```
Application is running
```

**Happy Coding! 🎉**
