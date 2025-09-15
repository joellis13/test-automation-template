#!/bin/bash

echo "Setting up Test Automation Template..."

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Error: Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "Error: Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "Pulling Selenoid browser images..."
docker pull selenoid/vnc:chrome_119.0
docker pull selenoid/vnc:chrome_120.0
docker pull selenoid/vnc:firefox_119.0

echo "Starting Selenoid..."
docker-compose up -d

echo "Waiting for Selenoid to start..."
sleep 10

# Check if Selenoid is running
if curl -s http://localhost:4444/status > /dev/null; then
    echo "✅ Selenoid is running successfully!"
    echo "🌐 Selenoid UI available at: http://localhost:8080"
    echo "🔗 Selenoid Hub available at: http://localhost:4444/wd/hub"
else
    echo "❌ Selenoid failed to start. Check Docker logs:"
    docker-compose logs selenoid
    exit 1
fi

echo ""
echo "Setup complete! You can now run tests with:"
echo "  ./gradlew test"
echo ""
echo "To view test reports after running tests:"
echo "  ./gradlew aggregate"
echo "  Then open: target/site/serenity/index.html"