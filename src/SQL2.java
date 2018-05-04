import java.io.*;

public class SQL2 {
    public static void main(String[] args) {
        PrintWriter pw_warmup = null;
        PrintWriter pw = null;
        try {
            pw_warmup = new PrintWriter(new FileOutputStream("/home/giant/cat/sqls2_warmup.txt"));
            pw = new PrintWriter(new FileOutputStream("/home/giant/cat/sqls2.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("CAPTURE '/home/giant/cat/sqls2Out.txt';");
        pw.println("use sirius;");
        pw.println("tracing on;");

        String sql1 = "select * from test1 where pkey=1 and ck1=50 " +
                "and ck2>=1 and ck2<=1 " +
                "and ck3=50 allow filtering;";
        String sql2 = "select * from test1 where pkey=1 and ck1=50 " +
                "and ck2=1 " +
                "and ck3>=2 and ck3<=100 allow filtering;";
        int i=0;
        String tmp = sql1;
        while (i < 100) {
            if(i<20) {
                pw_warmup.println(tmp);
            }
            pw.println(tmp);
            i++;
        }
        i=0;
        tmp = sql2;
        while (i < 100) {
            if(i<20) {
                pw_warmup.println(tmp);
            }
            pw.println(tmp);
            i++;
        }
        pw_warmup.close();
        pw.close();

    }
}
