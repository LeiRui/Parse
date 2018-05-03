import java.io.*;

public class Parse_Local {
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

        pw.write("sql,Key,Merged,Read,Request complete\n");

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

            else if(line.contains("Key cache hit")
                    ||line.contains("Merged data from")
                    ||line.contains("tombstone cells")) { // Read 200 live and 0 tombstone cells
                String[] split = line.split("\\|");
                pw.write(split[3]+",");
            }
            else if(line.contains("Request complete")) { //Request complete
                String[] split = line.split("\\|");
                pw.write(split[3] + "\n");
            }

        }


        sqlFilebr.close();
        sqlOutbr.close();
        pw.close();



    }
}
