package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ApkHelper {
    private final String apkInfo;

    public ApkHelper(String app) {
        try {
            //вызываем bash команду aapt dumb banding путь к apk, чтобы прочитать AndroidManifest.xml из apk файла
            apkInfo = executeSh("C:\\Users\\Public\\AndroidSDK\\build-tools\\34.0.0\\aapt.exe dumb badging " + app);
        } catch (IOException | InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }
    }

    public String getAppPackageFromApk() {
        return findGroup1ValueFromString(apkInfo,"package: name='\\s*([^']+?)\\s*'");
    }

    public String getAppMainActivity()  {
        return findGroup1ValueFromString(apkInfo, "launchable-activity: name='\\s*([^']+?)\\s*'");
    }

    private static String findGroup1ValueFromString(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    public static String executeSh(String command) throws IOException, ExecutionException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);//
        FutureTask<String > future = new FutureTask<>(()->{
            return new BufferedReader(new InputStreamReader(p.getInputStream()))
                    .lines().map(Object::toString)
                    .collect(Collectors.joining("\n"));
        });
        new Thread(future).start();
        return future.get();
    }
}