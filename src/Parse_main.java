import java.io.*;

/*
 extract main contradication and actual cost from tracing output
 */
public class Parse_main {
    public static void main(String[] args) throws IOException {
        String sqlFile = "/home/giant/cat/sqls4.txt";
        String sqlOut = "/home/giant/cat/sqls4Out.txt";
        PrintWriter pw = null;
        BufferedReader sqlFilebr=null;
        BufferedReader sqlOutbr=null;
        try {

            sqlFilebr = new BufferedReader(new FileReader(sqlFile));
            sqlOutbr = new BufferedReader(new FileReader(sqlOut));
            pw = new PrintWriter(new FileOutputStream("ParseResult4.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pw.write("sql,DN:sum merge cost,CN:Request complete\n");

        String line;
        //boolean inSession = false;
        double dnSum=0;
        while((line = sqlOutbr.readLine()) != null)
        {
            if(line.equals("")) {
                continue;
            }

            //System.out.println(line);
            if(line.startsWith("Tracing session:")) {
                //inSession=true;
                String select=null;
                while(!(select = sqlFilebr.readLine()).startsWith("select")){
                }
                pw.write(select+","); // sqlFilebr可以放心地一行一行读
                //TODO 可以加一个过滤环节 过滤read repair以及key cache miss
                dnSum=0;
            }

//            else if(line.contains("Sending READ message to")
//                    ||line.contains("REQUEST_RESPONSE message received from")
//                    ||line.contains("Key cache hit")
//                    ||line.contains("Merged data from")
//                    ||line.contains("tombstone cells") // Read 200 live and 0 tombstone cells
//                    ||line.contains("Enqueuing")
//                    ||line.contains("Sending REQUEST_RESPONSE message to")){
//                String[] split = line.split("\\|");
//                pw.write(split[3]+",");
//            }
            else if(line.contains("Key cache hit")) {
                String[] split = line.split("\\|");
                double t1 = Double.parseDouble(split[3]);
                String nextLine = sqlOutbr.readLine();
                split = nextLine.split("\\|");
                double t2 = Double.parseDouble(split[3]);
                dnSum+=(t2-t1);
            }

            else if(line.contains("Request complete")) {
                // write dn sum first
                pw.write(dnSum + ",");

                String[] split = line.split("\\|");
                pw.write(split[3] + "\n");
            }

        }


        sqlFilebr.close();
        sqlOutbr.close();
        pw.close();



    }
}
