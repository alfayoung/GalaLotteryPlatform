# Contributing to SPEIT Lottery

Thank you for your interest in contributing to SPEIT Lottery! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to maintain a respectful and collaborative environment. We expect all contributors to:

- Be respectful and inclusive
- Welcome newcomers and help them get started
- Accept constructive criticism gracefully
- Focus on what's best for the community
- Show empathy towards other community members

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues to avoid duplicates. When creating a bug report, include:

- **Clear title and description**
- **Steps to reproduce** the issue
- **Expected behavior** vs actual behavior
- **Screenshots** if applicable
- **Environment details** (OS, Java version, Node.js version, browser, etc.)
- **Error messages** or logs

**Example bug report:**

```
Title: Lottery draw fails when no users are registered

Description: When attempting to draw lottery winners with zero registered users,
the application throws an IndexOutOfBoundsException instead of showing a friendly error.

Steps to reproduce:
1. Start the application
2. Log in as admin
3. Click "Start Lottery Draw" without any registered users
4. Observe error

Expected: Show message "No users registered for lottery"
Actual: Application crashes with IndexOutOfBoundsException

Environment:
- OS: Windows 11
- Java: 21.0.1
- Browser: Chrome 120.0
```

### Suggesting Enhancements

Enhancement suggestions are welcome! Please provide:

- **Clear use case** - Why is this enhancement needed?
- **Detailed description** - What should the feature do?
- **Alternative solutions** - Are there other ways to achieve this?
- **Additional context** - Screenshots, mockups, or examples

### Pull Requests

1. **Fork the repository** and create your branch from `main`
2. **Make your changes** following the code style guidelines
3. **Test your changes** thoroughly
4. **Update documentation** if needed
5. **Commit with clear messages** following commit message guidelines
6. **Submit a pull request**

## Development Setup

### Prerequisites

- Java JDK 21+
- Node.js 16+
- MySQL 8.0+
- Git
- Maven (or use included Maven wrapper)

### Setting Up Development Environment

1. **Clone your fork:**
```bash
git clone https://github.com/your-username/SPEIT_Lottery.git
cd SPEIT_Lottery
```

2. **Set up the backend:**
```bash
cd backend
cp .env.example .env
# Edit .env with your local configuration
./mvnw clean install
./mvnw spring-boot:run
```

3. **Set up the frontend:**
```bash
cd frontend
npm install
cp .env.example .env.local
# Edit .env.local with your configuration
npm run dev
```

4. **Set up the database:**
```bash
mysql -u root -p
CREATE DATABASE lottery CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lottery;
SOURCE backend/src/main/resources/config-schema.sql;
```

## Code Style Guidelines

### Java (Backend)

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add JavaDoc comments for public methods and classes
- Keep methods focused and concise (max 50 lines)
- Use Spring Boot best practices
- Handle exceptions appropriately

**Example:**
```java
/**
 * Retrieves all users registered for the lottery.
 *
 * @return List of registered user entities
 * @throws DataAccessException if database query fails
 */
public List<UserEntity> getAllRegisteredUsers() {
    try {
        return mybatisDAO.getAllRegisteredUsers();
    } catch (Exception e) {
        logger.error("Failed to retrieve registered users", e);
        throw new DataAccessException("Could not fetch registered users", e);
    }
}
```

### JavaScript/Vue (Frontend)

- Follow [Vue.js Style Guide](https://vuejs.org/style-guide/)
- Use Composition API for new components
- Use meaningful component and variable names
- Add comments for complex logic
- Keep components focused and reusable
- Use TypeScript types where beneficial

**Example:**
```vue
<script setup>
import { ref, onMounted } from 'vue'

/**
 * Fetches and displays all registered lottery participants
 */
const participants = ref([])
const loading = ref(false)

const fetchParticipants = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/getAllRegistered')
    participants.value = response.data
  } catch (error) {
    console.error('Failed to fetch participants:', error)
    ElMessage.error('Failed to load participants')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchParticipants()
})
</script>
```

### General Guidelines

- **DRY Principle** - Don't Repeat Yourself
- **KISS Principle** - Keep It Simple, Stupid
- **SOLID Principles** - For object-oriented code
- **Responsive Design** - Ensure UI works on mobile and desktop
- **Accessibility** - Follow WCAG guidelines where possible
- **Security** - Never commit secrets or credentials

## Commit Message Guidelines

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, missing semicolons, etc.)
- `refactor`: Code refactoring (no feature changes or bug fixes)
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `build`: Build system or dependency changes
- `ci`: CI/CD configuration changes
- `chore`: Other changes that don't modify src or test files

### Examples

```
feat(lottery): add multi-prize support

Allow admins to configure multiple prize tiers with different
winner counts for each tier.

Closes #123
```

```
fix(auth): handle expired OAuth tokens

Previously, expired tokens caused application crashes.
Now users are redirected to re-authenticate.

Fixes #456
```

```
docs(readme): update setup instructions

Add troubleshooting section for common MySQL connection issues
```

## Testing

### Backend Tests

```bash
cd backend
./mvnw test
```

- Write unit tests for new services and utilities
- Write integration tests for API endpoints
- Aim for >80% code coverage
- Use JUnit 5 and Mockito

### Frontend Tests

```bash
cd frontend
npm run test
```

- Write unit tests for utilities and composables
- Write component tests for complex components
- Test user interactions and edge cases

## Pull Request Process

1. **Update documentation** - README, API docs, comments
2. **Add tests** - Ensure tests pass and add new tests for your changes
3. **Update CHANGELOG.md** - Add your changes under "Unreleased"
4. **Ensure builds succeed** - Run build locally before submitting
5. **Fill out PR template** - Describe what and why
6. **Link related issues** - Use "Closes #123" or "Fixes #456"
7. **Request review** - Tag maintainers if needed
8. **Address feedback** - Respond to review comments promptly

### PR Title Format

```
<type>(<scope>): <description>
```

Example: `feat(websocket): add reconnection logic`

### PR Description Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Testing
Describe how you tested these changes

## Screenshots (if applicable)
Add screenshots for UI changes

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed a self-review
- [ ] I have commented my code where needed
- [ ] I have updated the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix/feature works
- [ ] New and existing tests pass locally
- [ ] Any dependent changes have been merged
```

## Project Structure

Understanding the project structure helps you navigate the codebase:

```
backend/
├── src/main/java/lottery/backend/
│   ├── BackendApplication.java      # Application entry point
│   ├── LotteryController.java       # REST API endpoints
│   ├── WebMvcConfig.java           # Web configuration
│   ├── CommonUtil.java             # Utility functions
│   ├── database/                   # Data layer
│   │   ├── dao/MybatisDAO.java    # Database access
│   │   └── model/                  # Entity classes
│   ├── login/                      # Authentication
│   │   └── JAccountUtil.java      # OAuth2 integration
│   └── websocket/                  # WebSocket support
│       ├── WebSocketConfig.java
│       ├── MyWebSocketHandler.java
│       └── MyHandshakeInterceptor.java

frontend/
├── src/
│   ├── components/                 # Reusable components
│   │   ├── BulletScreen.vue       # Danmaku display
│   │   ├── Head.vue               # Header component
│   │   └── ...
│   ├── views/                      # Page components
│   │   ├── home.vue               # Home page
│   │   ├── cj.vue                 # Lottery draw (desktop)
│   │   ├── cjmobile.vue           # Lottery draw (mobile)
│   │   ├── login.vue              # Admin login
│   │   └── ...
│   ├── router/                     # Routing configuration
│   ├── stores/                     # State management
│   └── plugins/                    # Plugin configs
```

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Vue.js Documentation](https://vuejs.org/)
- [MyBatis Documentation](https://mybatis.org/mybatis-3/)
- [Element Plus Documentation](https://element-plus.org/)

## Getting Help

- **GitHub Issues** - For bugs and feature requests
- **Discussions** - For questions and general discussion
- **Code Review** - Ask for review in your PR

## Recognition

Contributors will be recognized in:
- CHANGELOG.md for significant contributions
- README.md acknowledgments section
- GitHub contributors page

Thank you for contributing to SPEIT Lottery!
