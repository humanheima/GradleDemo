
```
ReplaceAssetsPlugin : all task ready
ReplaceAssetsPlugin : whenReady name = preBuild , path = :app:preBuild
ReplaceAssetsPlugin : whenReady name = preDebugBuild , path = :app:preDebugBuild
ReplaceAssetsPlugin : whenReady name = compileDebugAidl , path = :app:compileDebugAidl
ReplaceAssetsPlugin : whenReady name = compileDebugRenderscript , path = :app:compileDebugRenderscript
ReplaceAssetsPlugin : whenReady name = generateDebugBuildConfig , path = :app:generateDebugBuildConfig
ReplaceAssetsPlugin : whenReady name = javaPreCompileDebug , path = :app:javaPreCompileDebug
ReplaceAssetsPlugin : whenReady name = checkDebugAarMetadata , path = :app:checkDebugAarMetadata
ReplaceAssetsPlugin : whenReady name = generateDebugResValues , path = :app:generateDebugResValues
ReplaceAssetsPlugin : whenReady name = generateDebugResources , path = :app:generateDebugResources
ReplaceAssetsPlugin : whenReady name = mergeDebugResources , path = :app:mergeDebugResources
ReplaceAssetsPlugin : whenReady name = createDebugCompatibleScreenManifests , path = :app:createDebugCompatibleScreenManifests
ReplaceAssetsPlugin : whenReady name = extractDeepLinksDebug , path = :app:extractDeepLinksDebug
ReplaceAssetsPlugin : whenReady name = processDebugMainManifest , path = :app:processDebugMainManifest
ReplaceAssetsPlugin : whenReady name = processDebugManifest , path = :app:processDebugManifest
ReplaceAssetsPlugin : whenReady name = processDebugManifestForPackage , path = :app:processDebugManifestForPackage
ReplaceAssetsPlugin : whenReady name = processDebugResources , path = :app:processDebugResources
ReplaceAssetsPlugin : whenReady name = compileDebugJavaWithJavac , path = :app:compileDebugJavaWithJavac
ReplaceAssetsPlugin : whenReady name = compileDebugSources , path = :app:compileDebugSources
ReplaceAssetsPlugin : whenReady name = mergeDebugNativeDebugMetadata , path = :app:mergeDebugNativeDebugMetadata
ReplaceAssetsPlugin : whenReady name = mergeDebugShaders , path = :app:mergeDebugShaders
ReplaceAssetsPlugin : whenReady name = compileDebugShaders , path = :app:compileDebugShaders
ReplaceAssetsPlugin : whenReady name = generateDebugAssets , path = :app:generateDebugAssets
ReplaceAssetsPlugin : whenReady name = replaceTask , path = :app:replaceTask
ReplaceAssetsPlugin : whenReady name = mergeDebugAssets , path = :app:mergeDebugAssets
ReplaceAssetsPlugin : whenReady name = compressDebugAssets , path = :app:compressDebugAssets
ReplaceAssetsPlugin : whenReady name = processDebugJavaRes , path = :app:processDebugJavaRes
ReplaceAssetsPlugin : whenReady name = mergeDebugJavaResource , path = :app:mergeDebugJavaResource
ReplaceAssetsPlugin : whenReady name = checkDebugDuplicateClasses , path = :app:checkDebugDuplicateClasses
ReplaceAssetsPlugin : whenReady name = dexBuilderDebug , path = :app:dexBuilderDebug
ReplaceAssetsPlugin : whenReady name = mergeExtDexDebug , path = :app:mergeExtDexDebug
ReplaceAssetsPlugin : whenReady name = mergeDexDebug , path = :app:mergeDexDebug
ReplaceAssetsPlugin : whenReady name = mergeDebugJniLibFolders , path = :app:mergeDebugJniLibFolders
ReplaceAssetsPlugin : whenReady name = mergeDebugNativeLibs , path = :app:mergeDebugNativeLibs
ReplaceAssetsPlugin : whenReady name = stripDebugDebugSymbols , path = :app:stripDebugDebugSymbols
ReplaceAssetsPlugin : whenReady name = validateSigningDebug , path = :app:validateSigningDebug
ReplaceAssetsPlugin : whenReady name = packageDebug , path = :app:packageDebug
ReplaceAssetsPlugin : whenReady name = assembleDebug , path = :app:assembleDebug

```

在 gradle plugin 中的 Task 主要有三种，一种是普通的 task，一种是增量 task，一种是 transform，下面分别看下这三种 task 怎么去读。

如何读 Task 的代码
1. 看 Task 继承的父类，一般来说，会继承 DefaultTask，IncrementalTask
2. 看 @TaskAction 注解的方法，此方法就是这个 Task 做的事情


### 重点 Task 实现分析

* generateDebugBuildConfig // 对应的类 GenerateBuildConfig ， 用来生成 BuildConfig.java
* processDebugManifest     //对应的类 MergeManifests ，用来合并 manifest 文件 ？疑问，没有找到这个类。
* mergeDebugResources      //对应的类 MergeResources , 用来 合并资源文件
* processDebugResources    //对应的类 ProcessAndroidResources  aapt 打包资源
* transformClassesWithDexBuilderForDebug //对应的类 DexArchiveBuilderTransform ，用来把 class 打包成 dex
* transformDexArchiveWithExternalLibsDexMergerForDebug //对应的类 ExternalLibsMergerTransform，用来打包三方库的 dex，在 dex 增量的时候就不需要再 merge 了，节省时间。
* transformDexArchiveWithDexMergerForDebug //对应的类 DexMergerTransform，用来打包最终的 dex。



### generateDebugBuildConfig 

对应的类 GenerateBuildConfig ， 用来生成 BuildConfig.java

GenerateBuildConfig 的 doTaskAction 方法。

```java
@Override
protected void doTaskAction() throws IOException {
    // must clear the folder in case the packagename changed, otherwise,
    // there'll be two classes.
    File destinationDir = getSourceOutputDir();
    FileUtils.cleanOutputDir(destinationDir);
    //创建 BuildConfigGenerator 对象
    BuildConfigGenerator generator = new BuildConfigGenerator(getSourceOutputDir(), getBuildConfigPackageName());
    // Hack (see IDEA-100046): We want to avoid reporting "condition is always true"
    // from the data flow inspection, so use a non-constant value. However, that defeats
    // the purpose of this flag (when not in debug mode, if (BuildConfig.DEBUG && ...) will
    // be completely removed by the compiler), so as a hack we do it only for the case
    // where debug is true, which is the most likely scenario while the user is looking
    // at source code.
    // map.put(PH_DEBUG, Boolean.toString(mDebug));
    
    //生成 BuildConfig.DEBUG
    generator.addField(
        "boolean", "DEBUG", isDebuggable() ? "Boolean.parseBoolean(\"true\")" : "false");
    if(isLibrary) {
        generator
            .addField(
                "String",
                "LIBRARY_PACKAGE_NAME",
                '"' + getBuildConfigPackageName() + '"')
            .addDeprecatedField(
                "String",
                "APPLICATION_ID",
                '"' + getBuildConfigPackageName() + '"',
                "@deprecated APPLICATION_ID is misleading in libraries. For the library package name use LIBRARY_PACKAGE_NAME"
            );
    } else {
        generator.addField("String", "APPLICATION_ID", '"' + getAppPackageName() + '"');
    }
    generator
        .addField("String", "BUILD_TYPE", '"' + getBuildTypeName() + '"')
        .addField("String", "FLAVOR", '"' + getFlavorName() + '"')
        .addField("int", "VERSION_CODE", Integer.toString(getVersionCode()))
        .addField(
            "String", "VERSION_NAME", '"' + Strings.nullToEmpty(getVersionName()) + '"')
        .addItems(getItems());
    List < String > flavors = getFlavorNamesWithDimensionNames();
    int count = flavors.size();
    if(count > 1) {
        for(int i = 0; i < count; i += 2) {
            generator.addField(
                "String", "FLAVOR_" + flavors.get(i + 1), '"' + flavors.get(i) + '"');
        }
    }
    //调用 BuildConfigGenerator 的 generate 方法。
    generator.generate();
}
```

BuildConfigGenerator 的 generate 方法。

```java
/**
 * Generates the BuildConfig class.
 */
public void generate() throws IOException {
    File pkgFolder = getFolderPath();
    if(!pkgFolder.isDirectory()) {
        if(!pkgFolder.mkdirs()) {
            throw new RuntimeException("Failed to create " + pkgFolder.getAbsolutePath());
        }
    }
    //BUILD_CONFIG_NAME 类名 BuildConfig.java 
    File buildConfigJava = new File(pkgFolder, BUILD_CONFIG_NAME);
    Closer closer = Closer.create();
    try {
        FileOutputStream fos = closer.register(new FileOutputStream(buildConfigJava));
        OutputStreamWriter out = closer.register(new OutputStreamWriter(fos, Charsets.UTF_8));
        //使用 JavaWriter 来生成类
        JavaWriter writer = closer.register(new JavaWriter(out));
        writer.emitJavadoc("Automatically generated file. DO NOT MODIFY")
            .emitPackage(mBuildConfigPackageName)
            .beginType("BuildConfig", "class", PUBLIC_FINAL);
        for(ClassField field: mFields) {
            emitClassField(writer, field);
        }
        for(Object item: mItems) {
            if(item instanceof ClassField) {
                emitClassField(writer, (ClassField) item);
            } else if(item instanceof String) {
                writer.emitSingleLineComment((String) item);
            }
        }
        writer.endType();
    } catch(Throwable e) {
        throw closer.rethrow(e);
    } finally {
        closer.close();
    }
}
```


### mergeDebugResources

对应的类 MergeResources , 用来合并资源文件。





参考链接

* [【Android 修炼手册】Gradle 篇 -- Android Gradle Plugin 主要 Task 分析](https://github.com/5A59/android-training/blob/master/gradle/android_gradle_plugin-%E4%B8%BB%E8%A6%81task%E5%88%86%E6%9E%90.md)