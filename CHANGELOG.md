# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- Externalized all configuration to environment variables
- Updated .gitignore to exclude sensitive files and build artifacts
- Improved documentation for open source release

### Added
- Comprehensive README.md with setup instructions
- MIT License
- Contributing guidelines (CONTRIBUTING.md)
- Environment variable templates (.env.example)
- SSL setup documentation (docs/SSL_SETUP.md)
- This changelog

### Security
- Removed all hardcoded secrets from repository
- Removed SSL certificates and passwords from version control
- Added security best practices documentation

## [1.0.0] - 2024-12-09

### Added
- SJTU JAccount OAuth2 authentication
- User registration and profile management
- Avatar upload functionality
- Lottery registration system
- Admin panel with password authentication
- Live lottery drawing with visual effects
- Prize winner selection
- Real-time bullet screen (danmaku) with WebSocket
- Tag cloud visualization for participants
- Responsive design for mobile and desktop
- HTTPS/WSS support with SSL certificates
- MySQL database integration with MyBatis ORM
- REST API for all operations
- Vue 3 frontend with Vite build system
- Spring Boot 3.1.5 backend with Java 21

### Features Completed
- Admin login system
- User JAccount OAuth2 login
- Lottery registration and drawing
- Bullet screen integration (desktop and mobile)
- User profile and avatar management
- Vote/feedback system integration
- Mobile-responsive lottery interface
- User list management
- WebSocket real-time communication
- Single JAR deployment support

### Fixed
- Index out of range error in lottery drawing
- UI rendering issues on profile page exit

## Project History

This project was developed for the SPEIT (Shanghai Jiao Tong University - ParisTech Elite Institute of Technology) New Year Gala. The initial development focused on creating an interactive lottery system with real-time engagement features.

### Development Timeline

- **2023-12-09**: Fixed lottery index out of range bug
- **2023-12-08**: Added HTTPS and WSS support, completed deployment setup
- **2023-12-07**: Compressed images for better performance
- **2023-12-06**: Java packaging tests
- **2023-12-05**: Packaged entire project into single JAR file
- **2023-11-29**: Completed login interface (admin and user authentication)

---

## Version History Notes

### Version Numbering
- Major version (X.0.0): Breaking changes or major feature releases
- Minor version (0.X.0): New features, backward compatible
- Patch version (0.0.X): Bug fixes, backward compatible

### Categories
- **Added**: New features
- **Changed**: Changes to existing functionality
- **Deprecated**: Features that will be removed in future versions
- **Removed**: Removed features
- **Fixed**: Bug fixes
- **Security**: Security-related changes
