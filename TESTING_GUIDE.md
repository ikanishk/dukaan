# Testing Guide - Dukaan Application

## Current Status
- ✅ Backend: Running on port 8081
- ✅ Frontend: Running on port 3000
- ✅ Database: H2 in-memory with seeded data

## Step-by-Step Testing

### 1. Test Backend API Directly

Open PowerShell and run:

```powershell
# Test login endpoint
$response = Invoke-RestMethod -Uri 'http://localhost:8081/api/auth/login' `
  -Method POST `
  -Headers @{'Content-Type'='application/json'} `
  -Body '{"username":"admin","password":"admin123"}'

# Display the response
$response

# Save the token
$token = $response.token
Write-Host "Token: $token"

# Test dashboard endpoint with token
$dashboardData = Invoke-RestMethod -Uri 'http://localhost:8081/api/dashboard' `
  -Method GET `
  -Headers @{'Authorization'="Bearer $token"}

# Display dashboard data
$dashboardData
```

**Expected Output:**
- Login should return a JWT token and user info
- Dashboard should return metrics (all zeros initially since no data exists)

### 2. Test Frontend Login

1. Open browser: http://localhost:3000
2. You'll be redirected to http://localhost:3000/login
3. Enter credentials:
   - Username: `admin`
   - Password: `admin123`
4. Click "Sign in"
5. You should be redirected to http://localhost:3000/dashboard

### 3. Check Browser Console

Open browser DevTools (F12) and check:

**Console Tab:**
- Look for any errors
- Check API requests to http://localhost:8081/api

**Network Tab:**
- Check if `/api/auth/login` returns 200
- Check if `/api/dashboard` returns 200
- Verify Authorization header is being sent

### 4. Common Issues & Solutions

#### Issue: "Cannot connect to backend"
**Solution:**
```powershell
# Check if backend is running
netstat -ano | findstr :8081

# If not running, start it:
cd C:\Users\AL84861\Downloads\Dukaan\backend
java -jar target/dukaan-backend-1.0.0.jar --spring.profiles.active=h2 --server.port=8081
```

#### Issue: "Login fails with 401"
**Solution:**
- Verify credentials: admin/admin123
- Check backend logs for errors
- Ensure H2 database has user data

#### Issue: "Dashboard shows error"
**Solution:**
- Login first at /login
- Check browser console for errors
- Verify token is stored in localStorage
- Check Network tab for failed requests

#### Issue: "CORS errors"
**Solution:**
- Backend CORS is configured for http://localhost:3000
- Ensure frontend is running on port 3000
- Check backend SecurityConfig.java

### 5. Verify Data in H2 Console

1. Open: http://localhost:8081/h2-console
2. Connection settings:
   - JDBC URL: `jdbc:h2:mem:dukaan`
   - Username: `sa`
   - Password: (empty)
3. Click "Connect"
4. Run query:
```sql
SELECT * FROM users;
```

**Expected Result:**
Two users should be visible:
- admin (ADMIN role)
- staff (STAFF role)

### 6. Test API Endpoints

#### Create a Device
```powershell
$token = "YOUR_JWT_TOKEN_HERE"

$deviceData = @{
    imei = "123456789012345"
    model = "iPhone 13"
    brand = "Apple"
    color = "Blue"
    storage = "128GB"
    ram = "4GB"
    supplierId = 1
    purchasePrice = 45000
    condition = "Excellent"
    accessories = "Charger, Box"
    notes = "Test device"
} | ConvertTo-Json

Invoke-RestMethod -Uri 'http://localhost:8081/api/devices' `
  -Method POST `
  -Headers @{
      'Authorization'="Bearer $token"
      'Content-Type'='application/json'
  } `
  -Body $deviceData
```

#### Get Dashboard Data
```powershell
Invoke-RestMethod -Uri 'http://localhost:8081/api/dashboard' `
  -Method GET `
  -Headers @{'Authorization'="Bearer $token"}
```

### 7. Debug Frontend Issues

#### Check localStorage
Open browser console and run:
```javascript
// Check if token exists
console.log(localStorage.getItem('token'))

// Check if user exists
console.log(localStorage.getItem('user'))

// Check auth state
console.log(localStorage.getItem('auth-storage'))
```

#### Clear and retry
```javascript
// Clear all storage
localStorage.clear()

// Reload page
location.reload()
```

### 8. Expected Behavior

**After Login:**
1. Token stored in localStorage
2. User redirected to /dashboard
3. Dashboard makes API call to /api/dashboard
4. Dashboard displays metrics (initially all zeros)
5. No errors in console

**Dashboard Data Structure:**
```json
{
  "totalDevices": 0,
  "devicesInStock": 0,
  "devicesSold": 0,
  "devicesUnderRepair": 0,
  "totalSalesToday": 0,
  "totalSalesThisMonth": 0,
  "revenueToday": 0,
  "revenueThisMonth": 0,
  "profitToday": 0,
  "profitThisMonth": 0,
  "expensesToday": 0,
  "expensesThisMonth": 0,
  "netProfitToday": 0,
  "netProfitThisMonth": 0,
  "returnsToday": 0,
  "returnsThisMonth": 0
}
```

### 9. Quick Health Check

Run this complete test:

```powershell
# 1. Test backend health
try {
    $health = Invoke-WebRequest -Uri 'http://localhost:8081/h2-console' -UseBasicParsing
    Write-Host "✅ Backend is running" -ForegroundColor Green
} catch {
    Write-Host "❌ Backend is NOT running" -ForegroundColor Red
}

# 2. Test frontend health
try {
    $frontend = Invoke-WebRequest -Uri 'http://localhost:3000' -UseBasicParsing
    Write-Host "✅ Frontend is running" -ForegroundColor Green
} catch {
    Write-Host "❌ Frontend is NOT running" -ForegroundColor Red
}

# 3. Test login
try {
    $login = Invoke-RestMethod -Uri 'http://localhost:8081/api/auth/login' `
        -Method POST `
        -Headers @{'Content-Type'='application/json'} `
        -Body '{"username":"admin","password":"admin123"}'
    Write-Host "✅ Login successful - Token received" -ForegroundColor Green
    Write-Host "Token: $($login.token.Substring(0,20))..." -ForegroundColor Cyan
} catch {
    Write-Host "❌ Login failed" -ForegroundColor Red
    Write-Host $_.Exception.Message
}
```

### 10. Next Steps

Once login works:
1. Test creating a device
2. Test creating a sale
3. Test dashboard updates
4. Add more test data
5. Explore other features

---

## Troubleshooting Checklist

- [ ] Backend running on port 8081
- [ ] Frontend running on port 3000
- [ ] H2 console accessible
- [ ] Users exist in database
- [ ] Login returns JWT token
- [ ] Token stored in localStorage
- [ ] Dashboard API call succeeds
- [ ] No CORS errors
- [ ] No console errors

If all checked, the system is working correctly!
