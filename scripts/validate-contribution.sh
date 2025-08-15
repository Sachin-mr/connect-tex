#!/bin/bash

# Script to validate contributions before submitting PR
# Run this script locally to ensure your changes will pass CI

set -e

echo "🚀 Starting contribution validation..."

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print status
print_status() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
}

# Check if gradlew is executable
if [ ! -x "./gradlew" ]; then
    print_error "gradlew is not executable. Run: chmod +x gradlew"
    exit 1
fi

# Build the project
echo "📦 Building project..."
./gradlew clean build
print_status "Build completed successfully"

# Run lint checks
echo "🔍 Running lint checks..."
./gradlew lint
print_status "Lint checks passed"

# Run unit tests
echo "🧪 Running unit tests..."
./gradlew test
print_status "Unit tests passed"

# Check code formatting
echo "🎨 Checking code formatting..."
if command -v ktlint &> /dev/null; then
    ktlint --android
else
    print_warning "ktlint not found, skipping formatting check"
fi

# Check for sensitive files
echo "🔒 Checking for sensitive files..."
sensitive_files=(
    "*.pem"
    "*.key"
    "*.p12"
    "*.jks"
    "local.properties"
    "google-services.json"
    "keystore.properties"
)

found_sensitive=false
for pattern in "${sensitive_files[@]}"; do
    if find . -name "$pattern" -not -path "./.git/*" -not -path "./build/*" | grep -q .; then
        print_warning "Sensitive file pattern found: $pattern"
        found_sensitive=true
    fi
done

if [ "$found_sensitive" = false ]; then
    print_status "No sensitive files detected"
fi

# Check commit message format (if on a branch)
if [ -n "$(git rev-parse --abbrev-ref HEAD 2>/dev/null)" ]; then
    last_commit=$(git log -1 --pretty=%B)
    if [[ $last_commit =~ ^(feat|fix|docs|style|refactor|test|chore|perf|ci|build)(\(.+\))?:\ .+ ]]; then
        print_status "Commit message format is valid"
    else
        print_warning "Last commit message doesn't follow conventional format"
        echo "Expected format: type(scope): description"
    fi
fi

echo ""
echo "🎉 All validation checks completed!"
echo ""
echo "Your contribution is ready for submission."
echo "Remember to:"
echo "  - Write a clear PR description"
echo "  - Follow conventional commit format for PR title"
echo "  - Ensure all CI checks will pass"
