# GitHub Actions Setup for Contributor Validation

## Overview
This setup adds comprehensive GitHub Actions workflows to validate commits from contributors using popular GitHub Marketplace actions.

## Quick Setup Guide

### 1. Update build.gradle.kts
Add these plugins to your `app/build.gradle.kts`:

```kotlin
plugins {
    id("org.owasp.dependencycheck") version "8.4.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

// Add dependency check configuration
dependencyCheck {
    format = "ALL"
    suppressionFile = "dependency-check-suppressions.xml"
    failBuildOnCVSS = 7.0
}

// Add ktlint configuration
ktlint {
    android = true
    ignoreFailures = false
    disabledRules = setOf("import-ordering")
}
```

### 2. Add Required Dependencies
Update your `app/build.gradle.kts` dependencies:

```kotlin
dependencies {
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    // Lint
    lintChecks("com.android.tools.lint:lint-checks:31.3.1")
}
```

### 3. Apply Configuration Files
The following files have been created:
- `.github/workflows/` - Contains all GitHub Actions workflows
- `.editorconfig` - Code formatting configuration
- `app/dependency-check-suppressions.xml` - Security scan suppressions
- `scripts/validate-contribution.sh` - Local validation script

### 4. Enable Workflows
1. Push these changes to your repository
2. Go to GitHub → Settings → Actions → General
3. Ensure "Allow all actions and reusable workflows" is selected
4. Ensure "Read and write permissions" is set for workflows

## Available Workflows

### Android CI (`android-ci.yml`)
- **Builds** the Android application
- **Runs unit tests** and **instrumented tests**
- **Uploads artifacts** for debugging

### Code Quality (`code-quality.yml`)
- **Android lint** for code issues
- **ktlint** for Kotlin formatting
- **Super-linter** for multi-language validation

### Security Scan (`security-scan.yml`)
- **CodeQL** for security vulnerabilities
- **OWASP dependency check** for known vulnerabilities
- **Weekly scheduled scans**

### Dependency Review (`dependency-review.yml`)
- **Vulnerability assessment** for new dependencies
- **License compliance** checking
- **PR-specific analysis**

### PR Validation (`pr-validation.yml`)
- **PR title format** validation (conventional commits)
- **Description quality** checks
- **Sensitive file** detection

## Usage for Contributors

### Before Submitting PR
Run the local validation script:
```bash
./scripts/validate-contribution.sh
```

### PR Requirements
- **Title format**: `type(scope): description`
- **Description**: Must be meaningful (≥10 characters)
- **All checks**: Must pass CI/CD workflows

### Example PR Titles
- `feat(login): add Google Sign-In integration`
- `fix(home): resolve crash on empty data`
- `docs(readme): update setup instructions`

## Monitoring

### Check Workflow Status
1. Go to your repository on GitHub
2. Click the **Actions** tab
3. View workflow runs and results

### Status Badges
Add these to your README.md:

```markdown
![Android CI](https://github.com/[username]/[repo]/workflows/Android%20CI/badge.svg)
![Code Quality](https://github.com/[username]/[repo]/workflows/Code%20Quality%20Check/badge.svg)
![Security Scan](https://github.com/[username]/[repo]/workflows/Security%20Scan/badge.svg)
```

## Troubleshooting

### Common Issues
1. **Build failures**: Check Java version (requires Java 17)
2. **Test failures**: Ensure emulator setup is correct
3. **Lint errors**: Fix reported issues or update configuration
4. **Security warnings**: Review and address vulnerabilities

### Getting Help
- Check workflow logs in GitHub Actions
- Review uploaded artifacts for detailed reports
- Update workflow versions as needed

## Security Considerations
- All workflows use **verified actions** from GitHub Marketplace
- **Minimal permissions** are requested
- **Secrets are not exposed** in logs
- **Dependency scanning** prevents vulnerable packages

## Maintenance
- Update workflow versions periodically
- Review and update suppressions as needed
- Monitor security advisories for used actions
