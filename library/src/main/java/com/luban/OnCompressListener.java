package com.luban;

import java.io.File;

/**
 * 压缩完成回调
 *
 * @author author
 */
public interface OnCompressListener {

  /**
   * Fired when the compression is started, override to handle in your own code
   */
  void onStart();

  /**
   * Fired when a compression returns successfully, override to handle in your own code
   * @param file 压缩后的file对象
   * @param  resInfo 压缩源的相关信息，谨防部分场景需要使用
   */
  void onSuccess(File file, String resInfo);

  /**
   * Fired when a compression fails to complete, override to handle in your own code
   */
  void onError(Throwable e);
}
