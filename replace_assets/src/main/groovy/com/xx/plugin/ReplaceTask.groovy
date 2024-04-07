package com.xx.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.concurrent.TimeUnit

/**
 * Created by p_dmweidu on 2022/11/30
 * Desc:
 */
public class ReplaceTask extends DefaultTask {

    private String TAG = "ReplaceTask"

    @TaskAction
    void generateDarkMode() {
        String resCompiledDirPath = "$project.buildDir/intermediates/processed_res/debug/out"
        String resCompiledFilePath = "$resCompiledDirPath/resources-debug.ap_"
        File resFile = new File(resCompiledFilePath)
        if (!resFile.exists()) {
            Log.printlnWithTag(TAG, "copyProcessedResources path:$resCompiledFilePath not exist")
            return
        } else {
            Log.printlnWithTag(TAG, "copyProcessedResources path:$resCompiledFilePath")
        }

        //存放编译后的资源
        File destFile = new File("${project.rootDir}/app/src/main/assets/compiledRes")
        if (!destFile.exists()) {
            destFile.mkdirs()
        }
        Log.printlnWithTag(TAG, "copyProcessedResources ${destFile.absolutePath}")

        ant.unzip(src: resCompiledFilePath, dest: destFile)

        def oldDarkModeFile = "${project.rootDir}/app/src/main/assets/darkmode.7z"
        //在assets目录下新建 newSkin 文件夹
        String newSkinDir = "${project.rootDir}/app/src/main/assets/newSkin"

        File file = new File(newSkinDir)
        file.deleteDir()
        file.mkdirs()

        Log.printlnWithTag(TAG, " oldDarkModeFile = " + oldDarkModeFile)
        Log.printlnWithTag(TAG, " newSkinDir = " + newSkinDir)

        String sevenZipPath = get7zPath2()
        if (sevenZipPath.isEmpty()) {
            Log.printlnWithTag(TAG, "do7zipCompressApk 7zip path is empty")
            return
        }

        try {
            //把老的皮肤包解压到 newSkinDir 目录下
            extract7z(oldDarkModeFile, file.getAbsolutePath(), sevenZipPath)
        } catch (Exception ep) {
            Log.printlnWithTag(TAG, "extract7z error: " + ep.getMessage())
        }

        // 1. 把compliedRes/res/color-night-v8下面的文件都拷贝到 newSkin/color 目录下，直接替换

        File newSkinColorDir = new File(newSkinDir, "color")
        if (!newSkinColorDir.exists()) {
            newSkinColorDir.mkdirs()
        }

        File colorNightV8Dir = new File(destFile.getAbsolutePath() + File.separator + "res/color")
        //File colorNightV8Dir = new File(destFile.getAbsolutePath()+File.separator+"res/color-night-v8")

        if (colorNightV8Dir.exists() && colorNightV8Dir.isDirectory()) {
            Log.printlnWithTag(TAG, "----开始拷贝颜色-----")
            File[] colorFiles = colorNightV8Dir.listFiles()
            for (File colorFile : colorFiles) {
                String fileName = colorFile.getName()
                File destColorFile = new File(newSkinColorDir, fileName)
                Log.printlnWithTag(TAG, "copy color file " + colorFile.getAbsolutePath() + "\n to " + destColorFile.getAbsolutePath())
                Files.copy(Paths.get(colorFile.getAbsolutePath()), Paths.get(destColorFile.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING)
            }
            Log.printlnWithTag(TAG, "----拷贝颜色结束-----")
        }

        // 2. 把 newSkin 打包成 darkmodeNew.7z

        String darkModeNew7zPath = "${project.rootDir}/app/src/main/assets/darkModeNew.7z"
        package7z(newSkinDir, darkModeNew7zPath, sevenZipPath)

        // 3. 把 darkmode.7z 删除

        // 4. 把 darkmodeNew.7z 重命名为 darkmode.7z

    }

    private static String get7zPath2() {
        String sevenZipPath = "";
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder("which", "7z");
            // 不添加这个路径，找不到7z，你可以尝试在你的代码中显式地添加环境变量，以确保 which 命令可以在任何情况下找到 7z。
            // 你可以使用 ProcessBuilder 的 environment 方法来获取和修改当前的环境变量。
            // 以下是如何在你的 get7zPath 方法中添加这个修改：
            Map<String, String> env = builder.environment();
            env.put("PATH", env.get("PATH") + ":/usr/local/bin");
            process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("/7z")) {
                    sevenZipPath = line;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return sevenZipPath;
    }
    /**
     * 解压7z文件
     *
     * @param srcDirPath
     * @param outZipPath
     * @param sevenZipPath
     * @throws IOException* @throws InterruptedException
     */
    private void extract7z(String srcFilePath, String outZipPath, String sevenZipPath)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder([sevenZipPath, "x", srcFilePath, "-o" + outZipPath])
        Process process = pb.start()

        InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir)
        String line;
        while ((line = input.readLine()) != null) {
            Log.printlnWithTag(TAG, "extract7z：" + input.getLineNumber() + ":" + line);
        }
        process.waitFor()
        process.destroy()
    }

    /**
     * 将文件压缩成7z文件
     *
     * @param srcDirPath
     * @param outZipPath
     * @param sevenZipPath
     * @throws IOException  @throws InterruptedException
     */
    private void package7z(String srcFilePath, String outZipPath, String sevenZipPath)
            throws IOException, InterruptedException {
        //将 srcFilePath 目录下的所有文件（不包括 srcFilePath 目录） 压缩到 outZipPath。
        ProcessBuilder pb = new ProcessBuilder([sevenZipPath,
                                                "a", "-t7z", outZipPath, srcFilePath + File.separator + "*", "-mx5"])
        Process process = pb.start()

        InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir)
        String line;
        while ((line = input.readLine()) != null) {
            Log.printlnWithTag(TAG, "package7z：" + input.getLineNumber() + ":" + line);
        }
        process.waitFor()
        process.destroy()
    }

}
