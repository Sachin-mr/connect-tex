# GitHub Actions Workflows

This directory contains GitHub Actions workflows for validating commits from contributors and ensuring code quality.

## Workflows Overview

### 1. Android CI (`android-ci.yml`)
- **Purpose**: Main continuous integration pipeline
- **Triggers**: Push to main/develop branches, pull requests
- **Actions**:
  - Builds the Android application
  - Runs unit tests
  - Runs Android instrumented tests
  - Uploads build artifacts

### 2. Code Quality (`code-quality.yml`)
- **Purpose**: Ensures code quality and style consistency
- **Triggers**: Push to main/develop branches, pull requests
- **Actions**:
  - Runs Android lint
  - Runs ktlint for Kotlin formatting
  - Super-linter for multi-language validation

### 3. Security Scan (`security-scan.yml`)
- **Purpose**: Security vulnerability detection
- **Triggers**: Push, pull requests, weekly scheduled runs
- **Actions**:
  - CodeQL analysis
  - Dependency vulnerability scanning
  - Security report generation

### 4. Dependency Review (`dependency-review.yml`)
- **Purpose**: Validates dependencies for security and licensing
- **Triggers**: Pull requests
- **Actions**:
  - Dependency vulnerability check
  - License compliance validation

### 5. PR Validation (`pr-validation.yml`)
- **Purpose**: Validates pull request quality
- **Triggers**: Pull requests
- **Actions**:
  - PR title validation
  - Description quality check
  - Sensitive file detection

## Setup Instructions

### Prerequisites
1. Ensure your repository has GitHub Actions enabled
2. Make sure you have the required secrets configured:
   - `GITHUB_TOKEN` (automatically provided)

### Required Plugins
Add these plugins to your `app/build.gradle.kts`:

```kotlin
plugins {
    id("org.owasp.dependencycheck") version "8.4.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}
```

### Required Dependencies
Add these dependencies to your `app/build.gradle.kts`:

```kotlin
dependencies {
    // Lint tools
    lintChecks("com.android.tools.lint:lint-checks:31.3.1")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## Usage

### For Contributors
1. **Before submitting PR**:
   - Ensure all tests pass locally
   - Run `./gradlew lint` to check code style
   - Follow conventional commit format for PR titles

2. **PR Requirements**:
   - PR title must follow conventional commit format
   - PR description must be meaningful
   - All CI checks must pass

### For Maintainers
1. **Review PRs**:
   - Check all workflow results
   - Review security scan reports
   - Verify dependency changes

## Workflow Status Badges

Add these badges to your README.md:

```markdown
![Android CI](https://github.com/[username]/[repo]/workflows/Android%20CI/badge.svg)
![Code Quality](https://github.com/[username]/[repo]/workflows/Code%20Quality%20Check/badge.svg)
![Security Scan](https://github.com/[username]/[repo]/workflows/Security%20Scan/badge.svg)
```

## Troubleshooting

### Common Issues
1. **Build failures**: Check Java version compatibility
2. **Test failures**: Ensure emulator setup is correct
3. **Lint errors**: Fix reported issues or update lint configuration
4. **Security warnings**: Review and address reported vulnerabilities

### Support
- Check workflow logs in GitHub Actions tab
- Review artifact uploads for detailed reports
- Update workflow versions as needed
