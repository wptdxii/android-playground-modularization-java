# Playground

A sample Android app which showcases advanced usage of some open source libraries with Java.

# Build

## Channel Apk

Run command:

windows:

```cmd
.\gradlew.bat build channelProdRelease
```

Linus & macOS:

```bash
$ ./gradlew build channelProdRelease
```

Ref:

* [VasDolly](https://github.com/Tencent/VasDolly)

## DependencyUpdates

Displays a report of the project dependencies that are up-to-date:

windows:

```cmd
.\gradlew.bat dependencyUpdates -Drevision=release
```

Linux & macOS:

```bash
$ ./gradlew dependencyUpdates -Drevision=release
```

Ref:

* [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin)
