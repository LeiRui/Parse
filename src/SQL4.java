import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class SQL4 {
    public static void main(String[] args) {
        PrintWriter pw_warmup = null;
        PrintWriter pw = null;
        PrintWriter pw_number = null;
        try {
            pw_warmup = new PrintWriter(new FileOutputStream("/home/giant/cat/sqls6_warmup.txt"));
            pw = new PrintWriter(new FileOutputStream("/home/giant/cat/sqls6.txt"));
            pw_number = new PrintWriter(new FileOutputStream("/home/giant/cat/sqls6Number.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("CAPTURE '/home/giant/cat/sqls6Out.txt';");
        pw.println("tracing on;");

        String sql1 = "select count(*) from sirius.test1 where pkey=1 and ck2=50 " +
                "and ck1>=1 and ck1<=%d " +
                "and ck3=50 allow filtering;"; // %d*100
        String sql2 = "select count(*) from red.dm1 where pkey=1 and ck1=1 " +
                "and ck2=1 " +
                "and ck3>=1 and ck3<=%d allow filtering;";

        for(int z=1;z<=100;z++) {
            String tmp1 = String.format(sql1,z); // read z*100, result z rows
            int i=0;
            while (i < 25) {
                if (i < 5) {
                    pw_warmup.println(tmp1);
                }
                pw.println(tmp1);
                pw_number.println(z*10000); // row number
                i++;
            }
        }
//        for (int z = 1; z <= 100; z++) {
//            String tmp2 = String.format(sql2, z); // read z, result z rows
//            int i = 0;
//            while (i < 30) {
//                if (i < 5) {
//                    pw_warmup.println(tmp2);
//                }
//                pw.println(tmp2);
//                i++;
//            }
//        }
        pw_warmup.close();
        pw.close();
        pw_number.close();
    }

    }
