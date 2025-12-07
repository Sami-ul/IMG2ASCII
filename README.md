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
