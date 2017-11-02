import edu.hdu.db.DbHelp;
import edu.hdu.utils.EsUtil;

import java.io.IOException;

public class testEsUtil {
    public static void main(String[] args) {
        EsUtil esUtil = new EsUtil();
        esUtil.doSearch("浙江省蓝藻治理", "");

//        DbHelp dbHelp = new DbHelp();
//        dbHelp.test();

    }
}