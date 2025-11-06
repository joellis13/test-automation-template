# Test Automation Template

A modern Java test automation framework using Gradle with the following technology stack:

- **Serenity BDD** - Test reporting and framework
- **Cucumber** - Behavior Driven Development (BDD)
- **Selenium WebDriver** - Browser automation
- **Selenoid** - Remote browser execution in Docker containers
- **WebDriverManager** - Automatic driver management

## Prerequisites

- Java 21 or higher
- Docker and Docker Compose (for Selenoid)
- Gradle (wrapper included)

## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd test-automation-template
```

### 2. Setup Selenoid (Docker-based Browser Grid)

Create a `docker-compose.yml` file in the project root:

```yaml
version: '3.7'
services:
  selenoid:
    image: aerokube/selenoid:latest-release
    container_name: selenoid
    ports:
      - "4444:4444"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ./selenoid:/etc/selenoid:ro
    environment:
      - OVERRIDE_VIDEO_OUTPUT_DIR=./selenoid/video
    command: ["-conf", "/etc/selenoid/browsers.json", "-video-output-dir", "/opt/selenoid/video", "-log-output-dir", "/opt/selenoid/logs"]

  selenoid-ui:
    image: aerokube/selenoid-ui:latest-release
    container_name: selenoid-ui
    ports:
      - "8080:8080"
    command: ["--selenoid-uri", "http://selenoid:4444"]
```

Create the Selenoid configuration directory and browser configuration:

```bash
mkdir -p selenoid
```

Create `selenoid/browsers.json`:

```json
{
  "chrome": {
    "default": "119.0",
    "versions": {
      "119.0": {
        "image": "selenoid/vnc:chrome_119.0",
        "port": "4444",
        "path": "/"
      }
    }
  }
}
```

### 3. Start Selenoid

```bash
docker-compose up -d
```

Verify Selenoid is running by visiting: http://localhost:8080

### 4. Run Tests

Execute all tests:
```bash
./gradlew test
```

Run tests with Serenity reports:
```bash
./gradlew test aggregate
```

View the Serenity reports at: `target/site/serenity/index.html`

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   └── com/jellisisland/test/automation/template/
│   │       ├── pages/              # Page Object classes
│   │       ├── steps/              # Step implementation classes
│   │       ├── stepdefinitions/    # Cucumber step definitions
│   │       └── runners/            # Test runners
│   └── resources/
│       ├── features/               # Cucumber feature files
│       └── serenity.properties     # Serenity configuration
```

## Configuration

### Serenity Properties

The `src/test/resources/serenity.properties` file contains the main configuration:

- **Remote WebDriver**: Configured for Selenoid at `http://localhost:4444/wd/hub`
- **Browser**: Chrome with VNC enabled
- **Timeouts**: Implicit wait and explicit wait timeouts
- **Reports**: Screenshot settings and report encoding

### Browser Configuration

To run tests with different browsers, update the `serenity.properties`:

```properties
# For Firefox
webdriver.remote.driver=firefox
firefox.capabilities.browserName=firefox
firefox.capabilities.version=latest

# For different Chrome versions
chrome.capabilities.version=120.0
```

## Adding New Tests

### 1. Create a Feature File

Add new `.feature` files in `src/test/resources/features/`:

```gherkin
Feature: New Feature
  Scenario: New scenario
    Given I am on a webpage
    When I perform an action
    Then I should see the expected result
```

### 2. Create Step Definitions

Add corresponding step definitions in `src/test/java/.../stepdefinitions/`:

```java
@Given("I am on a webpage")
public void i_am_on_a_webpage() {
    // Implementation
}
```

### 3. Create Page Objects

Add page objects in `src/test/java/.../pages/`:

```java
public class MyPage extends PageObject {
    @FindBy(id = "element-id")
    private WebElement element;
    
    public void performAction() {
        element.click();
    }
}
```

## CI/CD Integration

This template is designed to work with CI/CD pipelines. For CI environments:

1. Ensure Docker is available
2. Start Selenoid before running tests
3. Use the `./gradlew test aggregate` command
4. Archive the Serenity reports from `target/site/serenity/`

Example GitHub Actions workflow:

```yaml
name: Test Automation
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        java-version: '21'
        distribution: 'temurin'
    
    - name: Start Selenoid
      run: docker-compose up -d
    
    - name: Run tests
      run: ./gradlew test aggregate
    
    - name: Archive test reports
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: test-reports
        path: target/site/serenity/
```

## Troubleshooting

### Common Issues

1. **Connection refused to Selenoid**: Ensure Docker containers are running and port 4444 is accessible
2. **Browser not starting**: Check the `browsers.json` configuration and ensure the Docker image is available
3. **Tests timing out**: Adjust timeout values in `serenity.properties`

### Useful Commands

```bash
# Check Selenoid status
curl http://localhost:4444/status

# View running containers
docker ps

# Check Selenoid logs
docker logs selenoid

# Restart Selenoid
docker-compose restart selenoid
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for your changes
4. Ensure all tests pass
5. Submit a pull request
