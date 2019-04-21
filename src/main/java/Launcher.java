import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Launcher {
    @Option(name = "-r", usage = "search in subdirectories")
    private boolean rFlag;

    @Option(name = "-d", usage = "directory")
    private String directory;

    @Argument(required = true, usage = "file name")
    private String fileName;

    public static void main(String[] args) {
        new Launcher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Find.jar [-r] [-d directory] filename.txt");
            parser.printUsage(System.err);
        }

        try {
            Find find = new Find(rFlag, directory, fileName);
            find.find();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
