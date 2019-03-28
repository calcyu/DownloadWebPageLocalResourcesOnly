package pereira.agnaldo.downloadwebpage.entity;

/**
 * Author: CalcYu
 * Date: 2019/3/28
 */
public class DownloadVo {
    private String url;
    private String file;

    public DownloadVo(String url, String file) {
        this.url = url;
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
