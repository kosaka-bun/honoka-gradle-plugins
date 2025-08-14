# 更新日志

## 1.0.2
#### 工程
- 更新Gradle版本为9.0.0。

#### honoka-gradle-utils 1.0.2
- 添加了一些常用的DSL。

#### honoka-basic-plugin 1.0.2
- 优化了部分代码结构。
- 修正了`checkVersionOfProjects`任务中存在的问题。
- 新增了`kotlinBom`与`springBootBom`的快速依赖配置。

#### honoka-android-plugin 1.0.0
- 起始版本。在honoka-basic-plugin的基础上扩展实现了`kotlinAndroid`快速依赖配置，以及aar包默认发布配置。

## 1.0.1
#### 工程
- 更新Gradle版本为8.13。

#### honoka-gradle-utils 1.0.1
- 为`DependencyHandler`添加了一些扩展函数。
- 新增了`DslContainer`类与`defineDsl`扩展函数，用于快速定义多级DSL。

#### honoka-basic-plugin 1.0.1
- 重构项目，弃用了`GlobalData`与`BuildFinishedListener`，全部改为使用`defineDsl`函数创建自定义DSL。
- `DependenciesExt`获取版本号时使用的`key`增加了前缀，并新增了`lombok`函数。
- `PublishingExt`中的`checkVersionOfProjects`任务改为通过判断一个项目是否存在`publication`，来判断是否为需要发布的项目。

## 1.0.0
#### honoka-gradle-utils 1.0.0
- 起始版本。提供Maven发布快速配置、版本号检查任务、读取`libs.versions.toml`中的版本号以快速引入依赖等功能。

#### honoka-basic-plugin 1.0.0
- 起始版本。提供用于Gradle插件开发的一些常用DSL、全局变量管理器、构建完成监听器等。
