# Luban

<div align="right">
<a href="Translation/README-EN.md">:book: English Documentation</a>
</div>

`Luban`（鲁班） —— `Android`图片压缩工具，仿微信朋友圈压缩策略。

# 效果与对比

内容 | 原图 | `Luban` | `Wechat`
---- | ---- | ------ | ------
截屏 720P |720*1280,390k|720*1280,87k|720*1280,56k
截屏 1080P|1080*1920,2.21M|1080*1920,104k|1080*1920,112k
拍照 13M(4:3)|3096*4128,3.12M|1548*2064,141k|1548*2064,147k
拍照 9.6M(16:9)|4128*2322,4.64M|1032*581,97k|1032*581,74k
滚动截屏|1080*6433,1.56M|1080*6433,351k|1080*6433,482k

# 导入

```sh
implementation 'com.vedeng:Luban:1.1.8'
```

# 使用

### 方法列表

方法 | 描述
---- | ----
load | 传入原图
filter | 设置开启压缩条件
ignoreBy | 不压缩的阈值，单位为K
setFocusAlpha | 设置是否保留透明通道 
setTargetDir | 缓存压缩图片路径
setCompressListener | 压缩回调接口
setRenameListener | 压缩前重命名接口
setQuality | 设置图片压缩质量，安卓常用压缩参数

### 异步调用

`Luban`内部采用`IO`线程进行图片压缩，外部调用只需设置好结果监听即可：

```java
Luban.with(this)
        .load(photos)
        .ignoreBy(1024)
        .setQuality(90)
        .setTargetDir(getPath())
        .filter(new CompressionPredicate() {
          @Override
          public boolean apply(String path) {
            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
          }
        })
        .setCompressListener(new OnCompressListener() {
          @Override
          public void onStart() {
            // TODO 压缩开始前调用，可以在方法内启动 loading UI
          }

          @Override
          public void onSuccess(File file) {
            // TODO 压缩成功后调用，返回压缩后的图片文件
          }

          @Override
          public void onError(Throwable e) {
            // TODO 当压缩过程出现问题时调用
          }
        }).launch();
```

### 同步调用

同步方法请尽量避免在主线程调用以免阻塞主线程，下面以rxJava调用为例

```java
Flowable.just(photos)
    .observeOn(Schedulers.io())
    .map(new Function<List<String>, List<File>>() {
      @Override public List<File> apply(@NonNull List<String> list) throws Exception {
        // 同步方法直接返回压缩后的文件
        return Luban.with(MainActivity.this).load(list).get();
      }
    })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe();
```
