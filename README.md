# IMG2ASCII - Image to ASCII Art Converter

A Java application that converts images to ASCII art using various design patterns.

## Project Structure

```
IMG2ASCII/
├── src/
│   ├── main/
│   │   └── java/              # Production source code
│   │       ├── converter/     # ASCII conversion logic (Strategy + Builder)
│   │       ├── loader/        # Image loading (Dependency Injection)
│   │       ├── model/         # Data models
│   │       ├── observer/      # Observer pattern implementation
│   │       ├── ramp/          # ASCII ramp factory pattern
│   │       ├── singleton/     # Configuration singleton
│   │       ├── strategy/      # Scaling strategies
│   │       └── ui/            # Swing UI
│   └── test/
│       └── java/              # Test source code (mirrors main structure)
├── build.gradle               # Gradle build configuration
└── settings.gradle            # Gradle settings
```

## Design Patterns Implemented

1. **Strategy Pattern**: Different image scaling strategies (Nearest Neighbor, Bilinear Interpolation)
2. **Observer Pattern**: Conversion progress notifications
3. **Builder Pattern**: Fluent API for creating ASCII converters
4. **Factory Pattern**: Creating different ASCII ramp types
5. **Singleton Pattern**: Application configuration management

## Building and Running

### Using IntelliJ IDEA

1. **Open the project** in IntelliJ IDEA
2. **Reload Gradle project**: Right-click on `build.gradle` → "Load Gradle Project"
3. **Run the application**: 
   - Right-click on `AppRunner.java` → Run
   - Or use Gradle: `gradle run` or `./gradlew run`
4. **Run tests**: 
   - Right-click on `src/test/java` → "Run All Tests"
   - Or use Gradle: `gradle test` or `./gradlew test`

### Using Gradle Command Line

```bash
# Windows
gradlew.bat test          # Run all tests
gradlew.bat build         # Build the project
gradlew.bat run           # Run the application

# Linux/Mac
./gradlew test
./gradlew build
./gradlew run
```

### Using IntelliJ Built-in Build

1. **Build** → Build Project (Ctrl+F9)
2. **Run** → Run 'AppRunner' (Shift+F10)
3. **Run** → Run All Tests (Ctrl+Shift+F10 on test directory)

## Running Tests

All tests are located in `src/test/java/` and organized by component:

- `converter/` - Tests for ASCII converter and builder
- `integration/` - Integration tests demonstrating all patterns working together
- `observer/` - Observer pattern tests
- `ramp/` - Factory pattern and ramp implementation tests
- `singleton/` - Singleton pattern tests
- `strategy/` - Strategy pattern tests

### Test Command
```bash
# Run all tests with detailed output
gradlew test

# Run specific test class
gradlew test --tests converter.AsciiConverterTest

# Run tests with coverage (if configured)
gradlew test jacocoTestReport
```

## Requirements

- Java 11 or higher
- Gradle 8.5+ (wrapper included)
- JUnit 5.9.3 (automatically downloaded by Gradle)

## Features

- ✅ Load images (JPG, PNG, GIF, BMP)
- ✅ Convert to ASCII art with multiple character ramps
- ✅ Adjustable scaling and quality
- ✅ Real-time progress tracking
- ✅ Persistent user preferences
- ✅ Graphical user interface
- ✅ Comprehensive unit tests

## Usage

1. Launch the application
2. Click "Load Image" to select an image file
3. Adjust settings:
   - **Ramp Type**: Choose character set (Simple, Detailed, Blocks)
   - **Scaling Strategy**: Choose interpolation method
   - **Scale**: Adjust output size (10-100%)
4. Click "Convert to ASCII" to generate art
5. View results in the output panel

Settings are automatically saved and restored between sessions.
