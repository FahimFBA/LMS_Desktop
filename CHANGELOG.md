# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project follows [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2026-06-23

### Added

- Add GitHub Actions release automation that publishes GitHub Releases from changelog entries.

### Changed

- Replace the deprecated system-scoped MySQL connector dependency with the maintained Maven coordinate.
- Update Maven dependency versions for MySQL Connector/J, Log4j, Apache POI, JUnit, and JavaFX.
- Declare Maven dependencies for JavaFX Media and Apache POI OOXML modules used by the application.

### Security

- Fix Dependabot alerts for vulnerable MySQL Connector/J, Log4j Core, and Apache POI OOXML versions.
