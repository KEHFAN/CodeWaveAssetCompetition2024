package uncompress;

import com.netease.lowcode.extension.uncompress.Result;
import com.netease.lowcode.extension.uncompress.UnZip;

public class UnZipTest {
    public static void main1(String[] args) {
        UnZip.unzip("https://lcpapp-static.nos-eastchina1.126.net/dogfood/asset-center/library/library-send_email-1.0.3.zip","");
    }

    public static void main(String[] args) {
        Result result = UnZip.unzipWithPassword("http://nos.netease.com/qiyu/open.api.ex.s.5407154.SROnbNlY?Signature=vdpczPwX2wTK88O38rwlK1Hmfl7AlG1wRXKUJxIpBBs%3D&Expires=1728894218&NOSAccessKeyId=b7e7f6715c6a4cffab9d28b5404f6f52",
                "5407154/session.txt",
                "7c14cb470657");
        System.out.println();
    }
}
