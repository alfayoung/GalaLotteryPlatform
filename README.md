# SPEIT Lottery

A full-stack web application for conducting interactive lottery draws and user engagement activities, originally developed for SPEIT (Shanghai Jiao Tong University - ParisTech Elite Institute of Technology) New Year Gala events.

## Features

### User Features
- **SJTU JAccount OAuth2 Authentication** - Secure login using SJTU's unified authentication system
- **Lottery Registration** - Users can register to participate in prize drawings
- **Custom Avatars** - Upload and manage profile pictures
- **Real-time Bullet Screen (Danmaku)** - Live commenting system with WebSocket support
- **Responsive Design** - Optimized interfaces for both desktop and mobile devices
- **User Profile Management** - View and edit personal information

### Admin Features
- **Password-Protected Admin Panel** - Secure administrative access
- **Live Lottery Drawing** - Conduct real-time prize drawings with visual effects
- **Winner Selection** - Randomly select winners from registered participants
- **Prize Management** - Configure prizes and number of winners
- **User Management** - View all registered users and participants
- **Tag Cloud Visualization** - Display participants in an interactive tag cloud

## Tech Stack

### Backend
- **Java 21** - Modern Java with latest features
- **Spring Boot 3.1.5** - Production-ready framework
- **MyBatis** - SQL mapping framework
- **MySQL 8.0+** - Relational database
- **WebSocket** - Real-time bidirectional communication
- **Maven** - Dependency management

### Frontend
- **Vue 3** - Progressive JavaScript framework
- **Vite** - Next-generation build tool
- **Pinia** - State management
- **Vue Router** - Client-side routing
- **Element Plus** - UI component library
- **Axios** - HTTP client
- **Day.js** - Date manipulation

## Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Node.js 16+** and npm
- **MySQL 8.0+**
- **Git**

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/alfayoung/GalaLotteryPlatform
cd GalaLotteryPlatform
```

### 2. Database Setup

1. Create a MySQL database:

```sql
CREATE DATABASE lottery CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Import the schema:

```bash
mysql -u root -p lottery < backend/src/main/resources/config-schema.sql
```

### 3. Backend Configuration

1. Copy the environment template:

```bash
cd backend
cp .env.example .env
```

2. Edit `.env` and configure your settings:

```bash
# Database credentials
DB_USERNAME=root
DB_PASSWORD=your-database-password

# Admin password for the admin panel
ADMIN_PASSWORD=your-secure-admin-password

# SJTU JAccount OAuth2 (register at https://jaccount.sjtu.edu.cn/oauth2/)
OAUTH_CLIENT_ID=your-client-id
OAUTH_CLIENT_SECRET=your-client-secret

# Optional: SSL configuration (for production)
SSL_ENABLED=false
```

3. Build and run the backend:

```bash
# Using Maven Wrapper (recommended)
./mvnw clean install
./mvnw spring-boot:run

# Or using system Maven
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:7500` (or `https://localhost:7500` if SSL is enabled).

### 4. Frontend Configuration

1. Install dependencies:

```bash
cd frontend
npm install
```

2. Copy environment template:

```bash
cp .env.example .env.local
```

3. Edit `.env.local`:

```bash
# For local development
VITE_API_BASE_URL=localhost:7500

# For production, use your domain
# VITE_API_BASE_URL=yourdomain.com:7500
```

4. Run the development server:

```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

### 5. Production Build

**Frontend:**
```bash
cd frontend
npm run build
```

The built files will be in `frontend/dist/`. Copy these to `backend/src/main/resources/static/` for deployment.

**Backend:**
```bash
cd backend
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

## Configuration

### Environment Variables

See [`.env.example`](.env.example) for all available configuration options.

Key configuration files:
- `backend/src/main/resources/application.properties` - Spring Boot configuration
- `backend/src/main/resources/mybatis/mybatis-config.xml` - MyBatis ORM configuration
- `frontend/vite.config.js` - Vite build configuration

### SJTU JAccount OAuth2 Setup

1. Register your application at: https://jaccount.sjtu.edu.cn/oauth2/
2. Set the redirect URI to: `http://localhost:7500/api/jaccountLogin` (adjust for production)
3. Obtain your Client ID and Client Secret
4. Add them to your `.env` file

## API Documentation

### Authentication Endpoints

- `POST /api/checkPassword` - Verify admin password
- `GET /api/jaccountLogin` - OAuth2 callback endpoint
- `GET /api/getCode` - Get OAuth2 authorization code

### Lottery Endpoints

- `POST /api/registerForLottery` - Register user for lottery
- `GET /api/getAllRegistered` - Get all registered participants (admin)
- `POST /api/setAndGetPrizeWinners` - Select and retrieve prize winners (admin)

### User Endpoints

- `GET /api/getAllUser` - Get all users (admin)
- `POST /api/uploadProfileImage` - Upload user avatar
- `GET /api/getProfile` - Get user profile information

### WebSocket

- `/ws` - WebSocket endpoint for real-time bullet screen messages

## Project Structure

```
SPEIT_Lottery/
├── backend/                          # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/lottery/backend/
│   │   │   │   ├── BackendApplication.java    # Main application
│   │   │   │   ├── LotteryController.java     # REST API endpoints
│   │   │   │   ├── database/                  # Data models and DAO
│   │   │   │   ├── login/                     # OAuth2 authentication
│   │   │   │   └── websocket/                 # WebSocket configuration
│   │   │   └── resources/
│   │   │       ├── application.properties     # App configuration
│   │   │       ├── config-schema.sql          # Database schema
│   │   │       └── mybatis/                   # MyBatis mappers
│   │   └── test/                              # Unit tests
│   └── pom.xml                                # Maven dependencies
├── frontend/                         # Vue 3 frontend
│   ├── src/
│   │   ├── components/               # Reusable Vue components
│   │   ├── views/                    # Page components
│   │   ├── router/                   # Vue Router configuration
│   │   ├── stores/                   # Pinia state management
│   │   └── plugins/                  # Plugin configurations
│   ├── public/                       # Static assets
│   └── package.json                  # npm dependencies
├── .env.example                      # Environment template
└── README.md                         # This file
```

## Development

### Running Tests

**Backend:**
```bash
cd backend
./mvnw test
```

**Frontend:**
```bash
cd frontend
npm run test
```

### Code Style

- Backend: Follow standard Java conventions
- Frontend: ESLint configuration included

## Deployment

### Single JAR Deployment

The application can be packaged as a single JAR file with the frontend included:

1. Build the frontend:
```bash
cd frontend
npm run build
```

2. Copy built files to backend static resources:
```bash
cp -r dist/* ../backend/src/main/resources/static/
```

3. Package the backend:
```bash
cd ../backend
./mvnw clean package
```

4. Run the JAR:
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Docker Deployment

(Docker support coming soon - contributions welcome!)

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Developed for SPEIT (Shanghai Jiao Tong University - ParisTech Elite Institute of Technology)
- Uses SJTU JAccount for authentication
- Built with Spring Boot and Vue.js

## Support

For issues, questions, or contributions:
- Open an issue on GitHub
- Check existing documentation in the `docs/` folder

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history and updates.

---

**Note:** This application was originally developed for internal use at SPEIT. When deploying for your own use, make sure to:
1. Register your own OAuth2 application with SJTU JAccount (or adapt the authentication system)
2. Configure all environment variables properly
3. Use secure passwords and secrets
4. Set up proper SSL certificates for production use
