import java.io.*;

public class SQL {
    public static void main(String[] args) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("sqls.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("CAPTURE '/home/giant/cat/sqlOut0503.txt';");
        pw.println("use sirius;\n");
        pw.println("tracing on;\n");

        String sql = "select * from test1 where pkey=1 and ck1=50" +
                "and ck2>=1 and ck2<=%d " +
                "and ck3=50 allow filtering;\n";
        for(int z=1; z<=90;z++) {
            int i=0;
            String tmp = String.format(sql,z);
            while (i < 25) {
                pw.println(tmp);
                i++;
            }
        }
        pw.close();

    }
}
