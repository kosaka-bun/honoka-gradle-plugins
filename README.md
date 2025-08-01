# Honoka Gradle Plugins
![Gradle](https://img.shields.io/badge/Gradle-8.13-brightgreen?logo=Gradle)
[![License](https://img.shields.io/github/license/kosaka-bun/honoka-gradle-plugins?label=License&color=blue&logo=GitHub)](./LICENSE)
![GitHub Stars](https://img.shields.io/github/stars/kosaka-bun/honoka-gradle-plugins?label=Stars&logo=GitHub&style=flat)
[![Release](https://img.shields.io/github/release/kosaka-bun/honoka-gradle-plugins?label=Release&logo=GitHub)](../../releases)

## 简介
Java与Kotlin项目Gradle快速配置插件集（仅适用于`build.gradle.kts`）。

本项目采用Apache-2.0 License，使用本项目时，请遵守此开源许可证的相关规定。

**本项目中的所有代码并未经过严格测试，请勿用于生产环境。**

请参阅：[更新日志](./docs/changelog.md)

## 使用
本项目部署于：

[![maven-repo](https://github-readme-stats.vercel.app/api/pin/?username=honoka-studio&repo=maven-repo)](https://github.com/honoka-studio/maven-repo)

使用前请先阅读此仓库的文档，为你的Gradle添加依赖仓库。

各模块版本号请前往[Releases](../../releases)查看。

#### Kotlin DSL
```kotlin
plugins {
    id("de.honoka.gradle.plugin.basic") version "版本号"
}
```
