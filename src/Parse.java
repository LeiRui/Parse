import java.io.*;

public class Parse {
    public static void main(String[] args) throws IOException{
        String sqlFile = "D:\\files\\sqls.txt";
        String sqlOut = "D:\\files\\sqlsOut.txt";
        PrintWriter pw = null;
        BufferedReader sqlFilebr=null;
        BufferedReader sqlOutbr=null;
        try {

            sqlFilebr = new BufferedReader(new FileReader(sqlFile));
            sqlOutbr = new BufferedReader(new FileReader(sqlOut));
            pw = new PrintWriter(new FileOutputStream("ParseResult1.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pw.write("sql,CN:Sending,CN:REQUEST_RESPONSE,DN:Key,DN:Merged,DN:Read,DN:Enqueuing,DN:Sending,CN:Request complete\n");

        String line;
        //boolean inSession = false;
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
            }

            else if(line.contains("Sending READ message to")
                    ||line.contains("REQUEST_RESPONSE message received from")
                    ||line.contains("Key cache hit")
                    ||line.contains("Merged data from")
                    ||line.contains("tombstone cells") // Read 200 live and 0 tombstone cells
                    ||line.contains("Enqueuing")
                    ||line.contains("Sending REQUEST_RESPONSE message to")){
                String[] split = line.split("\\|");
                pw.write(split[3]+",");
            }
            else if(line.contains("Request complete")) {
                String[] split = line.split("\\|");
                pw.write(split[3] + "\n");
            }

        }


        sqlFilebr.close();
        sqlOutbr.close();
        pw.close();



    }
}
