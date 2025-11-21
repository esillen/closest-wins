# Deployment Guide - Fly.io

## Prerequisites

1. Install Fly.io CLI:
   ```bash
   # macOS
   brew install flyctl
   
   # Or using curl
   curl -L https://fly.io/install.sh | sh
   ```

2. Sign up for Fly.io account:
   ```bash
   flyctl auth signup
   # Or login if you already have an account:
   flyctl auth login
   ```

## Initial Deployment

1. **Launch your app** (first time only):
   ```bash
   cd /Users/erik/gitstuff/closest-wins
   flyctl launch
   ```
   
   When prompted:
   - Use the app name from fly.toml: **Yes**
   - Copy configuration to new app: **Yes**
   - Would you like to set up a PostgreSQL database: **No** (not needed)
   - Would you like to set up an Upstash Redis database: **No** (not needed)
   - Would you like to deploy now: **Yes**

2. **Set admin password** (important for security):
   ```bash
   flyctl secrets set ADMIN_PASSWORD=your-secure-password-here
   ```

3. **Deploy the app**:
   ```bash
   flyctl deploy
   ```

## Your App URL

After deployment, your app will be available at:
```
https://closest-wins.fly.dev
```

## Useful Commands

### View logs:
```bash
flyctl logs
```

### Check app status:
```bash
flyctl status
```

### Open your app in browser:
```bash
flyctl open
```

### SSH into your app:
```bash
flyctl ssh console
```

### Redeploy after changes:
```bash
flyctl deploy
```

### Set/update secrets:
```bash
flyctl secrets set ADMIN_PASSWORD=new-password
```

### View secrets (only shows names, not values):
```bash
flyctl secrets list
```

### Scale your app:
```bash
# Scale to 2 machines for redundancy
flyctl scale count 2

# Scale memory
flyctl scale memory 1024
```

## Regions

Change the region in fly.toml if needed. Common regions:
- `iad` - US East (Virginia)
- `lax` - US West (Los Angeles)
- `lhr` - London
- `fra` - Frankfurt
- `nrt` - Tokyo
- `syd` - Sydney

See all regions: `flyctl platform regions`

## Costs

Fly.io free tier includes:
- Up to 3 shared-cpu-1x VMs with 256MB RAM
- 160GB outbound data transfer
- Your app (1 VM, 512MB) is within free tier limits

## Monitoring

View real-time metrics:
```bash
flyctl dashboard
```

Or visit: https://fly.io/dashboard

## Troubleshooting

### App won't start:
```bash
flyctl logs
flyctl status
```

### WebSocket issues:
Fly.io supports WebSockets by default, but check logs if you see connection errors.

### Need to restart:
```bash
flyctl apps restart closest-wins
```

## Updating Your App

1. Make code changes locally
2. Commit to git (optional but recommended)
3. Deploy:
   ```bash
   flyctl deploy
   ```

That's it! Your game will be live at https://closest-wins.fly.dev 🚀

