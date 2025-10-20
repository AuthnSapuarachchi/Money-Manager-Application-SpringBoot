# 🔧 Git Secret Removal & Push Fix Guide

## Problem

GitHub blocked your push because it detected secrets (Brevo/Sendinblue API key) in your code.

## ✅ Solution - Complete Steps

### Step 1: Remove Secrets from Git History

Since the secrets are already in your Git history, you need to remove them:

```powershell
# WARNING: This rewrites Git history. Make sure you have a backup!

# Remove the secret from all commits
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch src/main/resources/application.properties" \
  --prune-empty --tag-name-filter cat -- --all

# Or use git-filter-repo (recommended, faster)
# First install: pip install git-filter-repo
# Then run:
git filter-repo --path src/main/resources/application.properties --invert-paths
```

### Step 2: Clean Up Git References

```powershell
# Remove the backup refs
git for-each-ref --format='delete %(refname)' refs/original | git update-ref --stdin

# Garbage collect
git reflog expire --expire=now --all
git gc --prune=now --aggressive
```

### Step 3: Stage Your Fixed Files

```powershell
# Add the updated files
git add .gitignore
git add src/main/resources/application.properties
git add .env.example
git add ACTIVATION_GUIDE.md

# Commit the changes
git commit -m "Security: Remove hardcoded secrets and use environment variables"
```

### Step 4: Set Upstream and Force Push

```powershell
# Set upstream tracking
git branch --set-upstream-to=origin/main main

# Force push (this will overwrite remote history)
git push -u origin main --force
```

## ⚠️ EASIER ALTERNATIVE: Start Fresh (Recommended)

If the above is too complex, here's an easier approach:

### Option A: Delete and Re-create Repository

1. **On GitHub**: Delete the repository `Money-Manager-Application-SpringBoot`
2. **Create a new repository** with the same name
3. **Push fresh code**:

```powershell
# Remove git history
rm -rf .git

# Initialize new repo
git init
git add .
git commit -m "Initial commit with secure configuration"

# Add remote and push
git remote add origin https://github.com/AuthnSapuarachchi/Money-Manager-Application-SpringBoot.git
git branch -M main
git push -u origin main
```

### Option B: Use BFG Repo-Cleaner (Easiest)

1. **Download BFG**: https://rtyley.github.io/bfg-repo-cleaner/
2. **Run**:

```powershell
# Clone a fresh copy
cd ..
git clone --mirror https://github.com/AuthnSapuarachchi/Money-Manager-Application-SpringBoot.git

# Clean secrets
java -jar bfg.jar --replace-text passwords.txt Money-Manager-Application-SpringBoot.git

# Push cleaned repo
cd Money-Manager-Application-SpringBoot.git
git reflog expire --expire=now --all && git gc --prune=now --aggressive
git push
```

## 🎯 Quick Fix (What I Did for You)

I've already:

- ✅ Updated `application.properties` to use environment variables
- ✅ Created `.env` file with your actual secrets (NOT committed)
- ✅ Created `.env.example` template (safe to commit)
- ✅ Updated `.gitignore` to ignore `.env` files
- ✅ Removed secrets from `ACTIVATION_GUIDE.md`

## 📝 Next Steps

1. **Choose one approach above** to clean Git history
2. **Verify `.env` exists** with your actual credentials
3. **Never commit `.env`** - it's in `.gitignore`
4. **For production**: Use GitHub Secrets or environment variables in your deployment platform

## 🔐 Environment Variables Setup

### For Local Development:

Your `.env` file is already created with your secrets.

### For Production:

- **Heroku**: Set config vars in dashboard
- **AWS**: Use Parameter Store or Secrets Manager
- **Azure**: Use App Configuration
- **Docker**: Use docker-compose env_file or secrets

## ✅ Verification

After fixing, verify:

```powershell
# Check no secrets in code
git log --all --full-history -- "*application.properties"

# Verify .env is ignored
git status

# .env should NOT appear in untracked files
```

---

**Important**: After cleaning Git history, you MUST force push. Coordinate with team members if any!
