
`com.android.application.properties`文件内容

```
implementation-class=com.android.build.gradle.AppPlugin
```

这里定义了入口是 AppPlugin，AppPlugin 继承自 BasePlugin。大部分工作还是在 BasePlugin 里做的。


BasePlugin 的 apply 方法。
```java
@Override
public final void apply(@NonNull Project project) {
    CrashReporting.runAction(
        () - > {
            basePluginApply(project);
            pluginSpecificApply(project);
        });
}
```

```java
private void basePluginApply(@NonNull Project project) {
    // We run by default in headless mode, so the JVM doesn't steal focus.
    System.setProperty("java.awt.headless", "true");
    this.project = project;
    this.projectOptions = new ProjectOptions(project);
    checkGradleVersion(project, getLogger(), projectOptions);
    DependencyResolutionChecks.registerDependencyCheck(project, projectOptions);
    //应用 AndroidBasePlugin
    project.getPluginManager().apply(AndroidBasePlugin.class);
    checkPathForErrors();
    checkModulesForErrors();
    PluginInitializer.initialize(project);
    RecordingBuildListener buildListener = ProfilerInitializer.init(project, projectOptions);
    ProfileAgent.INSTANCE.register(project.getName(), buildListener);
    threadRecorder = ThreadRecorder.get();
    Workers.INSTANCE.initFromProject(
        projectOptions,
        // possibly, in the future, consider using a pool with a dedicated size
        // using the gradle parallelism settings.
        ForkJoinPool.commonPool());
    ProcessProfileWriter.getProject(project.getPath())
        .setAndroidPluginVersion(Version.ANDROID_GRADLE_PLUGIN_VERSION)
        .setAndroidPlugin(getAnalyticsPluginType())
        .setPluginGeneration(GradleBuildProject.PluginGeneration.FIRST)
        .setOptions(AnalyticsUtil.toProto(projectOptions));
    if(!projectOptions.get(BooleanOption.ENABLE_NEW_DSL_AND_API)) {
        // 配置项目 configureProject
        threadRecorder.record(
            ExecutionType.BASE_PLUGIN_PROJECT_CONFIGURE,
            project.getPath(),
            null,
            this::configureProject);
        // 配置 Extension
        threadRecorder.record(
            ExecutionType.BASE_PLUGIN_PROJECT_BASE_EXTENSION_CREATION,
            project.getPath(),
            null,
            this::configureExtension);
        //创建任务
        threadRecorder.record(
            ExecutionType.BASE_PLUGIN_PROJECT_TASKS_CREATION,
            project.getPath(),
            null,
            this::createTasks);
    } else {
        // Apply the Java plugin
        project.getPlugins().apply(JavaBasePlugin.class);
        // create the delegate
        ProjectWrapper projectWrapper = new ProjectWrapper(project);
        PluginDelegate < E > delegate =
            new PluginDelegate < > (
                project.getPath(),
                project.getObjects(),
                project.getExtensions(),
                project.getConfigurations(),
                projectWrapper,
                projectWrapper,
                project.getLogger(),
                projectOptions,
                getTypedDelegate());
        delegate.prepareForEvaluation();
        // after evaluate callbacks
        project.afterEvaluate(
            CrashReporting.afterEvaluate(
                p - > {
                    threadRecorder.record(
                        ExecutionType.BASE_PLUGIN_CREATE_ANDROID_TASKS,
                        p.getPath(),
                        null,
                        delegate::afterEvaluate);
                }));
    }
}
```

AbstractAppPlugin 实现了 BasePlugin 的 createExtension 方法。 在这里创建了 我们在app的 build.gradle `android{}` 这个方法块。
```java
@NonNull
@Override
protected BaseExtension createExtension(@NonNull Project project, @NonNull ProjectOptions projectOptions, @
    NonNull GlobalScope globalScope, @NonNull NamedDomainObjectContainer < BuildType > buildTypeContainer, @
    NonNull NamedDomainObjectContainer < ProductFlavor > productFlavorContainer, @NonNull NamedDomainObjectContainer <
    SigningConfig > signingConfigContainer, @NonNull NamedDomainObjectContainer < BaseVariantOutput >
    buildOutputs, @NonNull SourceSetManager sourceSetManager, @NonNull ExtraModelInfo extraModelInfo) {
    return project.getExtensions()
        .create(
            "android",
            getExtensionClass(),
            project,
            projectOptions,
            globalScope,
            buildTypeContainer,
            productFlavorContainer,
            signingConfigContainer,
            buildOutputs,
            sourceSetManager,
            extraModelInfo,
            isBaseApplication);
}
```