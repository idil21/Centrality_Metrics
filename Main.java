import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void readContentOfFile(File file, Graph graph)throws Exception{
        BufferedReader br= null;
        String line,source="",destination="";
        try{
            br= new BufferedReader(new FileReader(file));
            while((line=br.readLine()) != null){
                String[] splited= line.split("\\s+");
                source =splited[0];
                destination = splited[1];
                graph.addEdge(source,destination);
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        Graph graph= new Graph();
        //Graph graph2 = new Graph();
        File txt = new File("karate_club_network.txt");
        //File txt2= new File("facebook_social_network.txt");
        readContentOfFile(txt,graph);
        graph.allPossiblePaths();
        //readContentOfFile(txt2,graph2);
        //graph2.allPossiblePaths();

        System.out.println("2019510063  Sıla Idil Murat ");
        System.out.println("Zachary Karate Club Network –  The Highest Node for Betweennes: " + graph.getMaxNodeForBetween().getLabel() + "  Value: "  + graph.getMaxNodeForBetween().getBetweenCount());

        System.out.println("Zachary Karate Club Network –  The Highest Node for Closeness:  " + graph.getMaxNodeOfClose().getLabel() + "  Value: " +  graph.getMaxNodeOfClose().getCloseCount());


    }
}
